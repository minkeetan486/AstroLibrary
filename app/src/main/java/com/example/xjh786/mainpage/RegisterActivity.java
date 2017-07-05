package com.example.xjh786.mainpage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etCoreId;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etDepartment_Code;
    private static final String TAG = "LibraryApp";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "RegisterActivity: oncreate");
    }

    public void onClickSubmit(View view){
        etName = (EditText) findViewById(R.id.eTRegisterName);
        etCoreId = (EditText) findViewById(R.id.eTRegisterCoreID);
        etPassword = (EditText)findViewById(R.id.etRegisterPassword);
        etEmail = (EditText)findViewById(R.id.eTRegsiterEmail);
        etDepartment_Code = (EditText) findViewById(R.id.eTRegisterDepartmentCode);

        final String str_Name = etName.getText().toString();
        final String str_CoreId = etCoreId.getText().toString();
        final String str_Password = etPassword.getText().toString();
        final String str_Email = etEmail.getText().toString();
        final String str_DepartmentCode = etDepartment_Code.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                hidePDialog();
                Log.d(TAG, "onRegisterSubmit: response received");
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onRegisterSubmit: try to decode content");

                    if(b_success){
                        Log.d(TAG, "onRegisterSubmit: success");

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("registerSuccess", true);

                        RegisterActivity.this.startActivity(intent);
                    }else{
                        Log.d(TAG, "onRegisterSubmit: fail");
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

        Log.d(TAG, "onRegisterSubmit: sending request");

        RegisterRequest registerRequest = new RegisterRequest(str_Name, str_CoreId, str_Password, str_Email, str_DepartmentCode, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Register...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }}
}
