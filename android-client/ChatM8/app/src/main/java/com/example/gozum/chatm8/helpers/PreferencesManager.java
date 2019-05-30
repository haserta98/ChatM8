package com.example.gozum.chatm8.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public final class PreferencesManager extends Activity {

    public SharedPreferences _preferences;
    public SharedPreferences.Editor _editor;

    private static volatile PreferencesManager _instance = null;

    public static PreferencesManager Instance()
    {
        if(_instance == null)
        {
            synchronized (PreferencesManager.class)
            {
                _instance = new PreferencesManager();
            }
        }
        return _instance;
    }


    public PreferencesManager()
    {
        if(_instance != null)
        {
            throw new RuntimeException("Lütfen Instance() fonksiyonunu kullanınız !");
        }
        _preferences = GlobalApplication.getAppContext().getSharedPreferences("Pref",MODE_PRIVATE);
        _editor = _preferences.edit();
    }


    public boolean TryLogin(JSONObject object)
    {
        try {
            if(object.getString("status").equals("OK"))
            {
                _editor.putBoolean("auth",true);
                _editor.putString("accountid",object.getString("accountid"));
                _editor.putString("name",object.getString("name"));
                _editor.commit();
                return true;
            }
            else{
                _editor.putBoolean("auth",false);
                _editor.commit();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean TryRegister(JSONObject object)
    {
        try {
            if(object.getString("status").equals("OK"))
            {
                _editor.putBoolean("auth",true);
                _editor.putString("accountid",object.getString("accountid"));
                _editor.putString("name",object.getString("name"));
                _editor.commit();
                return true;
            }
            else{
                _editor.putBoolean("auth",false);
                _editor.commit();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAuth()
    {
        return _preferences.getBoolean("auth",false);
    }
}
