package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GameActivity extends Activity implements QuizQuestionFragment.QuestionCallback, ContinueCallback {

    View mQuestionFragment;
    View mCorrectAnsFragment;
    View mWrongAnsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initatializing:
        mQuestionFragment= (View) findViewById(R.id.quiz_question_fragment);
        mWrongAnsFragment= (View) findViewById(R.id.correct_ans_fragment); //TODO
        mCorrectAnsFragment= (View) findViewById(R.id.wrong_ans_fragment); //TODO

        mQuestionFragment.setVisibility(View.VISIBLE);
        mCorrectAnsFragment.setVisibility(View.INVISIBLE);
        mWrongAnsFragment.setVisibility(View.INVISIBLE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAnswerSubmittedListener(boolean isCorrect) {
        if (true == isCorrect) {
            setCorrectAnswerView();
        } else {
            setWrongAnswerView();
        }
    }

    @Override
    public void onContinueSubmitted() {
        setNextQuestionView();
    }


    public void setNextQuestionView() {
        mQuestionFragment.setVisibility(View.VISIBLE);
        mCorrectAnsFragment.setVisibility(View.INVISIBLE);
        mWrongAnsFragment.setVisibility(View.INVISIBLE);
    }

    public void setWrongAnswerView() {
        mQuestionFragment.setVisibility(View.INVISIBLE);
        mCorrectAnsFragment.setVisibility(View.INVISIBLE);
        mWrongAnsFragment.setVisibility(View.VISIBLE);
    }
    public void setCorrectAnswerView() {
        mQuestionFragment.setVisibility(View.INVISIBLE);
        mCorrectAnsFragment.setVisibility(View.VISIBLE);
        mWrongAnsFragment.setVisibility(View.INVISIBLE);
    }




}
