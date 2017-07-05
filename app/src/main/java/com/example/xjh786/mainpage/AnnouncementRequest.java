package com.example.xjh786.mainpage;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mqdg36 on 7/5/2017.
 */

public class AnnouncementRequest extends StringRequest {
    private static final String ann_req_url = "https://androidlibrary.000webhostapp.com/LibraryApp/development/announcement.php";
    private Map<String, String> param;
    private static final String TAG = "LibraryApp";

    public AnnouncementRequest(String coreid, Response.Listener<String> listener){
        super(Request.Method.POST, ann_req_url, listener, null);
        param = new HashMap<>();
        param.put("version", "1");
        param.put("coreid", coreid);
        param.put("action", "retrieve");

    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
