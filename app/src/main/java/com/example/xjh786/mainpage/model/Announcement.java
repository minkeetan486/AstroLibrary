package com.example.xjh786.mainpage.model;

/**
 * Created by mqdg36 on 7/5/2017.
 */

public class Announcement {
    private String content, date;

    public Announcement() {
    }

    public Announcement(String content, String date) {
        this.content = content;
        this.date = date;

    }

    public String getcontent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
