package com.example.xjh786.mainpage;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KVM768 on 5/6/2017.
 */

public class LibraryListRequest extends StringRequest {
    private static final String book_req_url = "https://androidlibrary.000webhostapp.com/LibraryApp/development/book.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public LibraryListRequest(String coreid, Response.Listener<String> listener){
        super(Method.POST, book_req_url, listener, null);
        param = new HashMap<>();
        param.put("version", "1");
        param.put("coreid", coreid);
        param.put("action","query");//temp for now
        Log.d(TAG, "BookQuery: created");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}