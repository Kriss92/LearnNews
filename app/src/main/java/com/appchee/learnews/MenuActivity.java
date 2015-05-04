package com.appchee.learnews;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.appchee.learnews.R;
import com.appchee.learnews.actions.QuestionsManager;
import com.appchee.learnews.actions.RatingsManager;
import com.appchee.learnews.backend.WebClient;
import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.RatingBean;
import com.appchee.learnews.database.DbInteractions;
import com.appchee.learnews.database.LearNewsDbHelper;
import com.appchee.learnews.validation.ValidationException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.appchee.learnews.AddQuestionsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MenuActivity extends Activity {

    RatingsManager mRatingsManager;
    QuestionsManager mQuestionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mRatingsManager = new RatingsManager(getApplicationContext());
        mQuestionsManager = new QuestionsManager(getApplicationContext());

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.animate().setDuration(200).scaleX(0.75f).scaleY(0.75f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.animate().scaleX(1.0f).scaleY(1.0f);
                }
                return false;
            }
        };

        Button newGameButton = (Button) findViewById(R.id.new_game_menu_button);
        newGameButton.setOnTouchListener(touchListener);
        Button addQuestionButton = (Button) findViewById(R.id.add_questions_menu_button);
        addQuestionButton.setOnTouchListener(touchListener);
        Button savedStoriesButton = (Button) findViewById(R.id.your_stories_menu_button);
        savedStoriesButton.setOnTouchListener(touchListener);

        initializeDB();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sign_out:
                //Sign user out
                break;
            case R.id.action_about:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newGameMenuButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void syncDbWithServer(View view) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebClient webc = new WebClient();
                try {
                    Log.d("User id is ", "" + CurrentUserDetails.userId);
                    int numQuestions = mQuestionsManager.getNumberOfQuestions().intValue();
                    List<QuestionBean> questionBeans = webc.syncQuestions(CurrentUserDetails.userId,
                            numQuestions);
                    addQuestionsToDb(questionBeans);
                    RatingBean[] ratingBeans = getRatingBeans();
                    if(ratingBeans != null) {
                        webc.syncRatings(ratingBeans);
                    }
                    try {
                        CurrentUserDetails.syncCurrentUsersScore();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        thread.join();
    }

    private RatingBean[] getRatingBeans() {
        return mRatingsManager.getRatingBeans();
    }

    private void addQuestionsToDb(List<QuestionBean> questionBeans) {
        if(questionBeans != null) {
            DbInteractions dbHelper = new DbInteractions(getApplicationContext());
            for (QuestionBean questionBean : questionBeans) {
                dbHelper.addQuestion(questionBean);
            }
        }

    }

    public void addQuestionsMenuButtonClicked(View view) {
        Intent intent = new Intent(this, AddQuestionsActivity.class);
        startActivity(intent);
    }

    public void SavedStoriesMenuButtonClicked(View view) {
        Intent intent = new Intent(this, SavedStoriesActivity.class);
        startActivity(intent);
    }


    public void initializeDB(){
        DbInteractions dbHelper = new DbInteractions(getApplicationContext());
        {
            QuestionBean questionBean1=new QuestionBean();
            questionBean1.setId(1);
            questionBean1.setQuestion("Who is negotiating the ceasefire between the Israeli and the Palestinian delegation?");
            questionBean1.setCategory("Economy");
            questionBean1.setAnswer1("United States");
            questionBean1.setAnswer2("United Kingdom");
            questionBean1.setAnswer3("France");
            questionBean1.setAnswer4("Egypt");
            questionBean1.setCorrectIndex(3);
            questionBean1.setNewsURL("http://www.bbc.com/news/world-middle-east-28603599");
            questionBean1.setCategory("Economy");
            questionBean1.setDateAdded("Today");
            questionBean1.setRating(5);
            dbHelper.addQuestion(questionBean1);
        }

    }

}
