package com.appchee.learnews.beans;

/**
 * Created by demouser on 7/31/14.
 */
public class AnswerBean {

    Integer mId;
    Integer mQuestionId;
    String mAnswer;
    Boolean mCorrect = false;

    public AnswerBean() {}

    public AnswerBean(String answer, Boolean correct) {
        this.mAnswer = answer;
        this.mCorrect = correct;
    }

    public Integer getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(Integer questionId) {
        this.mQuestionId = questionId;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    public Boolean getCorrect() {
        return mCorrect;
    }

    public void setCorrect(Boolean correct) {
        this.mCorrect = correct;
    }

    public void setCorrect(Integer correct) {
        if (correct == 0) {
            setCorrect(false);
        }
        setCorrect(true);
    }

    public Integer getId() {

        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }
}
