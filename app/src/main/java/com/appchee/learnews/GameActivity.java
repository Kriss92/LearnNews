package com.appchee.learnews;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends Activity implements QuizQuestionFragment.QuestionCallback, StoryBarFragment.StoryBarCallback, CorrectAnswerFragment.CorrectCallback, WrongAnswerFragment.WrongCallback {

    CorrectAnswerFragment mCorrectAnsFragment;
    WrongAnswerFragment mWrongAnsFragment;
    QuizQuestionFragment mQuizQuestionFragment;

    public int mCorrectAnswer=1;
    public String[] mAnswers= {"aa","bb", "cc", "dd"};
    public int mCorrectPrecentage=100;
    TextView mCurrQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initializing:
        mQuizQuestionFragment= (QuizQuestionFragment) getFragmentManager().findFragmentById(R.id.quiz_question_fragment);
        mWrongAnsFragment= (WrongAnswerFragment) getFragmentManager().findFragmentById(R.id.wrong_ans_fragment);
        mCorrectAnsFragment=  (CorrectAnswerFragment) getFragmentManager().findFragmentById(R.id.correct_ans_fragment);
        mCurrQuestion= (TextView) findViewById(R.id.q_text);

        setNextQuestionView();
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




    // Interfaces Implementations
    @Override
    public void onAnswerSubmittedListener(int answerSelected) {
        if (mCorrectAnswer == answerSelected) {
            setCorrectAnswerView();
            mCorrectAnsFragment.populate( mAnswers[answerSelected] , mCorrectPrecentage);
            //generate answer view in fragment
        } else {
            setWrongAnswerView();
            mWrongAnsFragment.populate( mAnswers[answerSelected], mAnswers[mCorrectAnswer]);
            //generate answer view in fragment
        }
        //generating story fragment
    }

    @Override
    public void onContinueButtonListener() {
        setNextQuestionView();
        setNextQuestionViewContent();
        //generate question
    }
    @Override
    public void onSaveStoryButtonListener() {
        Log.d("Rony", "Saving Story....");
;    }

    @Override
    public void onSendStoryButtonListener() {
        Log.d("Rony", "Sending Story....");

    }


    public void setNextQuestionViewContent() {
        mCurrQuestion.setText("New Question!!!!!!????");
        mQuizQuestionFragment.populate(new String[] {"A","B","C","D"});
    }

    //methods:
    public void setNextQuestionView() {
        mQuizQuestionFragment.getView().setVisibility(View.VISIBLE);
        mCorrectAnsFragment.getView().setVisibility(View.GONE);
        mWrongAnsFragment.getView().setVisibility(View.GONE);
    }

    public void setNextQuestionVars() {
        //mCorrectPrecentage...
    }

    public void setCorrectAnswerView() {
        mQuizQuestionFragment.getView().setVisibility(View.GONE);
        mCorrectAnsFragment.getView().setVisibility(View.VISIBLE);
        mWrongAnsFragment.getView().setVisibility(View.GONE);


    }


    public void setWrongAnswerView() {
        mQuizQuestionFragment.getView().setVisibility(View.GONE);
        mCorrectAnsFragment.getView().setVisibility(View.GONE);
        mWrongAnsFragment.getView().setVisibility(View.VISIBLE);

    }
    public void setWrongAnswerViewContent() {
    }


}
