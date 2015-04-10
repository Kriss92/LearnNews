package com.appchee.learnews.backend;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.appchee.learnews.beans.QuestionBean;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kriss on 01/04/2015.
 */
public class WebClient extends WebFunctions {
    private final int ANSWER_CHOICES = 4;

    Integer userID = -1;
    String pass;
    String email;

    public WebClient() {

    }

    public boolean addQuestion(QuestionBean questionBean) {

        String postParameters = "question=" + questionBean.getQuestion();

        postParameters += "&answer1" + "=" + questionBean.getAnswer1();
        postParameters += "&answer2" + "=" + questionBean.getAnswer1();
        postParameters += "&answer3" + "=" + questionBean.getAnswer1();
        postParameters += "&answer4" + "=" + questionBean.getAnswer1();

        postParameters += "&index=" + questionBean.getCorrectIndex() + "&newsUrl=" +
                questionBean.getNewsURL() + "&category=" + questionBean.getCategory();

        String response = sendRequestPOST("addQ.php", postParameters);
        String toPrint = "Adding question result:" + (response != null ? response : "no_connection");
        Log.d("Connection", toPrint);
        return response.equals("success");

    }

    public boolean reportQuestion(int questionId) {
        String postParameters = "questionId= " + questionId;
        String response = sendRequestPOST("reportQuestion.php", postParameters);
        return response.equals("success");
    }

    public int signInUser(String email, String password) {
        String postParameters = "email=" + email + "&password=" + password;
        String response = sendRequestPOST("signInUser.php", postParameters);
        Log.d("Email, password & response = ", email + " " + password + " " + response);
        if(response.equals("")) {
           return -2;
        }
        return Integer.parseInt(response);
    }

    public List<QuestionBean> syncQuestions(int userID) throws IOException {
        String postParameters = "userId=" + userID;
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
