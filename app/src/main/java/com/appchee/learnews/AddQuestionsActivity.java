package com.appchee.learnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.appchee.learnews.beans.AnswerBean;
import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.database.DbInteractions;
import com.appchee.learnews.validation.ValidationException;
import com.appchee.learnews.backend.WebClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AddQuestionsActivity extends Activity {

    RadioButton[] buttons;
    Spinner spinner;
    RadioButton selectedButton;
    String newQuestionAsked;
    EditText[] answerViews;
    String[] answers;
    String question;
    String url;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        initialiseButtons();

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
        Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnTouchListener(touchListener);
    }

    private void initialiseButtons() {
        initializeSpinner();


        //TODO: fix, make coding better, avoid duplication
        buttons = new RadioButton[4];
        buttons[0] = (RadioButton) findViewById(R.id.button_a);
        buttons[0].setButtonDrawable(R.drawable.radio_button);
        buttons[1] = (RadioButton) findViewById(R.id.button_b);
        buttons[1].setButtonDrawable(R.drawable.radio_button);
        buttons[2] = (RadioButton) findViewById(R.id.button_c);
        buttons[2].setButtonDrawable(R.drawable.radio_button);
        buttons[3] = (RadioButton) findViewById(R.id.button_d);
        buttons[3].setButtonDrawable(R.drawable.radio_button);


        answerViews = new EditText[4];
        answerViews[0] = (EditText) findViewById(R.id.answer_a);
        answerViews[1] = (EditText) findViewById(R.id.answer_b);
        answerViews[2] = (EditText) findViewById(R.id.answer_c);
        answerViews[3] = (EditText) findViewById(R.id.answer_d);
    }

    private void initializeSpinner() {
        spinner = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = getResources().getStringArray(R.array.categories)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    public void nextButtonClicked(View view) {

    }

    public void answerButtonClicked(View view) {
        Log.d("Here we are", "We didn't crash 0");
        int index = getButtonIndex(view.getTag().toString());
        Log.d("Here", "Got to answer button");
        deselectAllOtherButtons(index);
        ((RadioButton) view).setButtonDrawable(R.drawable.selected_radio_button);
        selectedButton = (RadioButton) view;
    }

    private int getButtonIndex(String tag) {
        int buttonIndex = (int) tag.toCharArray()[0] - 'A';
        return buttonIndex;
    }

    public void deselectAllOtherButtons(int buttonIndex) {
        int i;
        for (i = 0; i < 4; i++) {
            //if it isn't the clicked button, make all the others unchecked
            if (i - buttonIndex != 0) {
                buttons[i].setButtonDrawable(R.drawable.radio_button);
                buttons[i].setChecked(false);
            }
        }
    }

    public void submitButtonClicked(View view) {

        EditText questionEditText = (EditText) findViewById(R.id.add_questions_text);
        question = questionEditText.getText().toString();
        if (question.isEmpty()) {
            startToastForIncompleteData(R.string.complete_all_fields);
            questionEditText.setBackgroundColor(Color.RED);
            return;
        }
        questionEditText.setBackgroundColor(Color.WHITE);

        Log.d("Add Question", "Submit button is clicked");

        List<AnswerBean> answerBeans = new ArrayList<AnswerBean>(4);

        answers = new String[4];
        int i;
        for (i = 0; i < 4; i++) {
            answers[i] = answerViews[i].getText().toString();
            //Log.d("answer", "Answer " + answers[i]);
            if (answers[i].isEmpty()) {
                startToastForIncompleteData(R.string.complete_all_fields);
                answerViews[i].setBackgroundColor(Color.RED);
                return;
            } else {
                answerViews[i].setBackgroundColor(Color.WHITE);
                answerBeans.add(new AnswerBean(answers[i], false));
            }
        }

        EditText urlEditText = (EditText) findViewById(R.id.link_text);
        url = urlEditText.getText().toString();
        if (url.isEmpty()) {
            startToastForIncompleteData(R.string.complete_all_fields);
            urlEditText.setBackgroundColor(Color.RED);
            return;
        }
        urlEditText.setBackgroundColor(Color.WHITE);

        int buttonIndex = 0;
        if (selectedButton != null) {
            buttonIndex = getButtonIndex(selectedButton.getTag().toString());
            answerBeans.get(buttonIndex).setCorrect(true);
        } else {
            startToastForIncompleteData(R.string.select_correct_answer);
            return;
        }

        try {
            try {
                saveQuestion(category, question, url, answers, buttonIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (ValidationException e) {
            makeToastForInvalidQuestion(e.getMessage());
        }

        Button submitButton = (Button) view;
        submitButton.setBackgroundColor(Color.GREEN);

        Intent refresh = new Intent(this, AddQuestionsActivity.class);
        finish();
        startActivity(refresh);
    }

    private void makeToastForInvalidQuestion(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void startToastForIncompleteData(int message) {
        Context context = getApplicationContext();
        CharSequence text = getString(message);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void saveQuestion(final String category, final String question, final String url, final String[] answers, final int buttonIndex)
            throws ValidationException, InterruptedException {

        Log.d("Add Question", "Try to save");
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setId(100);
        questionBean.setQuestion(question);
        questionBean.setCategory(category);
        questionBean.setAnswer1(answers[0]);
        questionBean.setAnswer2(answers[1]);
        questionBean.setAnswer3(answers[2]);
        questionBean.setAnswer4(answers[3]);

        questionBean.setCorrectIndex(buttonIndex);
        questionBean.setNewsURL(url);
        questionBean.setCategory(category);
        questionBean.setDateAdded("Today");
        questionBean.setRating(5);

        questionBean.validate();
        //new DbInteractions(this.getApplicationContext()).addQuestion(questionBean);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebClient webc = new WebClient();
                webc.addQuestion(questionBean);
            }
        });
        thread.start();

        thread.join();
    }

}
