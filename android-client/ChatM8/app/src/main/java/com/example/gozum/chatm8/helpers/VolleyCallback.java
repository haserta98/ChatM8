package com.example.gozum.chatm8.helpers;

import org.json.JSONArray;
import org.json.JSONObject;

public interface VolleyCallback {

    void onSuccess(JSONObject response);

    void onSuccess(JSONArray array);

}
