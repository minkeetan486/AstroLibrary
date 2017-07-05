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
 * Created by mqdg36 on 7/5/2017.
 */

public class AnnouncementAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Announcement> announcements;

    public AnnouncementAdapter(Activity activity, List<Announcement> announcements) {
        this.activity = activity;
        this.announcements = announcements;
    }

    @Override
    public int getCount() {
        return announcements.size();
    }

    @Override
    public Object getItem(int location) {
        return announcements.get(location);
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
            convertView = inflater.inflate(R.layout.announcement_list, null);

        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        // getting movie data for the row
        Announcement m = announcements.get(position);

        content.setText(m.getcontent());

        date.setText(m.getDate());


        return convertView;
    }


}
