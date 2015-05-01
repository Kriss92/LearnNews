package com.appchee.learnews.beans;

import android.util.Log;

import com.appchee.learnews.validation.UnsafeContentValidator;
import com.appchee.learnews.validation.UrlValidator;
import com.appchee.learnews.validation.ValidationException;

import java.sql.Timestamp;
import java.util.List;

public class RatingBean {

    Integer mQuestionId;
    Integer mUserId;
    float mRating;

    public RatingBean() {
    }

    public RatingBean(Integer questionId, Integer userId, float rating) {
        this.mQuestionId = questionId;
        this.mUserId = userId;
        this.mRating = rating;
    }

    public Integer getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(Integer mQuestionId) {
        this.mQuestionId = mQuestionId;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float mRating) {
        this.mRating = mRating;
    }

}

