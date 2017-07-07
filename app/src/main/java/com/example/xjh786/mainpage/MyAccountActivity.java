package com.example.xjh786.mainpage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.xjh786.mainpage.adapter.CustomListAdapter;
import com.example.xjh786.mainpage.adapter.MyAccountListAdapter;
import com.example.xjh786.mainpage.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAccountActivity extends AppCompatActivity {

    private static final String TAG = "LibraryApp";
    String str_CoreId, str_Password;
    private List<Book> bookList = new ArrayList<Book>();
    private ListView listView;
    private MyAccountListAdapter adapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView myAccountFullNameTextView = (TextView)findViewById(R.id.myAccountFullName);
        myAccountFullNameTextView.setText("Full Name : " + MainActivity.str_fullName);
        TextView myAccountEmailTextView = (TextView)findViewById(R.id.myAccountEmail);
        myAccountEmailTextView.setText("Email : " + MainActivity.str_email);
        TextView myAccountDepartmentTextView = (TextView)findViewById(R.id.myAccountDepartment);
        myAccountDepartmentTextView.setText("Department : " + MainActivity.str_dept);

        listView = (ListView) findViewById(R.id.history);
        adapter = new MyAccountListAdapter(this, bookList);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        //START--- Work In Progress: Hardcode for now...
      //  str_CoreId = "vbp687";
      //  str_Password = "vbp687";
        //END----- Work In Progress: Hardcode for now...

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                hidePDialog();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onCreate MyAccount: receive response " + response.toString());

                    if(b_success){
                        Log.d(TAG, "onCreate MyAccount: success");
                        JSONObject result = jsonResponse.getJSONObject("result");

                            JSONArray books_array = result.getJSONArray("pending");

                            for (int i = 0; i < books_array.length(); i++) {
                                try {
                                    JSONObject obj = books_array.getJSONObject(i);
                                    Book book = new Book();
                                    book.setTitle(obj.getString("Title"));
                                    book.setAuthor(obj.getString("Author"));
                                    book.setBorrowDate(obj.getString("Borrowed_Date"));
                                    book.setDueDate(obj.getString("Due_Date"));
                                    book.setBorrowStatus("pending");
                                    bookList.add(book);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }



                            JSONArray history_array = result.getJSONArray("history");
                            for (int j = 0; j < history_array.length(); j++) {
                                try {
                                    JSONObject obj = history_array.getJSONObject(j);
                                    Book book = new Book();
                                    book.setTitle(obj.getString("Title"));
                                    book.setAuthor(obj.getString("Author"));
                                    book.setBorrowDate(obj.getString("Borrowed_Date"));
                                    book.setBorrowStatus("history");
                                    bookList.add(book);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        adapter.notifyDataSetChanged(); // update the listview


                    }else{
                        Log.d(TAG, "onCreate MyAccount: fail");
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyAccountActivity.this);
                        builder.setMessage("Failed getting My Account info")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Log.d(TAG, "onCreate MyAccount: sending request");

        MyAccountRequest myAccountRequest = new MyAccountRequest(MainActivity.str_coreId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyAccountActivity.this);
        queue.add(myAccountRequest);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
