package com.example.gozum.chatm8.controllers;

import android.content.Context;

import com.example.gozum.chatm8.helpers.VolleyCallback;

import java.util.List;

public interface BaseController{
    void GetAll(Context ctx,VolleyCallback callback);
}
