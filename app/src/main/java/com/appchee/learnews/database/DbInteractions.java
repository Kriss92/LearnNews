package com.appchee.learnews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//TODO:
public class DbInteractions {

    private LearNewsDbHelper mDbHelper;


    private static final String GET_QUESTION_QUERY = "select * from Questions where id = ?";
//
//    private static final String GET_ANSWER_QUERY = "select * from Answers where questionId = ?";
//
//    private static final String ADD_QUESTION_QUERY = "insert into  Questions (question, answerId, URL, category) values (?, ?, ?, ?) ";
//
//    private static final String ADD_ANSWER_QUERY = "insert into Answers(questionId, answer, correct) values (?, ?, ?) ";


    public DbInteractions(Context context) {
        mDbHelper = new LearNewsDbHelper(context);
    }


//    DbInteractions interactionsHelper = new DbInteractions(this.getApplicationContext());
//    interactionsHelper.questionAnswered();
//    interactionsHelper.addQuestion();
//    interactionsHelper.getQuestion(0);


    public void addQuestion() {
        //TODO
//       mDbHelper.getWritableDatabase().execSQL(ADD_QUESTION_QUERY, new String[]{"QUESTION", "1", "HTTP://TEST.TT", "SOME"});
//        Integer id = cursor.getInt(0);
//        addAnswer(id, "Answer", 1);

        ContentValues values = new ContentValues();
        values.put("question", "What is your name?");
        values.put("answerId", "1");
        Long result = mDbHelper.getWritableDatabase().insert(LearNewsDbHelper.QUESTIONS_TABLE, null, values);
        Log.d("PFFFFFF", "RESULT " + result.toString());
    }


    private void addAnswer(Integer questionId, String answer, Integer correct) {
        //TODO:
     //   Cursor cursor = mDbHelper.getWritableDatabase().rawQuery(ADD_ANSWER_QUERY, new String[] {questionId.toString(), answer, correct.toString()});
    }

    public void questionAnswered() {

    }

    public void updateInteractions() {

    }

    private static class QuestionsQuery {
        public static final String[] PROJECTION = {"id", "question", "answerId", "URL", "category" };
        public static final int ID_INDEX = 0;
        public static final int QUESTION_INDEX = 1;
        public static final int ANSWER_ID_INDEX = 2;
        public static final int URL_INDEX = 3;
        public static final int CATEGORY_INDEX = 4;
    }
    public void getQuestion(Integer questionId) {

        Cursor questionCursor = mDbHelper.getReadableDatabase().query(
                LearNewsDbHelper.QUESTIONS_TABLE, new String[] {"id", "question", "answerId", "URL", "category" }, "id = " + questionId.toString(),  new String[]{}, null, null, null, null);

        while (questionCursor.moveToNext()) {
            Log.d("test", questionCursor.getString(QuestionsQuery.QUESTION_INDEX));
        }

        SQLiteDatabase sth = mDbHelper.getReadableDatabase();

    }
}
