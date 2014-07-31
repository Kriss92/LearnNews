package com.appchee.learnews.beans;

import java.util.List;

/**
 * Created by demouser on 7/31/14.
 */
public class QuestionBean {

   // public static enum categories = {};

    Integer id;
    String question;
    String category;
    List<AnswerBean> answers;
    String newsURL;

    public QuestionBean() { }

    public QuestionBean(Integer id, String question, List<AnswerBean> answers, String newsURL, String category) {
        this.newsURL = newsURL;
        this.id = id;
        this.question = question;
        this.category = category;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<AnswerBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerBean> answers) {
        this.answers = answers;
    }


    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public Integer getCorrectAnswer() {
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getCorrect()) {
                return i;
            }
        }
        return 0;
    }
}
