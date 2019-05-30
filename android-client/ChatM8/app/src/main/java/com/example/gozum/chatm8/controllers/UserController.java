package com.example.gozum.chatm8.controllers;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gozum.chatm8.dto.UserDTO;
import com.example.gozum.chatm8.entities.User;
import com.example.gozum.chatm8.helpers.PreferencesManager;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class UserController implements BaseController {
    private String url = "http://51.254.242.39:8000/";

    private static volatile UserController _instance = null;
    private UserController()
    {
        if(_instance != null)
        {
            throw new RuntimeException("Lütfen Instance() fonksiyonunu kullanınız , new keyword'ü kullanılmamalı !");
        }
    }
    public static UserController Instance()
    {
        if(_instance == null)
        {
            synchronized (UserController.class)
            {
                _instance = new UserController();
            }
        }
        return _instance;
    }



    @Override
    public void GetAll(Context ctx, final VolleyCallback callback) {

        StringRequest users = new StringRequest(
                Request.Method.GET, url+"getall",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj= new JSONObject(response);
                            callback.onSuccess(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley_Error",error.getMessage());
                    }
                }
              );
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(users);
    }


    public void Login(Context ctx, final String email, final String password, final VolleyCallback callback)
    {
        StringRequest login = new StringRequest(
                Request.Method.POST, url + "login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("OK"))
                            {
                                callback.onSuccess(obj);
                            }

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
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(login);
    }




    public void Create(final User entity ,Context ctx, final VolleyCallback callback) {
        StringRequest insert = new StringRequest(Request.Method.POST, url + "register",
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
                }){
            @Override
            protected Map<String, String> getParams() {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.UK);
                Date date = new Date();
                Map<String, String> params = new HashMap<>();
                params.put("email", entity.getEmail());
                params.put("password", entity.getPassword());
                params.put("name",entity.getName());
                params.put("created_time",dateFormat.format(date));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(insert);
    }

}
