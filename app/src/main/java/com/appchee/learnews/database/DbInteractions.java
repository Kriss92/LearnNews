package com.appchee.learnews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appchee.learnews.LoginActivity;
import com.appchee.learnews.actions.NewsManager;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.RatingBean;
import com.appchee.learnews.beans.StoryBean;
import com.google.android.gms.games.quest.Quest;

import java.util.ArrayList;
import java.util.List;

public class DbInteractions {

    private final LearNewsDbHelper mDbHelper;

    public DbInteractions(Context context) {
        mDbHelper = new LearNewsDbHelper(context);
    }

    public void addQuestion(QuestionBean question) {
        ContentValues values = new ContentValues();
        values.put("id", question.getId());
        values.put("question", question.getQuestion());
        values.put("answer1", question.getAnswer1());
        values.put("answer2", question.getAnswer2());
        values.put("answer3", question.getAnswer3());
        values.put("answer4", question.getAnswer4());
        values.put("correctIndex", question.getCorrectIndex());
        values.put("newsUrl", question.getNewsURL());
        values.put("category", question.getCategory());
        values.put("dateAdded", question.getDateAdded());
        values.put("rating", question.getRating());
        mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.QUESTIONS_TABLE, null, values);
    }

    public void updateRating(int questionId, int userId, float rating) {
        String RATING_TRANSACTION = "INSERT OR REPLACE INTO Ratings (questionId, userId, rating)" +
                "VALUES(" + questionId + ", " + userId + ", " + rating + ")" +
                ";";

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.beginTransaction();

        try {
            db.execSQL(RATING_TRANSACTION);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }

    }

    private static class GetQuestionsQuery {
        public static final String[] PROJECTION = {"id", "question", "answer1", "answer2",
                "answer3",  "answer4",  "correctIndex",  "newsURL",
                "category", "dateAdded", "rating"};
        public static final int ID_INDEX = 0;
        public static final int QUESTION_INDEX = 1;
        public static final int ANSWER1_INDEX = 2;
        public static final int ANSWER2_INDEX = 3;
        public static final int ANSWER3_INDEX = 4;
        public static final int ANSWER4_INDEX = 5;
        public static final int CORRECT_INDEX_INDEX = 6;
        public static final int URL_INDEX = 7;
        public static final int CATEGORY_INDEX = 8;
        public static final int DATE_ADDED_INDEX = 9;
        public static final int RATING_INDEX = 10;

    }

    public QuestionBean getQuestion(Integer questionId) {

        QuestionBean questionBean = new QuestionBean();

        Cursor questionCursor =
                mDbHelper.getReadableDatabase().query(
                        LearNewsDbHelper.QUESTIONS_TABLE, GetQuestionsQuery.PROJECTION,
                        "id = " + questionId.toString(), new String[]{}, null, null, null, null);

        try {
            questionCursor.moveToNext();
            questionBean = buildQuestion(questionCursor);

        }
        finally {
            questionCursor.close();
        }

        return questionBean;
    }

    public void createInteraction(QuestionBean question) {
//        Cursor cursor = getInteractionsCursor(question);
//        if (cursor.moveToNext()) {
//            return;
//        }

        ContentValues values = new ContentValues();
        values.put("questionId", question.getId());
        values.put("userId", LoginActivity.mCurrentUserId);
        mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.INTERACTIONS_TABLE, null, values);
    }

    public void saveStory(QuestionBean question) {
        ContentValues values = new ContentValues();
        try {
            values.put("favorite", NewsManager.extractTitle(question.getNewsURL()));
        } catch (Exception e) {
            return;
        }
    }

    public List<StoryBean> readSavedStories(){
        List<StoryBean> stories = new ArrayList<StoryBean>();

        Cursor cursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.INTERACTIONS_TABLE, new String[] {"favorite"},
                null,  null, null, null, null, null);

        while(cursor.moveToNext()) {
            String title = cursor.getString(0);
            if (title != null && title.length() > 0) {
                Log.d("Story URL", title);
                StoryBean bean = new StoryBean();
                bean.setTitle(title);
                bean.setNewsIconId(2);
                stories.add(bean);
            }
        }


        return stories;
    }

    public QuestionBean getQuestionByNumber(Integer questionNumber) {

        QuestionBean questionBean = new QuestionBean();

        Cursor questionCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.QUESTIONS_TABLE, GetQuestionsQuery.PROJECTION,
                null,  null, null, null, null, null);

        try {
            questionCursor.move(questionNumber + 1);
            questionBean = buildQuestion(questionCursor);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            questionCursor.close();
        }

        return questionBean;
    }



    public QuestionBean buildQuestion(Cursor questionCursor ) {
        Integer id = questionCursor.getInt(GetQuestionsQuery.ID_INDEX);
        String question = questionCursor.getString(GetQuestionsQuery.QUESTION_INDEX);
        String answer1 = questionCursor.getString(GetQuestionsQuery.ANSWER1_INDEX);
        String answer2 = questionCursor.getString(GetQuestionsQuery.ANSWER2_INDEX);
        String answer3 = questionCursor.getString(GetQuestionsQuery.ANSWER3_INDEX);
        String answer4 = questionCursor.getString(GetQuestionsQuery.ANSWER4_INDEX);
        int correctIndex = questionCursor.getInt(GetQuestionsQuery.CORRECT_INDEX_INDEX);
        String url = questionCursor.getString(GetQuestionsQuery.URL_INDEX);
        String category = questionCursor.getString(GetQuestionsQuery.CATEGORY_INDEX);
        String dateAdded = questionCursor.getString(GetQuestionsQuery.DATE_ADDED_INDEX);
        float rating = questionCursor.getFloat(GetQuestionsQuery.RATING_INDEX);

        QuestionBean result = new QuestionBean(id, question, answer1, answer2,
                answer3,  answer4,  correctIndex,  url,
                category, dateAdded, rating);
        return result;
    }

    public void deleteQuestion(QuestionBean questionBean) {
        mDbHelper.getWritableDatabase().delete(LearNewsDbHelper.QUESTIONS_TABLE,
                "id = ?", new String[] {questionBean.getId().toString()});
        Log.d("DbInteractions", "Question was deleted");
    }

    private static class GetRatingsQuery {
        public static final String[] PROJECTION = {"questionId", "userId", "rating"};
        public static final int QUESTION_ID_INDEX = 0;
        public static final int USER_ID_INDEX = 1;
        public static final int RATING_INDEX = 2;
    }

    public RatingBean[] getRatingBeans() {
        RatingBean[] ratingBeans = null;
        int numQueries = (int) DatabaseUtils.queryNumEntries(mDbHelper.getReadableDatabase(),
                LearNewsDbHelper.RATINGS_TABLE);

        if (numQueries < 1) {
            return ratingBeans;
        }
        else {
            ratingBeans = new RatingBean[numQueries];
        }
            int i;
            for(i = 0; i < numQueries; i++) {
                ratingBeans[i] = getRatingByNumber(i);
        }
        return ratingBeans;
    }



    public RatingBean getRatingByNumber(Integer ratingNumber) {

        RatingBean ratingBean = new RatingBean();

        Cursor ratingCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.RATINGS_TABLE, GetRatingsQuery.PROJECTION,
                null,  null, null, null, null, null);

        try {
            ratingCursor.move(ratingNumber + 1);
            ratingBean = buildRating(ratingCursor);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ratingCursor.close();
        }

        return ratingBean;
    }

    public RatingBean buildRating(Cursor ratingCursor) {
        Integer questionId = ratingCursor.getInt(GetRatingsQuery.QUESTION_ID_INDEX);
        Integer userId = ratingCursor.getInt(GetRatingsQuery.USER_ID_INDEX);
        float rating = ratingCursor.getFloat(GetRatingsQuery.RATING_INDEX);

        RatingBean result = new RatingBean(questionId, userId, rating);
        return result;
    }

    public LearNewsDbHelper getDBHelper() {
        return mDbHelper;
    }
}
