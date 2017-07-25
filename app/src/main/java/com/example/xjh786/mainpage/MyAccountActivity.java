package com.example.xjh786.mainpage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.xjh786.mainpage.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAccountActivity extends AppCompatActivity{

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

        getAccountDetails();

    }

    public void getAccountDetails(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

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
                                book.setBook_id(obj.getString("Transaction_ID"));
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
                                book.setReturnDate(obj.getString("Returned_Date"));
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

    public void returnBook(String transid){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                hidePDialog();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "return Book: receive response " + response.toString());

                    String result = "";
                    if(b_success){
                        result = jsonResponse.getString("result");
                        AlertDialog.Builder alert = new AlertDialog.Builder(MyAccountActivity.this);
                        alert.setMessage(result);

                        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                bookList.clear();
                                getAccountDetails();
                            }
                        });

                        alert.show();
                    }else{
                        result = jsonResponse.getString("error_detail");
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyAccountActivity.this);
                        builder.setMessage(result)
                                .setPositiveButton("OK", null)
                                .create()
                                .show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Log.d(TAG, "return book sending request");

        ReturnBookRequest returnRequest = new ReturnBookRequest(transid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyAccountActivity.this);
        queue.add(returnRequest);
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


    public class MyAccountListAdapter extends BaseAdapter {
        private Context activity;
        private LayoutInflater inflater;
        private List<Book> books;

        public MyAccountListAdapter(Context activity, List<Book> books) {
            this.activity = activity;
            this.books = books;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int location) {
            return books.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.my_account_list_item, null);

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView author = (TextView) convertView.findViewById(R.id.author);
            TextView borrowDate = (TextView) convertView.findViewById(R.id.borrowDate);
            TextView dueDate = (TextView) convertView.findViewById(R.id.dueDate);
            Button returnBook = (Button) convertView.findViewById(R.id.returnBook);

            // getting movie data for the row
            final Book m = books.get(position);

            title.setText("Title: " + m.getTitle());
            author.setText("Author: " + m.getAuthor());
            borrowDate.setText("Borrowed Date: " + m.getBorrowDate());

            if(m.getBorrowStatus().equals("history")){
                returnBook.setVisibility(View.GONE);
                dueDate.setText("Returned Date: " + m.getReturnDate());

            }else{
                returnBook.setVisibility(View.VISIBLE);
                dueDate.setText("Due Date: " + m.getDueDate());

            }

            returnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    returnBook(m.getBook_id());
                    Log.d(TAG, "book id " + m.getBook_id());


                }
            });

            return convertView;
        }
    }
}
