package com.example.wbf486.androidlibrary;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WBF486 on 4/12/2017.
 */

public class RegisterRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "https://androidlibrary.000webhostapp.com/LibraryApp/development/register.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public RegisterRequest(String name, String coreid, String password, String email, String department_code, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        param = new HashMap<>();

        param.put("name", name);
        param.put("coreid", coreid);
        param.put("password", password);
        param.put("email", email);
        param.put("department_code", department_code);

        Log.d(TAG, "RegisterRequest: created");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
