package com.appchee.learnews.actions;
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
        while (m.find() == true) {
            return m.group(1);
        }
        return address;
    }
}
