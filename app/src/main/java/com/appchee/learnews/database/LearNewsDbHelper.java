package com.appchee.learnews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appchee.learnews.R;
import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;

import java.util.ArrayList;
import java.util.List;

public class LearNewsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LearNews";

    public static String QUESTIONS_TABLE = "Questions";
    public static String RECENT_QUESTIONS_TABLE = "Recent_Questions";
    public static String SAVED_QUESTIONS_TABLE = "Saved_Questions";
    public static String RATINGS_TABLE = "Ratings";
    public static String INTERACTIONS_TABLE = "Interactions";
    private Context mContext;

    private static final String QUESTIONS_TABLE_CREATE =
            "CREATE TABLE " +  QUESTIONS_TABLE + " (" +
                    "id INTEGER, " +
                    "question TEXT, " +
                    "answer1 TEXT, " +
                    "answer2 TEXT, " +
                    "answer3 TEXT, " +
                    "answer4 TEXT, " +
                    "correctIndex INTEGER, " +
                    "newsUrl TEXT, " +
                    "category TEXT, " +
                    "dateAdded TEXT, " +
                    "rating REAL" +
                    ");";

    private static final String RECENT_QUESTIONS_TABLE_CREATE =
            "CREATE TABLE " +  RECENT_QUESTIONS_TABLE + " (" +
                    "id INTEGER, " +
                    "question TEXT, " +
                    "answer1 TEXT, " +
                    "answer2 TEXT, " +
                    "answer3 TEXT, " +
                    "answer4 TEXT, " +
                    "correctIndex INTEGER, " +
                    "selectedIndex INTEGER, " +
                    "newsUrl TEXT, " +
                    "category TEXT, " +
                    "dateAdded TEXT, " +
                    "dateAnswered INTEGER, " +
                    "rating REAL" +
                    ");";

    private static final String RATINGS_TABLE_CREATE =
            "CREATE TABLE " +  RATINGS_TABLE + " (" +
                    "questionId INTEGER, " +
                    "userId INTEGER, " +
                    "rating REAL, " +
                    "PRIMARY KEY (questionId, userId)" +
                    ");";

    public LearNewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();

        try {
            db.execSQL(QUESTIONS_TABLE_CREATE);
            db.execSQL(RECENT_QUESTIONS_TABLE_CREATE);
            db.execSQL(RATINGS_TABLE_CREATE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
