package com.appchee.learnews.beans;

/**
 * Created by demouser on 7/31/14.
 */
public class AnswerBean {

    Integer id;
    Integer questionId;
    String answer;
    Boolean correct;

    public AnswerBean() {}

    public AnswerBean(String answer, Boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public void setCorrect(Integer correct) {
        if (correct == 0) {
            setCorrect(false);
        }
        setCorrect(true);
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
