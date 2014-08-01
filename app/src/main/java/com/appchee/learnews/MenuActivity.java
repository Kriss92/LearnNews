package com.appchee.learnews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appchee.learnews.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.zip.Inflater;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sign_out:
                //Sign user out
                break;
            case R.id.action_about:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newGameMenuButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void addQuestionsMenuButtonClicked(View view) {
        Intent intent = new Intent(this, AddQuestionsActivity.class);
        startActivity(intent);
    }

    public void SavedStoriesMenuButtonClicked(View view) {
        Intent intent = new Intent(this, SavedStoriesActivity.class);
        startActivity(intent);
    }

}
