package com.appchee.learnews;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appchee.learnews.actions.QuestionsManager;
import com.appchee.learnews.actions.RatingsManager;
import com.appchee.learnews.actions.RecentQuestionsManager;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.RecentQuestionBean;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity implements QuizQuestionFragment.QuestionCallback,
        StoryBarFragment.StoryBarCallback, CorrectAnswerFragment.CorrectCallback,
        WrongAnswerFragment.WrongCallback, CategoryDrawerFragment.CategoryCallback {

    CorrectAnswerFragment mCorrectAnsFragment;
    WrongAnswerFragment mWrongAnsFragment;
    QuizQuestionFragment mQuizQuestionFragment;

    public int mCorrectAnswer = 1;
    String mAnswer1;
    String mAnswer2;
    String mAnswer3;
    String mAnswer4;
    ImageView mCategoryPic;
    TextView mCategory;
    TextView mCurrQuestion;
    TextView mScoreView;
    QuestionsManager mQuestionsManager;
    RecentQuestionsManager mRecentQuestionsManager;
    RatingsManager mRatingsManager;
    QuestionBean mCurrentQuestionBean;
    String mCorrectAnswerText = null;
    RatingBar mRatingBar;
    float mRatingGiven = -1;
    int correctAnswerScore = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initializing:
        mQuizQuestionFragment = (QuizQuestionFragment) getFragmentManager().findFragmentById(R.id.quiz_question_fragment);
        mWrongAnsFragment = (WrongAnswerFragment) getFragmentManager().findFragmentById(R.id.wrong_ans_fragment);
        mCorrectAnsFragment = (CorrectAnswerFragment) getFragmentManager().findFragmentById(R.id.correct_ans_fragment);
        mCurrQuestion = (TextView) findViewById(R.id.q_text);
        mScoreView = (TextView) findViewById(R.id.score_view);
        mCategory = (TextView) findViewById(R.id.q_category);
        mCategoryPic = (ImageView) findViewById(R.id.q_img);
        initialiseRatingBar();
        mQuestionsManager = new QuestionsManager(getApplicationContext());
        mQuestionsManager.currentQuestionNum = -1;
        mRecentQuestionsManager = new RecentQuestionsManager(getApplicationContext());
        mRatingsManager = new RatingsManager(getApplicationContext());

        setNextQuestion();

    }

    private void initialiseRatingBar() {
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                updateRating(rating);
            }
        });
    }

    public void updateRating(float rating) {
        mRatingsManager.updateRating(mCurrentQuestionBean.getId(), CurrentUserDetails.userId, rating);
    }

    public void onReport(View view) {
        mQuestionsManager.reportQuestion(mCurrentQuestionBean);
        mQuestionsManager.deleteQuestion(mCurrentQuestionBean);
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

        String selectedAnswerText = getAnswer(answerSelected);

        addRecentQuestion(answerSelected);

        if (mCorrectAnswer == answerSelected) {
            mCorrectAnsFragment.populate(selectedAnswerText);
            mQuestionsManager.deleteQuestion(mCurrentQuestionBean);
            setCorrectAnswerView();
        } else {
            mWrongAnsFragment.populate(selectedAnswerText, mCorrectAnswerText);
            setWrongAnswerView();
        }
    }

    private void addRecentQuestion(int answerSelected) {
        RecentQuestionBean recentQuestionBean = new RecentQuestionBean();
        recentQuestionBean.setId(mCurrentQuestionBean.getId());
        recentQuestionBean.setQuestion(mCurrentQuestionBean.getQuestion());
        recentQuestionBean.setAnswer1(mCurrentQuestionBean.getAnswer1());
        recentQuestionBean.setAnswer2(mCurrentQuestionBean.getAnswer2());
        recentQuestionBean.setAnswer3(mCurrentQuestionBean.getAnswer3());
        recentQuestionBean.setAnswer4(mCurrentQuestionBean.getAnswer4());
        recentQuestionBean.setCorrectIndex(mCurrentQuestionBean.getCorrectIndex());
        recentQuestionBean.setSelectedIndex(answerSelected);
        recentQuestionBean.setNewsURL(mCurrentQuestionBean.getNewsURL());
        recentQuestionBean.setCategory(mCurrentQuestionBean.getCategory());
        recentQuestionBean.setDateAdded(mCurrentQuestionBean.getDateAdded());
        recentQuestionBean.setDateAnswered(System.currentTimeMillis());
        recentQuestionBean.setRating(mCurrentQuestionBean.getRating());

        mRecentQuestionsManager.addRecentQuestion(recentQuestionBean);

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
        mQuestionsManager.saveStory(mCurrentQuestionBean);
        Toast.makeText(this, "Story saved.", Toast.LENGTH_LONG).show();
        ;
    }

    @Override
    public void onSendStoryButtonListener() {
        Log.d("Rony", "Sending Story....");

    }


    //methods:

    public void setNextQuestion() {
        mCurrentQuestionBean = mQuestionsManager.getNextQuestion();
        if (mCurrentQuestionBean == null) {
            makeToast("You are out of questions. Press 'sync' for more!");

            Intent intent;
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            String category = mCurrentQuestionBean.getCategory();
            mCategory.setText(category);
            setCategoryImage(category);

            setNextQuestionView();
            setNextQuestionViewContent();
        }
    }

    private void makeToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
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

        mCorrectAnswerText = getAnswer(mCorrectAnswer);
    }

    private String getAnswer(int index) {
        switch (index) {
            case 0:
                return mAnswer1;
            case 1:
                return mAnswer2;
            case 2:
                return mAnswer3;
            case 3:
                return mAnswer4;
        }

        return "";
    }

    public void setNextQuestionView() {
        mQuizQuestionFragment.getView().setVisibility(View.VISIBLE);
        mRatingBar.setVisibility(View.GONE);
        mCorrectAnsFragment.getView().setVisibility(View.GONE);
        mWrongAnsFragment.getView().setVisibility(View.GONE);
    }

    public void setCorrectAnswerView() {
        CurrentUserDetails.score += correctAnswerScore;
        mScoreView.setText("" + CurrentUserDetails.score);
        mQuizQuestionFragment.getView().setVisibility(View.GONE);
        mRatingBar.setVisibility(View.VISIBLE);
        mCorrectAnsFragment.getView().setVisibility(View.VISIBLE);
        mWrongAnsFragment.getView().setVisibility(View.GONE);
    }


    public void setWrongAnswerView() {
        mQuizQuestionFragment.getView().setVisibility(View.GONE);
        mCorrectAnsFragment.getView().setVisibility(View.GONE);
        mRatingBar.setVisibility(View.VISIBLE);
        mWrongAnsFragment.getView().setVisibility(View.VISIBLE);

    }


    @Override
    public void onCategoriesSelected(String[] categories) {

    }

    public void setCategoryImage(String category) {
        Log.d("test", "Picture Change");
        String[] categories = getResources().getStringArray(R.array.categories);
        for (String c : categories) {
            if (c.equals(category)) {
                Log.d("test", c.toLowerCase());
                int img = getResources().getIdentifier(c.toLowerCase(), "drawable", getPackageName());
                mCategoryPic.setImageResource(img);
            }
        }
    }

}
