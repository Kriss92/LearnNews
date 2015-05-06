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

import static com.appchee.learnews.CurrentUserDetails.isUserInitialised;


public class RegisterActivity extends Activity {

    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void registerAccountButtonClicked(View view) throws InterruptedException {
        EditText emailEditText = (EditText) findViewById(R.id.email_field);
        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            startToast(R.string.complete_all_fields);
            emailEditText.setBackgroundColor(Color.parseColor("#FFB1A3"));
            return;
        }
        emailEditText.setBackgroundColor(Color.WHITE);

        EditText confirmEmailEditText = (EditText) findViewById(R.id.confirm_email_field);
        String confirmEmail = confirmEmailEditText.getText().toString();
        if (!confirmEmail.equals(email)) {
            Log.d("Emails are", email + " " + confirmEmail );
            startToast(R.string.emails_do_not_match);
            confirmEmailEditText.setBackgroundColor(Color.parseColor("#FFB1A3"));
            return;
        }
        confirmEmailEditText.setBackgroundColor(Color.WHITE);

        EditText passwordEditText = (EditText) findViewById(R.id.password_field);
        String password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            startToast(R.string.complete_all_fields);
            passwordEditText.setBackgroundColor(Color.parseColor("#FFB1A3"));
            return;
        } else if (password.length() < 6) {
            startToast(R.string.password_min_length);
        }
        passwordEditText.setBackgroundColor(Color.WHITE);

        EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirm_password_field);
        String confirmPassword = confirmPasswordEditText.getText().toString();
        if (!confirmPassword.equals(password)) {
            startToast(R.string.passwords_do_not_match);
            confirmPasswordEditText.setBackgroundColor(Color.parseColor("#FFB1A3"));
            return;
        }
        confirmPasswordEditText.setBackgroundColor(Color.WHITE);

        tryToRegister(email, password);
        if (userId == -1) {
            startToast(R.string.email_not_unique);
        } else {
            setUserDetails(email, password);
            startMenuActivity();
            finish();
        }
    }

    private void setUserDetails(String email, String password) throws InterruptedException {
        Log.d("My user id is", "" + userId);
        isUserInitialised = true;
        CurrentUserDetails.userId = userId;
        CurrentUserDetails.email = email;
        CurrentUserDetails.password = password;
        CurrentUserDetails.score = 0;
        CurrentUserDetails.syncCurrentUsersScore();
    }

    private void startMenuActivity() {
        Intent intent;
        intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void startToast(int message) {
        Context context = getApplicationContext();
        CharSequence text = getString(message);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void tryToRegister(final String email, final String password) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebClient webc = new WebClient();
                setUserId(webc.registerUser(email, password));
            }
        });
        thread.start();

        thread.join();
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }

    public void signInButtonClicked(View view) throws InterruptedException {
        Intent intent;
        intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
