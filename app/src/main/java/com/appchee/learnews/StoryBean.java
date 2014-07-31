package com.appchee.learnews;

import android.media.Image;

import java.util.Date;

public class StoryBean {
    private String title; // story headline
    private Date date; // date when the story was saved
    private String siteIconUrl; // icon of the story source

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

    public String getSiteIconUrl() {
        return siteIconUrl;
    }

    public void setSiteIconUrl(String url) {
        this.siteIconUrl = url;
    }
}
