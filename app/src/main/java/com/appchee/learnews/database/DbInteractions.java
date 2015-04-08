package com.appchee.learnews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.appchee.learnews.LoginActivity;
import com.appchee.learnews.actions.NewsManager;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.StoryBean;

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
        mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.QUESTIONS_TABLE, null, values);
        Long numQueries = DatabaseUtils.queryNumEntries(mDbHelper.getReadableDatabase(),
                LearNewsDbHelper.QUESTIONS_TABLE);
        Log.d("Queries in DbInteractions", "" + numQueries);

    }

    private static class GetQuestionsQuery {
        public static final String[] PROJECTION = {"id", "question", "answer1", "answer2",
                "answer3",  "answer4",  "correctIndex",  "newsURL",
                "category", "dateAdded"};
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

    }
    public QuestionBean getQuestion(Integer questionId) {

        Cursor questionCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.QUESTIONS_TABLE, GetQuestionsQuery.PROJECTION,
                        "id = " + questionId.toString(),  new String[]{}, null, null, null, null);

        questionCursor.moveToNext();
        return buildQuestion(questionCursor);
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

    private static class InteractionsQuery {
        public static final String[] PROJECTION = {"correct", "incorrect"};
        public static final int CORRECT_INDEX = 0;
        public static final int INCORRECT_INDEX = 1;

    }
    public void updateInteractions(QuestionBean question, boolean correct) {
        Cursor cursor = getInteractionsCursor(question);
        if (!cursor.moveToNext()) {
            return;
        }
        ContentValues values = new ContentValues();
        if (correct) {
            values.put("correct", cursor.getInt(InteractionsQuery.CORRECT_INDEX) + 1);
        } else {
            values.put("incorrect", cursor.getInt(InteractionsQuery.INCORRECT_INDEX) + 1 );
        }
        updateInteraction(question, values);

    }

    private void updateInteraction(QuestionBean question, ContentValues values) {
        mDbHelper.getWritableDatabase().update(LearNewsDbHelper.INTERACTIONS_TABLE, values,
//                "questionId = ? and userId = ?",  new String[] {question.getId().toString(), LoginActivity.mCurrentUserId});
                "questionId = ?",  new String[] {question.getId().toString()});
        Log.d("Interaction", "Interaction Updated");
    }

    public void saveStory(QuestionBean question) {
        ContentValues values = new ContentValues();
        try {
            values.put("favorite", NewsManager.extractTitle(question.getNewsURL()));
            updateInteraction(question, values);
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
                Log.d("Strory URL", title);
                StoryBean bean = new StoryBean();
                bean.setTitle(title);
                bean.setNewsIconId(2);
                stories.add(bean);
            }
        }


        return stories;
    }


    public double Interactions(QuestionBean question) {
        double correct = 0.0;
        double incorrect = 0.0;
        Cursor cursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.INTERACTIONS_TABLE, InteractionsQuery.PROJECTION, "questionId = ?",
                new String[] {question.getId().toString()}, null, null, null, null);

        while(cursor.moveToNext()) {
            correct += cursor.getInt(InteractionsQuery.CORRECT_INDEX);
            incorrect += cursor.getInt(InteractionsQuery.INCORRECT_INDEX);
        }
        return correct * 100 / (correct + incorrect);
    }

    private Cursor getInteractionsCursor(QuestionBean question) {
        return mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.INTERACTIONS_TABLE, InteractionsQuery.PROJECTION, "questionId = ?",
                new String[] {question.getId().toString()}, null, null, null, null);
    }

    public QuestionBean getQuestionByNumber(Integer questionNumber) {

        Cursor questionCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.QUESTIONS_TABLE, GetQuestionsQuery.PROJECTION,
                null,  null, null, null, null, null);

        questionCursor.move(questionNumber + 1);
        return buildQuestion(questionCursor);

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

        QuestionBean result = new QuestionBean(id, question, answer1, answer2,
                answer3,  answer4,  correctIndex,  url,
                category, dateAdded);
        return result;
    }

    public void deleteQuestion(QuestionBean questionBean) {
        mDbHelper.getWritableDatabase().delete(LearNewsDbHelper.QUESTIONS_TABLE,
                "id = ?", new String[] {questionBean.getId().toString()});
        Log.d("DbInteractions", "Question was deleted");
    }

    public LearNewsDbHelper getDBHelper() {
        return mDbHelper;
    }
}
