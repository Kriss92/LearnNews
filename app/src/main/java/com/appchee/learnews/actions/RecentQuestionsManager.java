package com.appchee.learnews.actions;

import android.content.Context;
import android.database.DatabaseUtils;
import android.util.Log;

import com.appchee.learnews.backend.WebClient;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.RecentQuestionBean;
import com.appchee.learnews.database.DbInteractions;
import com.appchee.learnews.database.LearNewsDbHelper;

import java.util.Random;

/**
 * Created by demouser on 7/31/14.
 */
public class RecentQuestionsManager {

    private Context mContext;
    DbInteractions mDbHelper;
    public static Integer currentRecentQuestionNum;
    public static final int MAX_RECENT_QUESTIONS = 2;

    public RecentQuestionsManager(Context context) {
        mContext = context;
        mDbHelper = new DbInteractions(mContext);
    }


    public Long getNumberOfRecentQuestions() {
        return DatabaseUtils.queryNumEntries(mDbHelper.getDBHelper().getReadableDatabase(),
                LearNewsDbHelper.RECENT_QUESTIONS_TABLE);
    }

    public RecentQuestionBean getNextRecentQuestion() {

        Long numQueries = getNumberOfRecentQuestions();

        if (numQueries < 1) {
            return null;
        }

        currentRecentQuestionNum++;

        Log.d("Question num", "All recent questions " + numQueries
                + " Num: " + currentRecentQuestionNum.toString());
        return mDbHelper.getRecentQuestionByNumber(currentRecentQuestionNum);
    }

    public RecentQuestionBean getRecentQuestionBeanByNumber(Integer number) {
        return mDbHelper.getRecentQuestionByNumber(number);
    }

    public void addRecentQuestion(RecentQuestionBean recentQuestionBean) {
        Long numQueries = getNumberOfRecentQuestions();

        if(numQueries > MAX_RECENT_QUESTIONS) {
            mDbHelper.deleteTopRecentQuestion();
        }
        mDbHelper.addRecentQuestion(recentQuestionBean);
    }

    public void deleteRecentQuestion(RecentQuestionBean recentQuestion) {
        mDbHelper.deleteRecentQuestion(recentQuestion);
    }

    public void saveStory(QuestionBean mCurrentQuestionBean) {
        mDbHelper.saveStory(mCurrentQuestionBean);
    }

    //TODO:
    // validation using unsafe words

}
