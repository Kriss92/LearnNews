package com.appchee.learnews.backend;

import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

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


//    public List syncQuestions(InputStream in) throws IOException {
//        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
//        try {
//            return readMessagesArray(reader);
//        }
//            finally{
//                reader.close();
//            }
//    }

//    @Override
//    public List<JSONObject> fetchEvents() {
//        String postParameters = "userID=" + userID + "&password=" + pass;
//        String response = sendRequestPOST("fetchEvents.php", postParameters);
//        List<JSONObject> result = new ArrayList<JSONObject>();
//        try {
//            JsonReader reader = new JsonReader(new StringReader(response));
//            JSONArray array = reader.beginArray();
//            reader.beginArray();
//            while (reader.hasNext()) {
//                messages.add(readMessage(reader));
//            }
//            reader.endArray();
//            return messages;
//        } catch (JsonException ex) {
//            return null;
//        }
//        if (result.size()>0){
//            String time = result.get(result.size()-1).getString("time");
//            postParameters = "userID=" + userID + "&password=" + pass + "&time=" + time;
//            response = sendRequestPOST("deleteEvents.php", postParameters);
//            if (response.equals("failure"))
//                return null;
//        }
//        return result;
//    }
//
//
//    public List readMessagesArray(JsonReader reader) throws IOException {
//        List messages = new ArrayList();
//
//        reader.beginArray();
//        while (reader.hasNext()) {
//            messages.add(readMessage(reader));
//        }
//        reader.endArray();
//        return messages;
//    }
//
//    public Message readMessage(JsonReader reader) throws IOException {
//        long id = -1;
//        String text = null;
//        User user = null;
//        List geo = null;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("id")) {
//                id = reader.nextLong();
//            } else if (name.equals("text")) {
//                text = reader.nextString();
//            } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
//                geo = readDoublesArray(reader);
//            } else if (name.equals("user")) {
//                user = readUser(reader);
//            } else {
//                reader.skipValue();
//            }
//        }
//        reader.endObject();
//        return new Message(id, text, user, geo);
//    }




}
