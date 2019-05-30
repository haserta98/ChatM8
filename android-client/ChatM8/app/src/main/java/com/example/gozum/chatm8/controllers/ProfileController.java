package com.example.gozum.chatm8.controllers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gozum.chatm8.helpers.PreferencesManager;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileController {
    private static final String url = "http://51.254.242.39:8000/";
    private static volatile ProfileController _instance = null;

    public ProfileController()
    {
        if(_instance !=null)
            throw new RuntimeException("Lütfen Instance() kullan");
    }

    public static ProfileController Instance()
    {
        if(_instance == null)
        {
            synchronized (ProfileController.class)
            {
                _instance = new ProfileController();
            }
        }
        return _instance;
    }

    public void SaveImage(Context ctx, final String image, final VolleyCallback callback)
    {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url + "resimekle",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("image", image);
                params.put("accountid", PreferencesManager.Instance()._preferences.getString("accountid",""));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(request);
    }

    public void ReadImage(Context ctx, final VolleyCallback callback){
        StringRequest request = new StringRequest(
                url + "resimgetir/" + PreferencesManager.Instance()._preferences.getString("accountid", ""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getJSONObject("data").getJSONArray("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(request);
    }
}
