package com.appchee.learnews.actions;

import android.content.Context;

import com.appchee.learnews.beans.RatingBean;
import com.appchee.learnews.database.DbInteractions;

public class RatingsManager {

    private Context mContext;
    DbInteractions mDbHelper;

    public RatingsManager(Context context) {
        mContext = context;
        mDbHelper = new DbInteractions(mContext);
    }

    public void updateRating(int questionId, int userId, float rating) {
        mDbHelper.updateRating(questionId, userId, rating);
    }

    public RatingBean[] getRatingBeans() {
        return mDbHelper.getRatingBeans();
    }
}
