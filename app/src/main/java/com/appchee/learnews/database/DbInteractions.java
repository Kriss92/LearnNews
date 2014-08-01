package com.appchee.learnews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.appchee.learnews.LoginActivity;
import com.appchee.learnews.actions.NewsManager;
import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.StoryBean;

import java.util.ArrayList;
import java.util.List;

public class DbInteractions {

    private final LearNewsDbHelper mDbHelper;

    public DbInteractions(Context context) {
        mDbHelper = new LearNewsDbHelper(context);
    }

    private static class AddQuestionsQuery {
        private static final String SQL = "insert into  Questions (question, answerId, URL, category) " +
                " values (?, ?, ?, ?) ";
        private  static final String Last_ID_SQL = "SELECT id from Questions order by id DESC limit 1";
        public static final int QUESTION_INDEX = 0;
        public static final int ANSWER_ID_INDEX = 1;
        public static final int URL_INDEX = 2;
        public static final int CATEGORY_INDEX = 2;
    }
    public void addQuestion(QuestionBean question) {

        ContentValues values = new ContentValues();
        values.put("question", question.getQuestion());
        values.put("answerId", question.getCorrectAnswer());
        values.put("URL", question.getNewsURL());
        values.put("category", question.getCategory());
        mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.QUESTIONS_TABLE, null, values);

        addAnswers(question);
    }

    private void addAnswers(QuestionBean question) {
        Cursor questionCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.QUESTIONS_TABLE, new String[] {"id"},
                null,  null, null, null, "id DESC", "1");
        questionCursor.moveToFirst();
        Integer questionId = questionCursor.getInt(0);

        for (AnswerBean answer : question.getAnswers()) {
            answer.setQuestionId(questionId);
            addAnswer(answer);
        }
    }

    private void addAnswer(AnswerBean answer) {
        ContentValues values = new ContentValues();
        values.put("questionId", answer.getQuestionId());
        values.put("answer", answer.getAnswer());
        int correct = answer.getCorrect() ? 1 : 0;
        values.put("correct", correct);
        mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.ANSWERS_TABLE, null, values);
    }

    private static class AnswersQuery {
        public static final String[] PROJECTION = {"id", "answer","correct"};
        public static final int ID_INDEX = 0;
        public static final int ANSWER_INDEX = 1;
        public static final int CORRECT_INDEX = 2;
    }
    public List<AnswerBean> getAnswers(Integer queationId) {
        Cursor answersCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.ANSWERS_TABLE, AnswersQuery.PROJECTION, "questionId = ?", new String[] {queationId.toString()}, null, null, null, null);
        List<AnswerBean> result = new ArrayList<AnswerBean>();

        while (answersCursor.moveToNext()) {
            Log.d("test", answersCursor.getString(AnswersQuery.ID_INDEX));
            AnswerBean answer = new AnswerBean();
            answer.setId(answersCursor.getInt(AnswersQuery.ID_INDEX));
            answer.setAnswer(answersCursor.getString(AnswersQuery.ANSWER_INDEX));
            answer.setCorrect(answersCursor.getInt(AnswersQuery.CORRECT_INDEX));
            result.add(answer);
        }
        return result;
    }

    private static class GetQuestionsQuery {
        public static final String[] PROJECTION = {"id", "question","URL", "category" };
        public static final int ID_INDEX = 0;
        public static final int QUESTION_INDEX = 1;
        public static final int URL_INDEX = 2;
        public static final int CATEGORY_INDEX = 3;
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

//                LearNewsDbHelper.INTERACTIONS_TABLE, InteractionsQuery.PROJECTION, "questionId = ?, userId = ?",
//                new String[] {question.getId().toString(), LoginActivity.mCurrentUserId}, null, null, null, null);
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
        List<AnswerBean> answers = getAnswers(id);
        String question = questionCursor.getString(GetQuestionsQuery.QUESTION_INDEX);
        String url = questionCursor.getString(GetQuestionsQuery.URL_INDEX);
        String category = questionCursor.getString(GetQuestionsQuery.CATEGORY_INDEX);

        QuestionBean result = new QuestionBean(id, question, answers, url,  category);
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
