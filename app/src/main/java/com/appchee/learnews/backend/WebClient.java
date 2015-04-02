package com.appchee.learnews.backend;

import android.util.Log;

/**
 * Created by Kriss on 01/04/2015.
 */
public class WebClient extends WebFunctions {
    private final int ANSWER_CHOICES = 4;

    public WebClient() {

    }

    public boolean addQuestion(String question, String[] answers, Integer correctAnswerIndex, String newsArticleUrl) {
        String postParameters = "question=" + question;
        int i;
        for (i = 1; i <= ANSWER_CHOICES; i++)
            postParameters += "&answer" + i + "=" + answers[i-1];

        postParameters += "&index=" + correctAnswerIndex + "&newsUrl=" + newsArticleUrl;
        String response = sendRequestPOST("addQuestion.php", postParameters);
        String toPrint = "Adding question result:" + (response != null ? response : "no_connection");
        Log.d("Connection", toPrint);
        return response.equals("success");

    }

}
