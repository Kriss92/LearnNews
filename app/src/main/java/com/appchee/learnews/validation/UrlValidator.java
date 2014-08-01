package com.appchee.learnews.validation;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by demouser on 8/1/14.
 */
public class UrlValidator implements Validator {

    private static String[] safeUrls = {"reuters", "bbc"};

    @Override
    public void validate(String text) throws ValidationException {

        try {
            boolean invalid = true;
            URL url = new URL(text);
            String[] parts = url.getHost().split("\\.");
            Log.d("URL host", url.getHost() + " len " + parts.length);
            for (String site : safeUrls) {
                if(site.equalsIgnoreCase(parts[0])) {
                    invalid = false;
                } else if (site.length() > 1 && site.equalsIgnoreCase(parts[1])) {
                    invalid = false;
                }
            }
            if (invalid) {
                throw new ValidationException("Unreliable website");
            }
        } catch (MalformedURLException e) {
            throw new ValidationException("Invalid URL");
        }

//        validateUrl()

    }
}
