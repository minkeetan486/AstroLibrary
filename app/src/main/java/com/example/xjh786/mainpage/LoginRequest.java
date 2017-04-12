package com.example.xjh786.mainpage;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by WBF486 on 4/11/2017.
 */

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://androidlibrary.000webhostapp.com/LibraryApp/development/login.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public LoginRequest(String coreid, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        param = new HashMap<>();
        param.put("version", "1");
        param.put("coreid", coreid);
        param.put("password", password);
        Log.d(TAG, "LoginRequest: created");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}