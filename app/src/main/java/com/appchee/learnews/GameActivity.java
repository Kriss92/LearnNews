package com.appchee.learnews;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appchee.learnews.actions.QuestionsManager;
import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity implements QuizQuestionFragment.QuestionCallback, StoryBarFragment.StoryBarCallback, CorrectAnswerFragment.CorrectCallback, WrongAnswerFragment.WrongCallback, CategoryDrawerFragment.CategoryCallback {

    CorrectAnswerFragment mCorrectAnsFragment;
    WrongAnswerFragment mWrongAnsFragment;
    QuizQuestionFragment mQuizQuestionFragment;

    public int mCorrectAnswer=1;
    public List<String> mAnswers = new ArrayList<String>();
    public int mCorrectPrecentage=100;
    TextView mCurrQuestion;
    QuestionsManager mManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initializing:
        mQuizQuestionFragment= (QuizQuestionFragment) getFragmentManager().findFragmentById(R.id.quiz_question_fragment);
        mWrongAnsFragment= (WrongAnswerFragment) getFragmentManager().findFragmentById(R.id.wrong_ans_fragment);
        mCorrectAnsFragment=  (CorrectAnswerFragment) getFragmentManager().findFragmentById(R.id.correct_ans_fragment);
        mCurrQuestion= (TextView) findViewById(R.id.q_text);
        mManager = new QuestionsManager(getApplicationContext());
        setNextQuestion();
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
            mCorrectAnsFragment.populate( mAnswers.get(answerSelected) , mCorrectPrecentage);
            setCorrectAnswerView();
            //generate answer view in fragment
        } else {
             mWrongAnsFragment.populate( mAnswers.get(answerSelected), mAnswers.get(mCorrectAnswer));
            setWrongAnswerView();

            //generate answer view in fragment
        }
        //generating story fragment
    }

    @Override
    public void onSkipButtonListener() {
        onContinueButtonListener();
        //TODO: Update DataBase
    }

    @Override
    public void onContinueButtonListener() {
        setNextQuestionView();
        setNextQuestionViewContent();
    }
    @Override
    public void onSaveStoryButtonListener() {
        Log.d("Rony", "Saving Story....");
;    }

    @Override
    public void onSendStoryButtonListener() {
        Log.d("Rony", "Sending Story....");

    }


    //methods:

    public void setNextQuestion() {
        setNextQuestionView();
        setNextQuestionViewContent();
    }

    public void setNextQuestionViewContent() {
        QuestionBean question = mManager.getNextQuestion();

        mCurrQuestion.setText(question.getQuestion());
        mAnswers.clear();
        List<String> answers = new ArrayList<String>();
        for (AnswerBean answer : question.getAnswers()) {
            answers.add(answer.getAnswer());
            mAnswers.add(answer.getAnswer());
        }
        // mAnswers = answers;
        Log.d("Before we crash ", " num answers " + answers.size());
        mCorrectAnswer = question.getCorrectAnswer();
        mQuizQuestionFragment.populate(answers);
    }

    public void setNextQuestionView() {
        mQuizQuestionFragment.getView().setVisibility(View.VISIBLE);
        mCorrectAnsFragment.getView().setVisibility(View.GONE);
        mWrongAnsFragment.getView().setVisibility(View.GONE);
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


    @Override
    public void onCategoriesSelected(String[] categories) {

    }
}
