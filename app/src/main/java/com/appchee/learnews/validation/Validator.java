package com.appchee.learnews.validation;

/**
 * Created by demouser on 8/1/14.
 */
public interface Validator {

    public void validate(String text) throws ValidationException;
}
