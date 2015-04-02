package com.appchee.learnews.backend;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.List;

import org.json.JSONObject;

public abstract class WebFunctions{


    protected static HttpURLConnection connect(String file) throws IOException {
        URL url = new URL("http://www.doc.ic.ac.uk/~ceb111/" + file);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setReadTimeout(10000);
        httpCon.setConnectTimeout(15000);
        httpCon.setDoInput(true);
        httpCon.setDoOutput(true);
        return httpCon;
    }

    protected static String sendRequestPOST(String file, String postParameters) {
        String response = "";
        HttpURLConnection httpCon = null;
        try {
            httpCon = connect(file);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("contentType", "application/json; charset=utf-8");
            httpCon.setFixedLengthStreamingMode(postParameters.getBytes().length);
            httpCon.connect();
            OutputStream os = httpCon.getOutputStream();
            PrintWriter out = new PrintWriter(httpCon.getOutputStream());
            out.print(postParameters);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response += inputLine;
            in.close();

        }  catch (IOException e) {
            Log.d("Exceptions in networking: ", Log.getStackTraceString(e));
        } finally {
            if (httpCon != null) {
                httpCon.disconnect();
            }
        }

        return response;
    }
}

//    protected static String encrypt(String text){
//        String result = "ERROR";
//        try{
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(text.getBytes("UTF-8"));
//            byte[] digest = md.digest();
//            StringBuffer sb = new StringBuffer();
//            for(byte b : digest) {
//                sb.append(Integer.toHexString(b & 0xff));
//            }
//            result = sb.toString();
//        } catch(Exception e) {
//
//        }
//        return result;
//    }

