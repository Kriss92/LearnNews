package com.appchee.learnews;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appchee.learnews.actions.QuestionsManager;
import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity implements QuizQuestionFragment.QuestionCallback,
        StoryBarFragment.StoryBarCallback, CorrectAnswerFragment.CorrectCallback,
        WrongAnswerFragment.WrongCallback, CategoryDrawerFragment.CategoryCallback {

    CorrectAnswerFragment mCorrectAnsFragment;
    WrongAnswerFragment mWrongAnsFragment;
    QuizQuestionFragment mQuizQuestionFragment;

    public int mCorrectAnswer=1;
    String mAnswer1;
    String mAnswer2;
    String mAnswer3;
    String mAnswer4;
    ImageView mCategoryPic;
    TextView mCategory;
    TextView mCurrQuestion;
    QuestionsManager mManager;
    QuestionBean mCurrentQuestionBean;
    String mCorrectAnswerText = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initializing:
        mQuizQuestionFragment= (QuizQuestionFragment) getFragmentManager().findFragmentById(R.id.quiz_question_fragment);
        mWrongAnsFragment= (WrongAnswerFragment) getFragmentManager().findFragmentById(R.id.wrong_ans_fragment);
        mCorrectAnsFragment=  (CorrectAnswerFragment) getFragmentManager().findFragmentById(R.id.correct_ans_fragment);
        mCurrQuestion= (TextView) findViewById(R.id.q_text);
        mCategory = (TextView) findViewById(R.id.q_category);
        mCategoryPic= (ImageView) findViewById(R.id.q_img);
        mManager = new QuestionsManager(getApplicationContext());
        setNextQuestion();
    }




    public void onReport(View view) {
        mManager.reportQuestion(mCurrentQuestionBean);
        onSkipButtonListener();
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

        String selectedAnswerText = null;
        setAnswer(answerSelected, selectedAnswerText);

        if (mCorrectAnswer == answerSelected) {
            Double correctPercentage = mManager.answerQuestion(mCurrentQuestionBean, true);
            mCorrectAnsFragment.populate(selectedAnswerText, correctPercentage.intValue());

            setCorrectAnswerView();
            //generate answer view in fragment
        } else {
            mWrongAnsFragment.populate(selectedAnswerText, mCorrectAnswerText);
            mManager.answerQuestion(mCurrentQuestionBean, false);
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
        setNextQuestion();
    }
    @Override
    public void onSaveStoryButtonListener() {
        Log.d("Rony", "Saving Story...." + mCurrentQuestionBean.getNewsURL());
        mManager.saveStrory(mCurrentQuestionBean);
        Toast.makeText(this, "Story saved.", Toast.LENGTH_LONG).show();
        ;    }

    @Override
    public void onSendStoryButtonListener() {
        Log.d("Rony", "Sending Story....");

    }


    //methods:

    public void setNextQuestion() {
        mCurrentQuestionBean = mManager.getNextQuestion();
        mManager.updateInteraction(mCurrentQuestionBean);
        String category=mCurrentQuestionBean.getCategory();
        mCategory.setText(category);
        setCategoryImage(category);

        setNextQuestionView();
        setNextQuestionViewContent();
    }

    public void setNextQuestionViewContent() {

        mCurrQuestion.setText(mCurrentQuestionBean.getQuestion());
        List<String> answers = new ArrayList<String>();
        mAnswer1 = mCurrentQuestionBean.getAnswer1();
        answers.add(mAnswer1);

        mAnswer2 = mCurrentQuestionBean.getAnswer2();
        answers.add(mAnswer2);

        mAnswer3 = mCurrentQuestionBean.getAnswer3();
        answers.add(mAnswer3);

        mAnswer4 = mCurrentQuestionBean.getAnswer4();
        answers.add(mAnswer4);

        mCorrectAnswer = mCurrentQuestionBean.getCorrectIndex();
        mQuizQuestionFragment.populate(answers);

        setAnswer(mCorrectAnswer, mCorrectAnswerText);
    }

    private void setAnswer(int index, String answerVar) {
        switch (index) {
            case 0:
                answerVar = mAnswer1;
                break;
            case 1:
                answerVar = mAnswer2;
                break;
            case 2:
                answerVar = mAnswer3;
                break;
            case 3:
                answerVar = mAnswer4;
                break;
        }
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

    public void setCategoryImage(String category) {
        Log.d("test", "Picture Change");
        String[] categories = getResources().getStringArray(R.array.categories);
        for (String c: categories) {
            if (c.equals(category)) {
                Log.d("test", c.toLowerCase());
                int img= getResources().getIdentifier(c.toLowerCase(), "drawable" , getPackageName() );
                mCategoryPic.setImageResource(img);
            }
        }
    }

}
