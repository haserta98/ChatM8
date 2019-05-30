package com.example.gozum.chatm8;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gozum.chatm8.controllers.RoomController;
import com.example.gozum.chatm8.controllers.UserController;
import com.example.gozum.chatm8.helpers.PreferencesManager;
import com.example.gozum.chatm8.helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boot();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        if(PreferencesManager.Instance().isAuth()){
            Intent i = new Intent(MainActivity.this, HomePage.class);
            startActivity(i);
        }
    }

    public void onRegister(View v) {
        Intent register = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    public void onLogin(View v) {

        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {

            UserController.Instance().Login(
                    getApplicationContext(),
                    email.getText().toString(),
                    password.getText().toString(),
                    new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    SuccessfulLogin(response);
                }
                @Override
                public void onSuccess(JSONArray response)
                {

                }
                    });
        }
    }

    public void SuccessfulLogin(JSONObject json) {
        try {
            Toast.makeText(getApplicationContext(), "" + json.getString("status"), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PreferencesManager.Instance().TryLogin(json);
        Intent i = new Intent(MainActivity.this, HomePage.class);
        startActivity(i);
    }


    public void Boot()
    {
        RoomController.Instance().SetContext(getApplicationContext());
        SocketClient.Instance().JoinRoom("default",PreferencesManager.Instance()._preferences.getString("accountid",""),null);
    }

    public void ClickSocialMediaButtons(View v)
    {
        String url="";
        switch(v.getId())
        {
            case R.id.facebook:
                url = "https://www.facebook.com/haserta98";
                break;
            case R.id.twitter:
                url = "https://www.twitter.com";
                break;
            case R.id.instagram:
                url = "https://www.instagram.com/hasanssx";
                break;
        }
        Uri _url = Uri.parse(url);
        Intent launch = new Intent(Intent.ACTION_VIEW,_url);
        startActivity(launch);
    }
}
