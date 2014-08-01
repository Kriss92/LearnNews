package com.appchee.learnews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;

import java.util.ArrayList;
import java.util.List;

//TODO:
public class DbInteractions {

    private final LearNewsDbHelper mDbHelper;

//    private static final String ADD_ANSWER_QUERY = "insert into Answers(questionId, answer, correct) values (?, ?, ?) ";


    public DbInteractions(Context context) {
        mDbHelper = new LearNewsDbHelper(context);
    }


//    DbInteractions interactionsHelper = new DbInteractions(this.getApplicationContext());
//    interactionsHelper.questionAnswered();
//    interactionsHelper.addQuestion();
//    interactionsHelper.getQuestion(0);


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

        Log.d("DB saved", "Saved question");
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
        values.put("correct", answer.getCorrect());
        mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.ANSWERS_TABLE, null, values);
        Log.d("DB saved", "Saved answer");
    }

    public void questionAnswered() {

    }

    public void updateInteractions() {

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
 //       public static final int ANSWER_ID_INDEX = 2;
        public static final int URL_INDEX = 2;
        public static final int CATEGORY_INDEX = 3;
    }
    public QuestionBean getQuestion(Integer questionId) {

        Cursor questionCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.QUESTIONS_TABLE, GetQuestionsQuery.PROJECTION,
                        "id = " + questionId.toString(),  new String[]{}, null, null, null, null);

        questionCursor.moveToNext();
        Log.d("test", questionCursor.getString(GetQuestionsQuery.QUESTION_INDEX));

        return buildQuestion(questionCursor);
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

    public List<String> getSavedURLS(String userId) {
        List<String> result = new ArrayList<String>();
        //TODO:

        return result;
    }

    public LearNewsDbHelper getDBHelper() {
        return mDbHelper;
    }
}
