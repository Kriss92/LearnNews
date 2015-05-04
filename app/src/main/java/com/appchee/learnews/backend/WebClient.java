package com.appchee.learnews.backend;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.appchee.learnews.beans.QuestionBean;
import com.appchee.learnews.beans.RatingBean;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class WebClient extends WebFunctions {
    private final int ANSWER_CHOICES = 4;

    public WebClient() {

    }

    public boolean addQuestion(QuestionBean questionBean) {

        String postParameters = "question=" + questionBean.getQuestion();

        postParameters += "&answer1" + "=" + questionBean.getAnswer1();
        postParameters += "&answer2" + "=" + questionBean.getAnswer2();
        postParameters += "&answer3" + "=" + questionBean.getAnswer3();
        postParameters += "&answer4" + "=" + questionBean.getAnswer4();

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

    public boolean syncRatings(RatingBean[] ratingBeans) {

        int i;
        for(i = 0; i < ratingBeans.length; i++) {
            String postParameters = "";
            postParameters += "questionId=" + ratingBeans[i].getQuestionId();
            postParameters += "&userId=" + ratingBeans[i].getUserId();
            postParameters += "&rating=" + ratingBeans[i].getRating();

            Log.d("questionId=", ""+ratingBeans[i].getQuestionId());
            Log.d("userId=", ""+ratingBeans[i].getUserId());
            Log.d("rating=", ""+ratingBeans[i].getRating());
            String response = sendRequestPOST("syncRatings.php", postParameters);
            Log.d("response=", ""+response);


            if(!response.equals("success")) {
                return false;
            }
        }

        return true;
    }

    public List<QuestionBean> syncQuestions(int userID, int numQuestionsInLocalDb) throws IOException {
        String postParameters = "userId=" + userID + "&numQuestionsInLocalDb=" + numQuestionsInLocalDb;
        String response = sendRequestPOST("syncQuestions.php", postParameters);

        if(! response.equals("false")) {
            Log.d("response is ", response);
            List<QuestionBean> result = new ArrayList<QuestionBean>();

            JsonReader reader = new JsonReader(new StringReader(response));

            reader.beginArray();
            while (reader.hasNext()) {
                result.add(readQuestionObject(reader));
            }
            reader.endArray();

            return result;
        }
        return null;
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
                } else if (name.equals("rating")) {
                    questionBean.setRating((float) reader.nextDouble());
                }
            }
        }
        reader.endObject();

        return questionBean;

    }

}
