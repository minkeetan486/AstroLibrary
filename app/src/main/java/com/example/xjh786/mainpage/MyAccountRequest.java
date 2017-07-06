package com.example.xjh786.mainpage;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jcf674 on 4/21/2017.
 */

public class MyAccountRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://androidlibrary.000webhostapp.com/LibraryApp/development/account.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public MyAccountRequest(String coreid, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        param = new HashMap<>();
        param.put("version", "1");
        param.put("coreid", coreid);
        param.put("action", "view");
        Log.d(TAG, "MyAccountRequest: created");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}

