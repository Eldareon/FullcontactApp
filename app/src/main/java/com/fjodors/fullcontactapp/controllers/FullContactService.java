package com.fjodors.fullcontactapp.controllers;

import com.fjodors.fullcontactapp.models.Company;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Fjodors on 2015.08.18..
 */
public interface FullContactService {

    @GET("/company/lookup.json")
    Observable<Company> getCompanyData(
            @Query("domain") String domain,
            @Query("apiKey") String apiKey);
}
