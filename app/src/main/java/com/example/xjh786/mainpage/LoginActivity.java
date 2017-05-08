package com.example.xjh786.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etCoreId;
    private EditText etPassword;
    private static final String TAG = "LibraryApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view){
        etCoreId = (EditText) findViewById(R.id.eTCoreId);
        etPassword = (EditText)findViewById(R.id.eTPassword);

        final String str_CoreId = etCoreId.getText().toString();
        final String str_Password = etPassword.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onClickLogin: receive response");

                    if(b_success){
                        Log.d(TAG, "onClickLogin: success");
                        String str_fullName = jsonResponse.getJSONObject("result").getString("fullName");
                        boolean b_isAdmin = jsonResponse.getJSONObject("result").getBoolean("isAdmin");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("fullName", str_fullName);
                        intent.putExtra("isAdmin", b_isAdmin);
                        intent.putExtra("coreId", str_CoreId);

                        LoginActivity.this.startActivity(intent);

                    }else{
                        Log.d(TAG, "onClickLogin: fail");
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Log.d(TAG, "onClickLogin: sending request");

        LoginRequest loginRequest = new LoginRequest(str_CoreId, str_Password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
    public void onClickReg(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        LoginActivity.this.startActivity(intent);
    }

}
