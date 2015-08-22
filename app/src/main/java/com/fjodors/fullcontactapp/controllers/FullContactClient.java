package com.fjodors.fullcontactapp.controllers;

import android.content.Context;
import android.util.Patterns;

import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.models.Company;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import rx.Observable;

/**
 * Created by Fjodors on 2015.08.18..
 */
public class FullContactClient {

    private static final String TAG = FullContactClient.class.getSimpleName();
    private static String ENDPOINT="https://api.fullcontact.com/v2";
    private static String API_KEY = "16e01d26c38b8739";
    private RestAdapter restAdapter;
    private FullContactService fullContactService;

    public FullContactClient(){
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(TAG))
                .build();
        fullContactService = restAdapter.create(FullContactService.class);
    }

    private boolean isCorrectWebUrl(String domainURL) {
        return Patterns.WEB_URL.matcher(domainURL).matches();
    }

    public Observable<Company> getCompanyData(Context context,String domainURL){

        if (!isCorrectWebUrl(domainURL))
            return error(context.getString(R.string.incorrect_domain_url));


        return fullContactService.getCompanyData(domainURL, API_KEY);
    }

    <T> Observable<T> error(String errorMessage) {
        return Observable.error(new Throwable(errorMessage));
    }


}
