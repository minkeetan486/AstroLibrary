package com.example.xjh786.mainpage;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MQDG36 on 7/21/2017.
 */

public class ReturnBookRequest extends StringRequest {
    private static final String REQUEST_URL = "https://androidlibrary.000webhostapp.com/LibraryApp/development/book.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public ReturnBookRequest(String transid, Response.Listener<String> listener){
        super(Request.Method.POST, REQUEST_URL, listener, null);
        param = new HashMap<>();
        param.put("version", "1");
        param.put("transid", transid);
        param.put("action", "return");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
