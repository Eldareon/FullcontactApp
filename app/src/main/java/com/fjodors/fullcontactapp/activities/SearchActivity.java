package com.fjodors.fullcontactapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.controllers.RequestBuilder;
import com.fjodors.fullcontactapp.listeners.WebResponseCallback;
import com.fjodors.fullcontactapp.models.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchActivity extends MenuActivity {

    protected static final String TAG = "Fullcontact-Search";

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.errorMsg)
    TextView errorMsgTV;
    @Bind(R.id.search)
    AutoCompleteTextView searchText;
    @Bind(R.id.searchButton)
    Button searchButton;

    private List<String> domains;

    protected static final String DOMAINS_KEY = "domains";
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    Company company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        domains = new ArrayList<String>();

        prefs = this.getSharedPreferences(DOMAINS_KEY, Context.MODE_PRIVATE);
        edit = prefs.edit();

        getDomainsFromMemory();
        addDomainHistory();

    }

    @OnClick(R.id.searchButton)
    public void searchData() {
        errorMsgTV.setVisibility(View.GONE);
        searchButton.setEnabled(false);

        if (Patterns.WEB_URL.matcher(searchText.getText().toString()).matches()) {
            progressBar.setVisibility(View.VISIBLE);
            getData(searchText.getText().toString());
        } else {
            errorMsgTV.setVisibility(View.VISIBLE);
            errorMsgTV.setText(getString(R.string.incorrect_domain_url) + " " + getString(R.string.try_again));
            searchButton.setEnabled(true);
        }

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
    }


    public void getData(final String domainURL) {

        RequestBuilder JSONBuilder = new RequestBuilder();

        try {
            JSONBuilder.DemandData(domainURL,
                    new WebResponseCallback() {
                        @Override
                        public void onData(JSONObject data) {
                            try {
                                Log.i(TAG, "Response: " + data.toString(4));

                                fillCompanyData(data);
                                
                                checkIfInHistory(domainURL);

                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                                intent.putExtra("companyData", company);
                                startActivity(intent);
                                searchButton.setEnabled(true);


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                progressBar.setVisibility(View.GONE);
                                errorMsgTV.setText(getString(R.string.data_parsing_error) + " " + getString(R.string.try_again));
                                errorMsgTV.setVisibility(View.VISIBLE);
                                searchButton.setEnabled(true);
                            }

                        }

                        public void onError(VolleyError error) {
                            Log.i(TAG, "Response: " + error.getMessage());

                            String errorMsg;

                            progressBar.setVisibility(View.GONE);

                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                errorMsg = getString(R.string.network_timeout);
                            } else if (error instanceof AuthFailureError) {
                                errorMsg = getString(R.string.auth_failure);
                            } else if (error instanceof ServerError) {
                                errorMsg = getString(R.string.server_error);
                            } else if (error instanceof NetworkError) {
                                errorMsg = getString(R.string.network_error);
                            } else if (error instanceof ParseError) {
                                errorMsg = getString(R.string.parse_error);
                            } else {
                                errorMsg = error.getMessage();
                            }

                            errorMsgTV.setText(errorMsg + " " + getString(R.string.try_again));
                            errorMsgTV.setVisibility(View.VISIBLE);
                            searchButton.setEnabled(true);

                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
            errorMsgTV.setText(getString(R.string.data_parsing_error) + " " + getString(R.string.try_again));
            errorMsgTV.setVisibility(View.VISIBLE);
            searchButton.setEnabled(true);
        }
    }



    public void fillCompanyData(JSONObject data) throws JSONException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            company = mapper.readValue(data.toString(), Company.class);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkIfInHistory(String domainURL) {
        boolean isInHistory = false;

        for (String domain : domains) {
            if (domain.equalsIgnoreCase(domainURL))
                isInHistory = true;
        }

        if (!isInHistory) {
            domains.add(domainURL);
            addDomainHistory();
        }
    }

    public void addDomainHistory() {
        ArrayAdapter<String> ACTadapter = new ArrayAdapter<String>
                (this, R.layout.auto_text_dropdown, domains);
        ACTadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        searchText.setThreshold(1);
        searchText.setAdapter(ACTadapter);

        saveDomainsToMemory();

    }

    public void saveDomainsToMemory() {
        Set<String> set = new HashSet<String>();
        set.addAll(domains);
        edit.clear();
        edit.putStringSet(DOMAINS_KEY, set);
        edit.commit();
    }

    public void getDomainsFromMemory() {
        Set<String> set = prefs.getStringSet(DOMAINS_KEY, null);

        if (set != null) {
            List<String> domainsFromMemory = new ArrayList<String>(set);
            domains = domainsFromMemory;
        }
    }

}
