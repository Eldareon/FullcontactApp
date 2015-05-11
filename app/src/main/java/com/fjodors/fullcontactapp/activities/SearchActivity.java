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
import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.controllers.RequestBuilder;
import com.fjodors.fullcontactapp.listeners.WebResponseCallback;
import com.fjodors.fullcontactapp.models.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SearchActivity extends MenuActivity {

    protected static final String TAG = "Fullcontact-Search";
    private ProgressBar progressBar;
    private TextView errorMsgTV;
    private AutoCompleteTextView searchText;

    private List<String> domains = new ArrayList<String>();

    protected static final String DOMAINS_KEY = "domains";
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorMsgTV = (TextView) findViewById(R.id.errorMsg);

        searchText = (AutoCompleteTextView) findViewById(R.id.search);

        prefs = this.getSharedPreferences(DOMAINS_KEY, Context.MODE_PRIVATE);
        edit = prefs.edit();

        getDomainsFromMemory();

        addDomainHistory();


        Button searchButton = (Button) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                errorMsgTV.setVisibility(View.GONE);

                if(Patterns.WEB_URL.matcher(searchText.getText().toString()).matches()){
                    progressBar.setVisibility(View.VISIBLE);
                    SearchData(searchText.getText().toString());
                } else {
                    errorMsgTV.setVisibility(View.VISIBLE);
                    errorMsgTV.setText(getString(R.string.incorrect_domain_url)+" "+getString(R.string.try_again));
                }

                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(searchText.getWindowToken(), 0);

            }
        });


    }


    public void SearchData(final String domainURL) {

        RequestBuilder JSONBuilder = new RequestBuilder();

        try {
            JSONBuilder.DemandData(domainURL,
                    new WebResponseCallback() {
                        @Override
                        public void onData(JSONObject data) {
                            try {
                                Log.i(TAG, "Response: " + data.toString(4));

                                ArrayList<Company> companyData = new ArrayList<Company>();

                                //first - add company main data to arraylist

                                JSONObject organization = new JSONObject(
                                        data.getString("organization"));

                                Company company = new Company();

                                company.setName(organization.optString("name"));
                                company.setLogoUrl(data.optString("logo"));
                                company.setEmployeeCount(organization.optString("approxEmployees"));
                                company.setYearFounded(organization.optString("founded"));
                                company.setWebsite(data.optString("website"));


                                companyData.add(company);


                                //second - add links
                                try {
                                    JSONArray linksArray = new JSONArray(
                                            organization.optString("links"));


                                    for (int i = 0; i < linksArray.length(); i++) {

                                        JSONObject jsondata;
                                        company = new Company();

                                        try {
                                            jsondata = linksArray.getJSONObject(i);
                                            company.setLinkUrl(jsondata.optString("url"));
                                            companyData.add(company);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //third - add emails
                                try {
                                    JSONObject contactInfo = new JSONObject(
                                            organization.optString("contactInfo"));


                                    JSONArray emailsArray = new JSONArray(
                                            contactInfo.optString("emailAddresses"));


                                    for (int i = 0; i < emailsArray.length(); i++) {

                                        JSONObject jsondata;
                                        company = new Company();

                                        try {
                                            jsondata = emailsArray.getJSONObject(i);
                                            company.setEmail(jsondata.optString("value"));
                                            Log.d("comp", jsondata.optString("value"));
                                            companyData.add(company);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //forth - social bio
                                try {
                                    JSONArray socialArray = new JSONArray(
                                            data.optString("socialProfiles"));

                                    for (int i = 0; i < socialArray.length(); i++) {

                                        JSONObject jsondata;
                                        company = new Company();

                                        try {
                                            jsondata = socialArray.getJSONObject(i);
                                            company.setTypeName(jsondata.optString("typeName"));
                                            company.setBioText(jsondata.optString("bio"));
                                            companyData.add(company);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                checkIfInHistory(domainURL);

                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                                intent.putParcelableArrayListExtra("companyData", companyData);
                                startActivity(intent);


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                progressBar.setVisibility(View.GONE);
                                errorMsgTV.setText(getString(R.string.data_parsing_error) + " " + getString(R.string.try_again));
                                errorMsgTV.setVisibility(View.VISIBLE);
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

                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
            errorMsgTV.setText(getString(R.string.data_parsing_error) + " " + getString(R.string.try_again));
            errorMsgTV.setVisibility(View.VISIBLE);
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
