package com.fjodors.fullcontactapp.controllers;


import com.fjodors.fullcontactapp.listeners.WebResponseCallback;

import org.json.JSONException;

/**
 * Created by Fjodors on 2015.05.08..
 */
public class RequestBuilder {

    private String apiKey = "16e01d26c38b8739";

    public RequestBuilder() {

    }

    public void DemandData(String domainUrl, WebResponseCallback callback) throws JSONException {
        new WebClient().request(domainUrl, callback, apiKey);
    }
}