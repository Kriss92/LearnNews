package com.appchee.learnews.actions;

import android.content.Context;
import android.database.DatabaseUtils;
import android.util.Log;

import com.appchee.learnews.backend.WebClient;
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
    public static Integer currentQuestionNum;

    public QuestionsManager(Context context) {
        mContext = context;
        mRandom = new Random();
        mDbHelper = new DbInteractions(mContext);
    }

    public QuestionBean getNextQuestion() {

        Long numQueries = DatabaseUtils.queryNumEntries(mDbHelper.getDBHelper().getReadableDatabase(),
                LearNewsDbHelper.QUESTIONS_TABLE);

        if (numQueries < 1) {
            return null;
        }

        Integer nextQuestionNum =  mRandom.nextInt(numQueries.intValue());

        while(currentQuestionNum == nextQuestionNum && numQueries > 1) {
            nextQuestionNum =  mRandom.nextInt(numQueries.intValue());
        }

        currentQuestionNum = nextQuestionNum;

        Log.d("Question num", "All queries " + numQueries
                + " Num: " + nextQuestionNum.toString());
        return mDbHelper.getQuestionByNumber(nextQuestionNum);
    }

    public void reportQuestion(final QuestionBean question) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebClient webc = new WebClient();
                webc.reportQuestion(question.getId());
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void deleteQuestion(QuestionBean question) {
        mDbHelper.deleteQuestion(question);
    }
    public void saveStory(QuestionBean mCurrentQuestionBean) {
        mDbHelper.saveStory(mCurrentQuestionBean);
    }

    //TODO:
    // validation using unsafe words

}
