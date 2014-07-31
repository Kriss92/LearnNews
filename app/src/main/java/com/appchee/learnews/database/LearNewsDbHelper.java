package com.appchee.learnews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LearNewsDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LearNews";

    private static final String QUESTIONS_TABLE_CREATE =
            "CREATE TABLE Questions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "question TEXT" +
                    "answerId INTEGER" +
                    "URL TEXT" +
                    "category TEXT" +
            ");";

    private static final String ANSWERS_TABLE_CREATE =
            "CREATE TABLE Answers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "questionId INTEGER references Questions(id)" +
                    "answer TEXT" +
                    "correct INTEGER" +
                    ");";

    private static final String INTERACTIONS_TABLE_CREATE =
            "CREATE TABLE Interactions (" +
                    "userId INTEGER, " +
                    "questionId INTEGER references Questions(id)" +
                    "correctNums INTEGER" +
                    "wrongs INTEGER" +
                    "reported INTEGER" +
                    "favorite INTEGER" +
                    "time INTEGER" + //number of seconds since 1970-01-01 00:00:00 UTC
                    ");";

    private static final String INTERACTIONS_CREATE_INDEX =
            "CREATE INDEX ON Interactions (userId, questionId)";

//    private static final String QUESTIONS_CREATE_FOREIGN_KEY =
//            "ALTER TABLE Questions ADD CONSTRAINT " +
//                    "FOREIGN KEY (answerId)" +
//                    "REFERENCES Answers(id);";


    public LearNewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        db.execSQL(QUESTIONS_TABLE_CREATE);
        db.execSQL(ANSWERS_TABLE_CREATE);
        db.execSQL(INTERACTIONS_TABLE_CREATE);
        db.execSQL(INTERACTIONS_CREATE_INDEX);
//        db.execSQL(QUESTIONS_CREATE_FOREIGN_KEY);

        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
