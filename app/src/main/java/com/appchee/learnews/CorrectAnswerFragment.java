package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;


public class CorrectAnswerFragment extends Fragment {

    private CorrectCallback mListener;
    private TextView mCorrectAnsText;
    private TextView mComplimentText;

    String[] compliments= {};


    public CorrectAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_correct_answer, container, false);

        //Initialize
        mCorrectAnsText = (TextView) v.findViewById(R.id.ans_correct);
        mComplimentText= (TextView) v.findViewById(R.id.ans_compliment_text);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CorrectCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CorrectCallback {
    }

    public void populate(String chosenAnswer){
        mCorrectAnsText.setText(chosenAnswer);
        mComplimentText.setText(getComment());
    }

    private String getComment() {
        Random rand = new Random();
        String[] comments = getResources().getStringArray(R.array.correct_comments);
        int i = rand.nextInt(comments.length);
        return comments[i];
    }
}
