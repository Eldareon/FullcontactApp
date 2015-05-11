package com.fjodors.fullcontactapp.controllers;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fjodors.fullcontactapp.app.AppController;
import com.fjodors.fullcontactapp.listeners.WebResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fjodors on 2015.05.08..
 */
public class WebClient {

    protected static final String TAG = "Fullcontact-WebClient";

    public WebClient() {

    }

    public void request(String domain, final WebResponseCallback callback, String apiKey) {

        Log.d(TAG, "Request: " + domain);

        StringRequest req = new StringRequest(
                Request.Method.GET,
                "https://api.fullcontact.com/v2/company/lookup.json?domain=" + domain + "&apiKey=" + apiKey,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response: ", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("Response: ", obj.toString(4));
                            callback.onData(obj);
                        } catch (Throwable t) {

                            try {
                                callback.onData(new JSONObject()
                                        .put("nojson", "response")
                                        .put("data", response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });

        req.setTag("api-call");
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // 2500
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // 1
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // 1F
                )
        );

        AppController.getInstance().addToRequestQueue(req);

    }
}
