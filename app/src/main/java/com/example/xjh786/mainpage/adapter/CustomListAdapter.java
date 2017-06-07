package com.example.xjh786.mainpage.adapter;

/**
 * Created by KVM768 on 5/12/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.xjh786.mainpage.R;
import com.example.xjh786.mainpage.app.AppController;
import com.example.xjh786.mainpage.model.Book;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Book> books;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Book> books) {
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
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView year_of_publish = (TextView) convertView.findViewById(R.id.year_of_publish);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        TextView book_id = (TextView) convertView.findViewById(R.id.book_id);

        // getting movie data for the row
        Book m = books.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader); // to be used later if possible

        // title
        title.setText(m.getTitle());

        // Author
        author.setText("Author: " + String.valueOf(m.getAuthor()));

        // year of publish
        year_of_publish.setText("Published: " + String.valueOf(m.getYear()));

        // qty
        qty.setText("Available: " + String.valueOf(m.getqty()));
        //qty.setTextColor("#ff0000");

        book_id.setText("ID:" + String.valueOf(m.getBook_id()));

        return convertView;
    }

}