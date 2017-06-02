package com.example.xjh786.mainpage.model;

/**
 * Created by KVM768 on 5/13/2017.
 */

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
    private String title, thumbnailUrl,author,book_id;
    private int qty,year_of_publish;

    public Book() {
    }

    public Book(String title, String thumbnailUrl, int qty, int year_of_publish,
                String author, String book_id) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl; //can keep for future use
        this.qty = qty;
        this.year_of_publish = year_of_publish;
        this.author = author;
        this.book_id = book_id;
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

    public void setBook_id(String book_id) { this.book_id = book_id; }

    public String getBook_id() {return book_id;}

//start parcelable implementation
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            Book mBook = new Book();
            // The order here, has to be as same as the ordering below in writeToParcel, title,book_id,author, year_of_publish, qty, thumbnailUrl
            mBook.title = source.readString();
            mBook.book_id = source.readString();
            mBook.author = source.readString();
            mBook.year_of_publish = source.readInt();
            mBook.qty = source.readInt();
            mBook.thumbnailUrl = source.readString();
            return mBook;
        }
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        // The oder here has to be the same as the one above, title,book_id,author, year_of_publish, qty, thumbnailUrl
        parcel.writeString(title);
        parcel.writeString(book_id);
        parcel.writeString(author);
        parcel.writeInt(year_of_publish);
        parcel.writeInt(qty);
        parcel.writeString(thumbnailUrl);
    }//parcelable ends here
}
