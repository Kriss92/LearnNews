package com.appchee.learnews.actions;
import android.util.Log;

import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demouser on 7/31/14.
 */
public class NewsManager {


    public static String extractTitle(String address) throws Exception {

        try {
            URL url = new URL(address);
            URLConnection urlConnection = url.openConnection();
            DataInputStream dis = new DataInputStream(urlConnection.getInputStream());
            String html = "", tmp = "";
            while ((tmp = dis.readLine()) != null) {
                html += " " + tmp;
            }
            dis.close();

            html = html.replaceAll("\\s+", " ");
            Pattern p = Pattern.compile("<title>(.*?)</title>");
            Matcher m = p.matcher(html);
            String result = url.getHost();
            while (m.find() == true) {
                result = m.group(1);
                break;
            }
            Log.d("Title parsed", result);
            return result;
        } catch (Exception e) {
            return address;
        }
    }
}
