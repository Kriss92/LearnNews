package com.appchee.learnews.beans;

import android.util.Log;

import com.appchee.learnews.validation.UnsafeContentValidator;
import com.appchee.learnews.validation.UrlValidator;
import com.appchee.learnews.validation.ValidationException;

import java.sql.Timestamp;
import java.util.List;

public class RecentQuestionBean {

    // public static enum categories = {};

    Integer mId;
    String mQuestion;
    String mCategory;
    String mAnswer1;
    String mAnswer2;
    String mAnswer3;
    String mAnswer4;
    int mCorrectIndex;
    int mSelectedIndex;
    String mNewsURL;
    String mDateAdded;
    long mDateAnswered;
    float mRating;

    public RecentQuestionBean() { }

    public RecentQuestionBean(Integer id, String question, String answer1, String answer2,
                        String answer3, String answer4, int correctIndex, int selectedIndex,
                        String newsURL, String category, String dateAdded,
                        long dateAnswered, float rating) {

        this.mId = id;
        this.mQuestion = question;
        this.mCategory = category;
        this.mAnswer1 = answer1;
        this.mAnswer2 = answer2;
        this.mAnswer3 = answer3;
        this.mAnswer4 = answer4;
        this.mCorrectIndex = correctIndex;
        this.mSelectedIndex = selectedIndex;
        this.mNewsURL = newsURL;
        this.mDateAdded = dateAdded;
        this.mDateAnswered = dateAnswered;
        this.mRating = rating;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getNewsURL() {
        return mNewsURL;
    }

    public void setNewsURL(String newsURL) {
        this.mNewsURL = newsURL;
    }

    public String getAnswer1() {
        return mAnswer1;
    }

    public void setAnswer1(String mAnswer1) {
        this.mAnswer1 = mAnswer1;
    }

    public String getAnswer2() {
        return mAnswer2;
    }

    public void setAnswer2(String mAnswer2) {
        this.mAnswer2 = mAnswer2;
    }

    public String getAnswer3() {
        return mAnswer3;
    }

    public void setAnswer3(String mAnswer3) {
        this.mAnswer3 = mAnswer3;
    }

    public String getAnswer4() {
        return mAnswer4;
    }

    public void setAnswer4(String mAnswer4) {
        this.mAnswer4 = mAnswer4;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int mSelectedIndex) {
        this.mSelectedIndex = mSelectedIndex;
    }

    public int getCorrectIndex() {
        return mCorrectIndex;
    }

    public void setCorrectIndex(int mCorrectIndex) {
        this.mCorrectIndex = mCorrectIndex;
    }

    public long getDateAnswered() {
        return mDateAnswered;
    }

    public void setDateAnswered(long mDateAnswered) {
        this.mDateAnswered = mDateAnswered;
    }

    public String getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(String mDateAdded) {
        this.mDateAdded = mDateAdded;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float mRating) {
        this.mRating = mRating;
    }

    public void validate() throws ValidationException {
        UnsafeContentValidator validator = new UnsafeContentValidator();
        validator.validate(mQuestion);
        validator.validate(mAnswer1);
        validator.validate(mAnswer2);
        validator.validate(mAnswer3);
        validator.validate(mAnswer4);
        new UrlValidator().validate(mNewsURL);
    }
}
