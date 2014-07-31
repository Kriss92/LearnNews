package com.appchee.learnews.database;

import android.database.Cursor;

//TODO:
public class DbInteractions {

    private static LearNewsDbHelper dbHelper = new LearNewsDbHelper(null);


    private static final String GET_QUESTION_QUERY = "select * from Questions where id = ?";

    private static final String GET_ANSWER_QUERY = "select * from Answers where questionId = ?";

    private static final String ADD_QUESTION_QUERY = "insert into Questions(question, answer, URL, category) values (?, ?, ?, ?) ";

    private static final String ADD_ANSWER_QUERY = "insert into Answers(questionId, answer, correct) values (?, ?, ?) ";


    public static void addQuestion() {
        //TODO
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(ADD_QUESTION_QUERY, new String[]{});
        Integer id = cursor.getInt(0);
        addAnswer(id);

    }


    private static void addAnswer(Integer questionId) {
        //TODO:
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(ADD_ANSWER_QUERY, new String[] {questionId.toString()});
    }

    public static void questionAnswered() {

    }

    private static void updateInteractions() {

    }

    private static void getQuestion(Integer questionId) {

        Cursor questionCursor = dbHelper.getReadableDatabase().rawQuery(
                GET_QUESTION_QUERY, new String[]{questionId.toString()});

        Cursor answerCursor = dbHelper.getReadableDatabase().rawQuery(
                GET_ANSWER_QUERY, new String[]{questionId.toString()});


    }
}
