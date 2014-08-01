package com.appchee.learnews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.appchee.learnews.R;

public class AddQuestionsActivity extends Activity {

    RadioButton[] buttons;
    RadioButton selectedButton;
    String newQuestionAsked;
    EditText[] answerViews;
    String[] answers;
    String question;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        initialiseButtons();
    }

    private void initialiseButtons()
    {
        //TODO: fix, make coding better, avoid duplication
        buttons = new RadioButton[4];
        buttons[0] = (RadioButton) findViewById(R.id.button_a);
        buttons[1] = (RadioButton) findViewById(R.id.button_b);
        buttons[2] = (RadioButton) findViewById(R.id.button_c);
        buttons[3] = (RadioButton) findViewById(R.id.button_d);

        answerViews = new EditText[4];
        answerViews[0] = (EditText) findViewById(R.id.answer_a);
        answerViews[1] = (EditText) findViewById(R.id.answer_b);
        answerViews[2] = (EditText) findViewById(R.id.answer_c);
        answerViews[3] = (EditText) findViewById(R.id.answer_d);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_questions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void answerButtonClicked(View view) {
        Log.d("Here we are", "We didn't crash 0");
        int index = getButtonIndex(view.getTag().toString());
        Log.d("Here", "Got to answer button");
        deselectAllOtherButtons(index);
        selectedButton = (RadioButton) view;
    }

    private int getButtonIndex(String tag) {
        int buttonIndex = (int) tag.toCharArray()[0] - 'A';
        return buttonIndex;
    }

    public void deselectAllOtherButtons(int buttonIndex) {
        int i;
        for(i = 0; i < 4; i++) {
            //if it isn't the clicked button, make all the others unchecked
            if(i - buttonIndex != 0)
            {
                buttons[i].setChecked(false);
            }
        }
    }

    public void submitButtonClicked(View view) {

        EditText questionEditText = (EditText) findViewById(R.id.add_questions_text);
        question = questionEditText.getText().toString();
        if(question.isEmpty()) {
            startToastForIncompleteData(R.string.complete_all_fields);
            questionEditText.setBackgroundColor(Color.RED);
            return;
        }
        questionEditText.setBackgroundColor(Color.WHITE);


        answers = new String[4];
        int i;
        for(i = 0; i <4; i++) {
            answers[i] = answerViews[i].getText().toString();
            if(answers[i].isEmpty()) {
                startToastForIncompleteData(R.string.complete_all_fields);
                answerViews[i].setBackgroundColor(Color.RED);
                return;
            }
            answerViews[i].setBackgroundColor(Color.WHITE);
        }

        EditText urlEditText = (EditText) findViewById(R.id.link_text);
        url = urlEditText.getText().toString();
        if(url.isEmpty()) {
            startToastForIncompleteData(R.string.complete_all_fields);
            urlEditText.setBackgroundColor(Color.RED);
            return;
        }
        urlEditText.setBackgroundColor(Color.WHITE);

        if(selectedButton != null) {
            int buttonIndex = getButtonIndex(selectedButton.getTag().toString());
        } else {
            startToastForIncompleteData(R.string.select_correct_answer);
            return;
        }

        Button submitButton = (Button) view;
        submitButton.setBackgroundColor(Color.GREEN);
        //Send information to the database: question, index, answers, url
        Log.d("Kriss tag:", "Data done");
    }

    private void startToastForIncompleteData(int message) {
        Context context = getApplicationContext();
        CharSequence text = getString(message);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
