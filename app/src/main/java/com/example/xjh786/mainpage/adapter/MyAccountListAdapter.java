package com.example.xjh786.mainpage.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xjh786.mainpage.R;
import com.example.xjh786.mainpage.model.Announcement;
import com.example.xjh786.mainpage.model.Book;

import java.util.List;

/**
 * Created by mqdg36 on 7/6/2017.
 */

public class MyAccountListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Book> books;

    public MyAccountListAdapter(Activity activity, List<Book> books) {
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

        // getting movie data for the row
        Book m = books.get(position);

        title.setText(m.getTitle());
        author.setText(m.getAuthor());
        borrowDate.setText(m.getBorrowDate());
        dueDate.setText(m.getDueDate());


        return convertView;
    }
}
