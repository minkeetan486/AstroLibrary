package com.example.xjh786.mainpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.HashMap; //jtwj
import android.text.TextWatcher;//jtwj
import android.widget.AdapterView.OnItemClickListener;//jtwj
import java.util.Locale; //jtwj
import android.text.Editable; //jtwj
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibraryList extends AppCompatActivity {
    private static final String TAG = "LibraryApp";
    private String etCoreId;
    //private List<Book> bookList = new ArrayList<Book>();
    private List<Book> bookList = new ArrayList<Book>();
    ArrayList<Book> arrayList = new ArrayList<Book>();
    private ListView listView;
    private CustomListAdapter adapter;
    private ProgressDialog pDialog;
    EditText inputSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);
        listView = (ListView) findViewById(R.id.list);

        Log.d(TAG, "Getbooks");
        GetBooks("vbp687");// hardcoded as well, need to get from the login intent
        adapter = new CustomListAdapter(this, arrayList);
        Log.d(TAG, "Arraylist" + arrayList);
        //adapter = new CustomListAdapter(this, bookList);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        inputSearch = (EditText) findViewById(R.id.inputSearch); //jtwj

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Log.d(TAG, "User input...filtering");

                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                arrayList.clear();

                if (text.length() == 0) {
                    //arrayList.clear();
                    arrayList.addAll();
                    Log.d(TAG, "Length is 0 - enter case 0");
                    GetBooks("vbp687");
                    Log.d(TAG, "Arraylist is " + bookList );
                } else {
                    arrayList.clear();
                    Log.d(TAG, "Length is not 0 and Arraylist is " + bookList );
                    GetFilterBooks("vbp687", text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        //need to make this better if possible
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object book_det = listView.getItemAtPosition(position);
                Book book_at=(Book)book_det;//
                Toast.makeText(getBaseContext(),book_at.getTitle(),Toast.LENGTH_SHORT).show(); // will work on intent from here
            }
        });
    }

    public void onClickSearchBooks(View view)
    {

    }

    public void onClickBook(AdapterView<?> parent, View v, int position)
    {
        Log.d(TAG, "Postion:" + position);
    }
    public void GetBooks(String etCoreId )
    {
        final String str_CoreId = etCoreId;
        Log.d(TAG, "Retrieving book");

       Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                //Log.d(TAG, "Retrieving book");
                hidePDialog();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    //Log.d(TAG, "onClickSearchBooks: receive response");

                    if(b_success){
                        Log.d(TAG, response);
                        JSONArray books_array = jsonResponse.getJSONArray("result");

                        for (int i = 0; i < books_array.length(); i++)
                        {
                            try {
                                JSONObject obj = books_array.getJSONObject(i);
                                Log.d(TAG, "book_array" + i + " is " + books_array.getJSONObject(i));
                                //Book book = new Book(title, thumbnailUrl, qty ,year_of_publish, author);
                                //Book book = new Book();
                                String title = obj.getString("Title");
                                Log.d(TAG, "Title" +  title);
                                String thumbnailUrl ="";

                                String author = obj.getString("Author");
                                Log.d(TAG, "Author" + author);
                                Integer qty = (int) obj.get("Num_of_Available");
                                Log.d(TAG, "Num_of_Available" + qty);
                                Integer year = (int) obj.get("Year_of_Publish");
                                Log.d(TAG, "Year_of_Publish" + year);

                                Book book = new Book(title, thumbnailUrl, qty ,year, author);

                                book.setTitle(obj.getString("Title"));
                                book.setAuthor(obj.getString("Author"));
                                book.setThumbnailUrl("https://img.clipartfest.com/25920a48a380e6c47e5c56da10ee44a9_no-image-available-clip-art-no-image-available-clipart_300-300.png");//hardcode for the time being
                                book.setYear((int) obj.get("Year_of_Publish"));
                                book.setqty((int) obj.get("Num_of_Available"));

                                //bookList.add(book);
                                arrayList.add(book);
                                Log.d(TAG, "Arraylist" + books_array);
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


        //Log.d(TAG, "onClickSearchBooks: sending request");

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

    public void GetFilterBooks(String etCoreId , String FilterText)
    {
        final String str_CoreId = etCoreId;
        final String FText = FilterText;

        Log.d(TAG, "Retrieving book");

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                //Log.d(TAG, "Retrieving book");
                hidePDialog();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    //Log.d(TAG, "onClickSearchBooks: receive response");

                    if(b_success){
                        Log.d(TAG, response);
                        JSONArray books_array = jsonResponse.getJSONArray("result");

                        for (int i = 0; i < books_array.length(); i++)
                        {
                            try {
                                JSONObject obj = books_array.getJSONObject(i);
                                Log.d(TAG, "book_array" + i + " is " + books_array.getJSONObject(i));

                                //Book book = new Book();
                                String title = obj.getString("Title");
                                Log.d(TAG, "Title" +  title);
                                String thumbnailUrl ="";

                                String author = obj.getString("Author");
                                Log.d(TAG, "Author" + author);
                                Integer qty = (int) obj.get("Num_of_Available");
                                Log.d(TAG, "Num_of_Available" + qty);
                                Integer year = (int) obj.get("Year_of_Publish");
                                Log.d(TAG, "Year_of_Publish" + year);

                                Book book = new Book(title, thumbnailUrl, qty ,year, author);

                                if (book.getTitle().toLowerCase(Locale.getDefault()).contains(FText)){

                                        book.setTitle(obj.getString("Title"));
                                        book.setAuthor(obj.getString("Author"));
                                        book.setThumbnailUrl("https://img.clipartfest.com/25920a48a380e6c47e5c56da10ee44a9_no-image-available-clip-art-no-image-available-clipart_300-300.png");//hardcode for the time being
                                        book.setYear((int) obj.get("Year_of_Publish"));
                                        book.setqty((int) obj.get("Num_of_Available"));

                                        //bookList.add(book);
                                        arrayList.add(book);
                                        Log.d(TAG, "Arraylist" + arrayList);}
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


        //Log.d(TAG, "onClickSearchBooks: sending request");

        LibraryListRequest libraryListReq = new LibraryListRequest(str_CoreId, responseListener);//remove core id and get from intent
        RequestQueue queue = Volley.newRequestQueue(LibraryList.this);
        queue.add(libraryListReq);


    }
}
