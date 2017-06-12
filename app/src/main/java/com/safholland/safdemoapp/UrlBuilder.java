package com.safholland.safdemoapp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

/**
 * Created by roeje on 6/9/17.
 */

public class UrlBuilder {

    private String baseUrl = "http://api.safholland.us/v3/gqss?uom=imperial&type=ss";

    public String createServiceUrl(String srcLat, String srcLng) {

        return baseUrl + "&origLat=" + srcLat + "&origLng" + srcLng;
    }
}
