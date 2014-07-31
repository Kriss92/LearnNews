package com.appchee.learnews;

import android.media.Image;

import java.util.Date;

public class StoryBean {
    private String title;
    private Date date;
    private Image siteIcon;

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

    public Image getSiteIcon() {
        return siteIcon;
    }

    public void setSiteIcon(Image siteIcon) {
        this.siteIcon = siteIcon;
    }
}
