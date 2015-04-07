package com.appchee.learnews.backend;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.google.android.gms.games.quest.Quest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kriss on 01/04/2015.
 */
public class WebClient extends WebFunctions {
    private final int ANSWER_CHOICES = 4;

    public WebClient() {

    }

    public boolean addQuestion(String question, String[] answers, Integer correctAnswerIndex, String url) {
        String postParameters = "question=" + question;
        int i;
        for (i = 1; i <= ANSWER_CHOICES; i++)
            postParameters += "&answer" + i + "=" + answers[i-1];

        postParameters += "&index=" + correctAnswerIndex + "&newsUrl=" + url;
        String response = sendRequestPOST("addQ.php", postParameters);
        String toPrint = "Adding question result:" + (response != null ? response : "no_connection");
        Log.d("Connection", toPrint);
        return response.equals("success");

    }

    public List<QuestionObject> syncQuestions() throws IOException {
        String postParameters = "username=" + "Alex" + "&password=" + "pass";
        String response = sendRequestPOST("syncQuestions.php", postParameters);
        List<QuestionObject> result = new ArrayList<QuestionObject>();

        JsonReader reader = new JsonReader(new StringReader(response));
        reader.beginArray();
        while(reader.hasNext()) {
            result.add(readQuestionObject(reader));
        }
        reader.endArray();

        return result;
    }

    public QuestionObject readQuestionObject(JsonReader reader) throws IOException {

        String Question = null;
        String Answer1 = null;
        String Answer2 = null;
        String Answer3 = null;
        String Answer4 = null;
        int CorrectIndex = 0;
        String NewsUrl = null;
        int i;
        String d;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            Log.d("Name is", name);

            if(reader.peek() != JsonToken.NULL) {
                if (name.equals("question")) {
                    Question = reader.nextString();
                } else if (name.equals("answer1")) {
                    Answer1 = reader.nextString();
                } else if (name.equals("answer2")) {
                    Answer2 = reader.nextString();
                } else if (name.equals("answer3")) {
                    Answer3 = reader.nextString();
                } else if (name.equals("answer4")) {
                    Answer4 = reader.nextString();
                } else if (name.equals("correctindex")) {
                    CorrectIndex = reader.nextInt();
                } else if (name.equals("newsurl")) {
                    NewsUrl = reader.nextString();
                } else if (name.equals("questionid")) {
                    i = reader.nextInt();
                } else if (name.equals("dateadded")) {
                    d = reader.nextString();
                }
            }
        }
        reader.endObject();
        return new QuestionObject(Question, Answer1, Answer2, Answer3, Answer4, CorrectIndex, NewsUrl);
    }

    private class QuestionObject {

        String Question = null;
        String Answer1 = null;
        String Answer2 = null;
        String Answer3 = null;
        String Answer4 = null;
        int CorrectIndex = 0;
        String NewsUrl = null;

        public QuestionObject(String Question,String Answer1, String Answer2, String Answer3,
                              String Answer4, int CorrectIndex, String NewsUrl) {

            this.Question = Question;
            this.Answer1 = Answer1;
            this.Answer2 = Answer2;
            this.Answer3 = Answer3;
            this.Answer4 = Answer4;
            this.CorrectIndex = CorrectIndex;
            this.NewsUrl = NewsUrl;
        }

    }



}
