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

    public QuestionsManager(Context context) {
        mContext = context;
        mRandom = new Random();
    }

    public QuestionBean getNextQuestion() {
        QuestionBean result = null;
        DbInteractions dbHelper = new DbInteractions(mContext);

        Long numQueries = DatabaseUtils.queryNumEntries(dbHelper.getDBHelper().getReadableDatabase(),
                LearNewsDbHelper.QUESTIONS_TABLE, null);

        Integer nextQuestionNum =  mRandom.nextInt(numQueries.intValue());

        Log.d("Question num", "All queries " + numQueries
                + " Num: " + nextQuestionNum.toString());
        dbHelper.getQuestionByNumber(nextQuestionNum);

        return result;
    }

    public Double answerQuestion(QuestionBean question, boolean correct) {
        //Update corrects/incorrects count

        //return percentage correct
        return 0.0;
    }

    //TODO:
    // validation using unsafe words

}
