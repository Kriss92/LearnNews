package com.appchee.learnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appchee.learnews.backend.WebClient;
import com.appchee.learnews.beans.QuestionBean;

import java.io.IOException;
import java.util.List;


public class SignInActivity extends Activity {

    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

    public void signInButtonClicked(View view) throws InterruptedException {
        EditText emailEditText = (EditText) findViewById(R.id.email_field);
        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            startToast(R.string.complete_all_fields);
            emailEditText.setBackgroundColor(Color.parseColor("#FFB1A3"));
            return;
        }
        emailEditText.setBackgroundColor(Color.WHITE);

        EditText passwordEditText = (EditText) findViewById(R.id.password_field);
        String password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            startToast(R.string.complete_all_fields);
            passwordEditText.setBackgroundColor(Color.parseColor("#FFB1A3"));
            return;
        }
        passwordEditText.setBackgroundColor(Color.WHITE);

        tryToSignIn(email, password);
        if(userId == -1) {
            startToast(R.string.invalid_password);
        } else if (userId == -2) {
            startToast(R.string.invalid_email);
        } else {
            Intent intent;
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            Log.d("My user id is", "" + userId);
            CurrentUserDetails.isUserInitialised = true;
            CurrentUserDetails.userId = userId;
            CurrentUserDetails.email = email;
            CurrentUserDetails.password = password;
        }
    }

    private void startToast(int message) {
        Context context = getApplicationContext();
        CharSequence text = getString(message);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void tryToSignIn(final String email, final String password) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebClient webc = new WebClient();
                setUserId(webc.signInUser(email, password));
            }
        });
        thread.start();

        thread.join();
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }

    private int getUserId() {
        return this.userId;
    }
}
