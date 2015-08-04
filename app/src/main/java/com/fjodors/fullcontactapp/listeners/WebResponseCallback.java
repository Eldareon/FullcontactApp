package com.fjodors.fullcontactapp.listeners;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Fjodors on 2015.05.08..
 */
public interface WebResponseCallback {

    void onError(VolleyError data);

    void onData(JSONObject data);
}

