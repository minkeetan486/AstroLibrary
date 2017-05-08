package com.example.xjh786.mainpage;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PMXT36 on 5/8/2017.
 */

public class BooksRequest extends StringRequest {
    private static final String BOOKS_REQUEST_URL = "https://androidlibrary.000webhostapp.com/LibraryApp/development/book.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public BooksRequest(String coreid, Response.Listener<String> listener){
        super(Request.Method.POST, BOOKS_REQUEST_URL, listener, null);
        param = new HashMap<>();
        param.put("version", "1");
        param.put("coreid", coreid);
        param.put("action", "query");
        Log.d(TAG, "BooksRequest: created");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
