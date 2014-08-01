package com.appchee.learnews.beans;

import android.util.Log;

import com.appchee.learnews.validation.UnsafeContentValidator;
import com.appchee.learnews.validation.UrlValidator;
import com.appchee.learnews.validation.ValidationException;

import java.util.List;

/**
 * Created by demouser on 7/31/14.
 */
public class QuestionBean {

   // public static enum categories = {};

    Integer mId;
    String mQuestion;
    String mCategory;
    List<AnswerBean> mAnswers;
    String mNewsURL;

    public QuestionBean() { }

    public QuestionBean(Integer id, String question, List<AnswerBean> answers, String newsURL, String category) {
        this.mNewsURL = newsURL;
        this.mId = id;
        this.mQuestion = question;
        this.mCategory = category;
        this.mAnswers = answers;
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

    public List<AnswerBean> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(List<AnswerBean> answers) {
        this.mAnswers = answers;
    }


    public String getNewsURL() {
        return mNewsURL;
    }

    public void setNewsURL(String newsURL) {
        this.mNewsURL = newsURL;
    }

    public Integer getCorrectAnswer() {
        for (int i = 0; i < mAnswers.size(); i++) {
            if (mAnswers.get(i).getCorrect()) {
                return i;
            }
        }
        return 0;
    }

    public void validate() throws ValidationException {
        UnsafeContentValidator validator = new UnsafeContentValidator();
        validator.validate(mQuestion);
        for(AnswerBean answer : mAnswers) {
            validator.validate(answer.getAnswer());
        }

        new UrlValidator().validate(mNewsURL);
    }
}
