package com.example.gozum.chatm8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gozum.chatm8.helpers.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    String url = "http://51.254.242.39:8000/register";
    EditText accountId;
    EditText name;
    EditText password;
    EditText repeatPassword;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountId = findViewById(R.id.registerAccountId);
        name = findViewById(R.id.registerName);
        password = findViewById(R.id.registerPassword);
        repeatPassword = findViewById(R.id.registerRepeatPassword);
        email = findViewById(R.id.registerEmail);
    }

    public boolean CheckFields() {
        return
                !(
                        TextUtils.isEmpty(accountId.getText().toString())
                                || TextUtils.isEmpty(name.getText().toString())
                                || TextUtils.isEmpty(password.getText().toString())
                                || TextUtils.isEmpty(repeatPassword.getText().toString())
                                || TextUtils.isEmpty(email.getText().toString())
                ) &&
                        password.getText().toString().matches(repeatPassword.getText().toString());
    }

    public void PostRequest(View v) {
        if (CheckFields()) {

            StringRequest postRequest = new StringRequest(Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject json;
                            try {
                                json = new JSONObject(response);
                                if (json.getString("status").equals("OK")) {
                                    SuccessfulRegister(json);
                                } else {
                                    FailedRegister();
                                }
                            } catch (JSONException _e) {
                                _e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            FailedRegister();
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name.getText().toString());
                    params.put("accountid", accountId.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("imgUrl", "");
                    params.put("isActive", "1");
                    params.put("created_time", "1");
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(postRequest);
        }
    }

    public void SuccessfulRegister(JSONObject json) {
        Toast.makeText(getApplicationContext(), "" + "Hoşgeldiniz..", Toast.LENGTH_LONG).show();
        Intent i = new Intent(RegisterActivity.this,HomePage.class);
        startActivity(i);
        PreferencesManager.Instance().TryRegister(json);
    }

    public void FailedRegister() {
        Toast.makeText(getApplicationContext(), "" + "Hatalı kayıt..", Toast.LENGTH_LONG).show();
    }
}
