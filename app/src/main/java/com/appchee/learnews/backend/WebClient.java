package com.appchee.learnews.backend;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.appchee.learnews.beans.QuestionBean;
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

    public List<QuestionBean> syncQuestions() throws IOException {
        String postParameters = "username=" + "Alex" + "&password=" + "pass";
        String response = sendRequestPOST("syncQuestions.php", postParameters);
        List<QuestionBean> result = new ArrayList<QuestionBean>();

        JsonReader reader = new JsonReader(new StringReader(response));
        reader.beginArray();
        while(reader.hasNext()) {
            result.add(readQuestionObject(reader));
        }
        reader.endArray();

        return result;
    }

    public QuestionBean readQuestionObject(JsonReader reader) throws IOException {

        QuestionBean questionBean = new QuestionBean();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            Log.d("Name is", name);

            if(reader.peek() != JsonToken.NULL) {
                if (name.equals("questionid")) {
                    questionBean.setId(reader.nextInt());
                } else if (name.equals("question")) {
                    questionBean.setQuestion(reader.nextString());
                } else if (name.equals("answer1")) {
                    questionBean.setAnswer1(reader.nextString());
                } else if (name.equals("answer2")) {
                    questionBean.setAnswer2(reader.nextString());
                } else if (name.equals("answer3")) {
                    questionBean.setAnswer3(reader.nextString());
                } else if (name.equals("answer4")) {
                    questionBean.setAnswer4(reader.nextString());
                } else if (name.equals("correctindex")) {
                    questionBean.setCorrectIndex(reader.nextInt());
                } else if (name.equals("newsurl")) {
                    questionBean.setNewsURL(reader.nextString());
                } else if (name.equals("category")) {
                    questionBean.setCategory(reader.nextString());
                } else if (name.equals("dateadded")) {
                    questionBean.setDateAdded(reader.nextString());
                }
            }
        }
        reader.endObject();

        return questionBean;

    }



}
