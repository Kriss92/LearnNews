package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appchee.learnews.actions.RecentQuestionsManager;
import com.appchee.learnews.beans.RecentQuestionBean;


public class RecentQuestionsActivity extends Activity {

    TextView mCurrRecentQuestion;
    RecentQuestionsManager mRecentQuestionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_questions);
        mRecentQuestionsManager = new RecentQuestionsManager(getApplicationContext());
        RecentQuestionBean recentQuestionBean = mRecentQuestionsManager.getRecentQuestionBeanByNumber(1);
        mCurrRecentQuestion = (TextView) findViewById(R.id.most_recent_question);
        mCurrRecentQuestion.setText(recentQuestionBean.getQuestion());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_previous_questions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
