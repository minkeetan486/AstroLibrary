package com.example.xjh786.mainpage;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LibraryList extends AppCompatActivity {
    private static final String TAG = "LibraryApp";
    private EditText etCoreId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);

    }

    public void onClickSearchBooks(View view)
    {
        etCoreId = (EditText)findViewById(R.id.coreID);

        final String str_CoreId = etCoreId.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onClickSearchBooks: receive response");

                    if(b_success){
                        Log.d(TAG, response);

                    }else{
                        Log.d(TAG, response);
                        Log.d(TAG, "onClickSearchBooks: No results");
                        AlertDialog.Builder builder = new AlertDialog.Builder(LibraryList.this);
                        builder.setMessage("Nothing found..")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Log.d(TAG, "onClickSearchBooks: sending request");

        LibraryListRequest libraryListReq = new LibraryListRequest(str_CoreId, responseListener);//remove core id and get from intent
        RequestQueue queue = Volley.newRequestQueue(LibraryList.this);
        queue.add(libraryListReq);
    }
}
