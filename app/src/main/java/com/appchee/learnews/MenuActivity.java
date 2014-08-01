package com.appchee.learnews;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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
import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.database.DbInteractions;
import com.appchee.learnews.validation.ValidationException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.appchee.learnews.AddQuestionsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

        //  String[] categories= getResources().getStringArray(R.array.categories);
        {
            List<AnswerBean> answerBeans1 = new ArrayList<AnswerBean>();
            AnswerBean ans1 = new AnswerBean("United States", false);
            answerBeans1.add(ans1);
            AnswerBean ans2 = new AnswerBean("United Kingdom", false);
            answerBeans1.add(ans2);
            AnswerBean ans3 = new AnswerBean("France", false);
            answerBeans1.add(ans3);
            AnswerBean ans4 = new AnswerBean("Egypt", true);
            answerBeans1.add(ans4);


            QuestionBean questionBean1=new QuestionBean();
            questionBean1.setQuestion("Who is negotiating the ceasefire between the Israeli and the Palestinitan delgeation?");
            questionBean1.setCategory("Economy");
            questionBean1.setNewsURL("http://www.bbc.com/news/world-middle-east-28603599");
            questionBean1.setAnswers(answerBeans1);
            dbHelper.addQuestion(questionBean1);
        }

        List<AnswerBean> answerBeans2 = new ArrayList<AnswerBean>();
        {   AnswerBean ans1 = new AnswerBean("Email users", false);
            answerBeans2.add(ans1);
            AnswerBean ans2 = new AnswerBean("Social network users", false);
            answerBeans2.add(ans2);
            AnswerBean ans3 = new AnswerBean("Big concerns", false);
            answerBeans2.add(ans3);
            AnswerBean ans4 = new AnswerBean("Government", true);
            answerBeans2.add(ans4);

            QuestionBean questionBean2=new QuestionBean();
            questionBean2.setQuestion("The new Internet law in Russia sets restrictions on:");
            questionBean2.setCategory("Politics");
            questionBean2.setNewsURL("http://www.bbc.com/news/technology-28583669");
            questionBean2.setAnswers(answerBeans2);
            dbHelper.addQuestion(questionBean2);
        }

        List<AnswerBean> answerBeans3 = new ArrayList<AnswerBean>();
        {
            AnswerBean ans1 = new AnswerBean("Cats", false);
            answerBeans3.add(ans1);
            AnswerBean ans2 = new AnswerBean("Scarecrows", false);
            answerBeans3.add(ans2);
            AnswerBean ans3 = new AnswerBean("Mimes", false);
            answerBeans3.add(ans3);
            AnswerBean ans4 = new AnswerBean("Beginning opera singers", true);
            answerBeans3.add(ans4);

            QuestionBean questionBean3=new QuestionBean();
            questionBean3.setQuestion("What did India hire to scare monkeys?");
            questionBean3.setCategory("Travel");
            questionBean3.setNewsURL("http://www.bbc.com/news/28599470");
            questionBean3.setAnswers(answerBeans3);
            dbHelper.addQuestion(questionBean3);
        }

        List<AnswerBean> answerBeans4 = new ArrayList<AnswerBean>();
        {
            AnswerBean ans1 = new AnswerBean("Shiffra Haim Cohen", false);
            answerBeans4.add(ans1);
            AnswerBean ans2 = new AnswerBean("Yochewed Mazal Tzur", false);
            answerBeans4.add(ans2);
            AnswerBean ans3 = new AnswerBean("Meriam Yahia Ibrahim", false);
            answerBeans4.add(ans3);
            AnswerBean ans4 = new AnswerBean("Uma Sara Mesada", true);
            answerBeans4.add(ans4);


            QuestionBean questionBean4=new QuestionBean();
            questionBean4.setQuestion("What was the name of the woman sentence for apostasy in Sudan?");
            questionBean4.setCategory("Politics");
            questionBean4.setNewsURL("http://www.bbc.com/news/world-us-canada-28596412");
            questionBean4.setAnswers(answerBeans4);
            dbHelper.addQuestion(questionBean4);
        }


        List<AnswerBean> answerBeans5 = new ArrayList<AnswerBean>();
        {
            AnswerBean ans1 = new AnswerBean("Jamaica", false);
            AnswerBean ans2 = new AnswerBean("Kenya", false);
            answerBeans5.add(ans2);
            AnswerBean ans3 = new AnswerBean("Botswana", false);
            answerBeans5.add(ans3);
            AnswerBean ans4 = new AnswerBean(" the UK", true);
            answerBeans5.add(ans4);


            QuestionBean questionBean5=new QuestionBean();
            questionBean5.setQuestion("The new winner of the 800 m race in the Commonwealth Games comes from:");
            questionBean5.setCategory("Sport");
            questionBean5.setNewsURL("http://www.bbc.com/sport/0/commonwealth-games/28595270");
            questionBean5.setAnswers(answerBeans5);
            dbHelper.addQuestion(questionBean5);
        }

    }
}
