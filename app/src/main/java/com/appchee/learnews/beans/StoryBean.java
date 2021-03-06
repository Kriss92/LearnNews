package com.appchee.learnews.beans;

import android.media.Image;
import android.net.Uri;

import java.net.URL;
import java.util.Date;

public class StoryBean {
    private String title; // story headline
    private Date date; // date when the story was saved
    private int mNewsIconId; // icon of the story source

    public void setUri(Uri Uri) {
        this.mUri = Uri;
    }

    private Uri mUri;

    public Uri getUri() {
        return mUri;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNewsIconId() {
        return mNewsIconId;
    }

    public void setNewsIconId(int id) {
        this.mNewsIconId = id;
    }
}
