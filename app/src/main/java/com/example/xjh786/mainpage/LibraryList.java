package com.example.xjh786.mainpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.xjh786.mainpage.model.Book;
import com.example.xjh786.mainpage.adapter.CustomListAdapter;
import com.example.xjh786.mainpage.app.AppController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibraryList extends AppCompatActivity  {
    private static final String TAG = "LibraryApp";
    private String etCoreId;
    private List<Book> bookList = new ArrayList<Book>();
    private ListView listView;
    private CustomListAdapter adapter;
    private ProgressDialog pDialog;
    public final static String PAR_KEY = "package com.example.xjh786.mainpage.model.PAR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, bookList);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        GetBooks("kvm768");// hardcoded as well, need to get from the login intent

        //need to make this better if possible
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Book book_at = new Book();
                Object book_det = listView.getItemAtPosition(position);
                book_at=(Book)book_det;
                Intent intent = new Intent(LibraryList.this, BookDetails.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable(PAR_KEY, book_at);
                intent.putExtras(mBundle);
                LibraryList.this.startActivity(intent);

                //Toast.makeText(getBaseContext(),book_at.getTitle(),Toast.LENGTH_SHORT).show(); // will work on intent from here
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSearchBooks(View view)
    {

    }

    public void onClickBook(AdapterView<?> parent, View v, int position)
    {
        Log.d(TAG, "Postion:" + position);
    }
    public void GetBooks(String etCoreId)
    {
        final String str_CoreId = etCoreId;
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                hidePDialog();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onClickSearchBooks: receive response");

                    if(b_success){
                        //Log.d(TAG, response);
                        JSONArray books_array = jsonResponse.getJSONArray("result");

                        for (int i = 0; i < books_array.length(); i++)
                        {
                            try {
                                JSONObject obj = books_array.getJSONObject(i);
                                Book book = new Book();
                                book.setTitle(obj.getString("Title"));
                                book.setBook_id(obj.getString("BookInfo_ID"));
                                book.setAuthor(obj.getString("Author"));
                                book.setThumbnailUrl("https://img.clipartfest.com/25920a48a380e6c47e5c56da10ee44a9_no-image-available-clip-art-no-image-available-clipart_300-300.png");//hardcode for the time being
                                book.setYear((int) obj.get("Year_of_Publish"));
                                book.setqty((int) obj.get("Num_of_Available"));
                                bookList.add(book);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            }
                        adapter.notifyDataSetChanged(); // update the listview
                    }
                    else
                    {
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }}
}
