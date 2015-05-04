package com.appchee.learnews;

import com.appchee.learnews.backend.WebClient;

import java.io.IOException;

/**
 * Created by Kriss on 10/04/2015.
 */
public class CurrentUserDetails {
    public static int userId;
    public static String email;
    public static String password;
    public static int score;
    public static boolean isUserInitialised = false;

    public static void syncCurrentUsersScore() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebClient webc = new WebClient();
                try {
                    score = webc.syncScore(userId, score);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        thread.join();
    }
}
