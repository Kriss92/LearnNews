package com.appchee.learnews.actions;

import android.content.Context;
import android.database.DatabaseUtils;
import android.util.Log;

import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.database.DbInteractions;
import com.appchee.learnews.database.LearNewsDbHelper;

import java.util.Random;

/**
 * Created by demouser on 7/31/14.
 */
public class QuestionsManager {

    private Context mContext;
    private Random mRandom;
    DbInteractions mDbHelper;

    public QuestionsManager(Context context) {
        mContext = context;
        mRandom = new Random();
        mDbHelper = new DbInteractions(mContext);
    }

    public QuestionBean getNextQuestion() {


        Long numQueries = DatabaseUtils.queryNumEntries(mDbHelper.getDBHelper().getReadableDatabase(),
                LearNewsDbHelper.QUESTIONS_TABLE);

        if (numQueries < 1) {
            return new QuestionBean();
        }
        Integer nextQuestionNum =  mRandom.nextInt(numQueries.intValue());

        Log.d("Question num", "All queries " + numQueries
                + " Num: " + nextQuestionNum.toString());
        return mDbHelper.getQuestionByNumber(nextQuestionNum);
    }

    public void reportQuestion(QuestionBean question) {
        mDbHelper.deleteQuestion(question);
    }

    public Double answerQuestion(QuestionBean question, boolean correct) {
        mDbHelper.updateInteractions(question, correct);
        return mDbHelper.Interactions(question);
    }

    public void saveStrory(QuestionBean mCurrentQuestionBean) {
        mDbHelper.saveStory(mCurrentQuestionBean);
    }

    public void updateInteraction(QuestionBean question) {
        mDbHelper.createInteraction(question);
    }



    //TODO:
    // validation using unsafe words

}
