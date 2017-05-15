package com.example.xjh786.mainpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.xjh786.mainpage.app.AppController;
import com.example.xjh786.mainpage.model.Book;

public class BookDetails extends AppCompatActivity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private static final String TAG = "LibraryApp.BookDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Book mBook = (Book)getIntent().getParcelableExtra(LibraryList.PAR_KEY);
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

}
