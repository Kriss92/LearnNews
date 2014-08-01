package com.appchee.learnews.validation;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by demouser on 8/1/14.
 */
public class UnsafeContentValidator implements Validator {


    private static String[] offencive = new String[] {
            "anal","anus","arse","ass","ballsack","balls","bastard","bitch","biatch","bloody","blowjob","blow job","bollock",
            "bollok","boner","boob","bugger","bum","butt","buttplug","clitoris","cock","coon","crap","cunt","damn","dick",
            "dildo","dyke","fag","feck","fellate","fellatio","felching","fuck","f u c k","fudgepacker","fudge packer","flange",
            "Goddamn","God damn","hell","homo","jerk","jizz","knobend","knob end","labia","lmao","lmfao","muff","nigger","nigga",
            "omg","penis","piss","poop","prick","pube","pussy","queer","scrotum","sex","shit","s hit","sh1t","slut","smegma",
            "spunk","tit","tosser","turd","twat","vagina","wank","whore","wtf"};


    private static HashSet<String> offenciveSet = new HashSet<String>(Arrays.asList(offencive));


    @Override
    public void validate(String text) throws ValidationException {
        Log.d("Validator", "Validate");
        for(String word : text.split("\\s+")) {
            word = word.toLowerCase();
            if(offenciveSet.contains(word)) {
                Log.d("word", word);
                throw new ValidationException("Offencive word detected");
            }
        }
    }
}
