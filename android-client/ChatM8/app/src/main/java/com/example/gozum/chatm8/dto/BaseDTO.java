package com.example.gozum.chatm8.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface BaseDTO <T>{
    T Get(JSONObject obj);
    List<T> Gets(JSONArray jsonArray);
}
