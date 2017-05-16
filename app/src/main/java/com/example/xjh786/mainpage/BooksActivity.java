package com.example.xjh786.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class BooksActivity extends AppCompatActivity {

    private static final String TAG = "LibraryApp";
    private static Response.Listener<String> responseListener;
    private String str_CoreId;
    ArrayList<String> books = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your search book function action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        books.add("Lord of the rings");
        books.add("Harry Potter");
        ListView lv = (ListView)findViewById(R.id.booksListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.content_books, books);
        if (lv != null){
            lv.setAdapter(adapter);
        }

        responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    Log.d(TAG, "onClickLogin: receive response");
                    Log.d(TAG, response);


                    /*TextView content = (TextView) findViewById(R.id.booksContentTxt);
                    if (content != null){
                        content.setText(response);
                    }*/

                    JSONArray arr = jsonResponse.getJSONArray("result");

                    for (int i = 0; i < arr.length(); i++){
                        Log.d(TAG,arr.getString(i));
                        JSONObject book = new JSONObject(arr.getString(i));
                        // books.add(book.getString("Title"));
                    }

                    for (String s : books) {
                        Log.d(TAG,s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Intent intent = getIntent();
        str_CoreId = intent.getStringExtra("coreId");

        loadBooks();

    }


    private void loadBooks()
    {
        Log.d(TAG, "loadBooks(): sending request");
        BooksRequest booksRequest = new BooksRequest(str_CoreId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BooksActivity.this);
        queue.add(booksRequest);
    }
}
