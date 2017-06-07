package com.example.xjh786.mainpage;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.xjh786.mainpage.app.AppController;
import com.example.xjh786.mainpage.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDetails extends AppCompatActivity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Book mBook;
    private static final String TAG = "LibraryApp.BookDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        mBook = (Book)getIntent().getParcelableExtra(LibraryList.PAR_KEY);
        if (imageLoader == null)
        {imageLoader = AppController.getInstance().getImageLoader();}

        NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.thumbnail);
        TextView title = (TextView) findViewById(R.id.title);
        TextView author = (TextView) findViewById(R.id.author);
        TextView year_of_publish = (TextView) findViewById(R.id.year_of_publish);
        TextView qty = (TextView) findViewById(R.id.qty);
        TextView book_id = (TextView) findViewById(R.id.book_id);

        // thumbnail image
        Log.d(TAG,"URL is:"+ mBook.getThumbnailUrl());
        thumbNail.setImageUrl(mBook.getThumbnailUrl(), imageLoader); // to be used later if possible

        // title
        title.setText("Book Title:" + mBook.getTitle());

        // Author
        author.setText("Author: " + String.valueOf(mBook.getAuthor()));

        // year of publish
        year_of_publish.setText("Published: " + String.valueOf(mBook.getYear()));

        // qty
        qty.setText("Available: " + String.valueOf(mBook.getqty()));

        book_id.setText("ID:" + String.valueOf(mBook.getBook_id()));
    }

    public void onClickBorrowBook(View view){
        BorrowBooks("kvm768", mBook.getBook_id());
    }

    public void BorrowBooks(String etCoreId, String Book_ID)
    {
        final String str_CoreId = etCoreId;
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onClickSearchBooks: receive response");

                    if(b_success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetails.this);
                        builder.setMessage("Borrow Successful!.")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();
                    }
                    else
                    {
                        Log.d(TAG, response);
                        Log.d(TAG, "onClickBorrow: Borrow Failed");
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetails.this);
                        builder.setMessage(jsonResponse.getString("error_detail"))
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        Log.d(TAG, "onBorrowBooks: sending request");

        BorrowRequest borrowBook = new BorrowRequest(str_CoreId,Book_ID,responseListener);//remove core id and get from intent
        RequestQueue queue = Volley.newRequestQueue(BookDetails.this);
        queue.add(borrowBook);

    }

}
