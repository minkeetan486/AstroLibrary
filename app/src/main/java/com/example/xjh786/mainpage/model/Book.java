package com.example.xjh786.mainpage.model;

/**
 * Created by KVM768 on 5/13/2017.
 */

import java.util.ArrayList;

public class Book {
    private String title, thumbnailUrl,author;
    private int qty,year_of_publish;

    //public Book() {
    //}

    public Book(String title, String thumbnailUrl, int qty, int year_of_publish,
                String author) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl; //can keep for future use
        this.qty = qty;
        this.year_of_publish = year_of_publish;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getYear() {
        return year_of_publish;
    }

    public void setYear(int year) {
        this.year_of_publish = year;
    }

    public int getqty() {
        return qty;
    }

    public void setqty(int qty) {
        this.qty = qty;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
