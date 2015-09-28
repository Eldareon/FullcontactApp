package com.fjodors.fullcontactapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.controllers.FullContactClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;


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

    private FullContactClient fullContactClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        domains = new ArrayList<String>();

        prefs = this.getSharedPreferences(DOMAINS_KEY, Context.MODE_PRIVATE);
        edit = prefs.edit();

        fullContactClient = new FullContactClient();

        getDomainsFromMemory();
        addDomainHistory();

    }

    public static class ErrorResponse {
        public String message;
    }

    private void notifyError(Throwable throwable) {
        Log.e(TAG, "error", throwable);
        errorMsgTV.setVisibility(View.VISIBLE);
        if (throwable instanceof RetrofitError) {
            ErrorResponse errorResponse = (ErrorResponse) ((RetrofitError) throwable).getBodyAs(ErrorResponse.class);
            errorMsgTV.setText(errorResponse.message + " " + getString(R.string.try_again));
        } else {
            errorMsgTV.setText(throwable.getMessage() + " " + getString(R.string.try_again));
        }
    }


    @OnClick(R.id.searchButton)
    public void searchData() {
        errorMsgTV.setVisibility(View.GONE);
        searchButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        fullContactClient.getCompanyData(this, searchText.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    searchButton.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }).subscribe(
                company -> {
                    if (company.getWebsite() != null) {
                        checkIfInHistory(searchText.getText().toString());
                        Intent intent = new Intent(this, ResultActivity.class);
                        intent.putExtra("companyData", company);
                        startActivity(intent);
                    } else {
                        notifyError(new Throwable(getString(R.string.no_data_error)));
                    }
                },
                this::notifyError);
    }

    public void checkIfInHistory(String domainURL) {
        boolean isInHistory = false;

        for (String domain : domains) {
            if (domain.equalsIgnoreCase(domainURL)) {
                isInHistory = true;
                break;
            }
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
