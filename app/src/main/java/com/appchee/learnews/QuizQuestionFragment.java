package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class QuizQuestionFragment extends Fragment {

    private QuestionCallback mListener;
    private TextView mAnswer0;
    private TextView mAnswer1;
    private TextView mAnswer2;
    private TextView mAnswer3;


    public QuizQuestionFragment() {
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
        View v=inflater.inflate(R.layout.fragment_quiz_question, container, false);
        mAnswer0 = (TextView) v.findViewById(R.id.q_answer0);
        mAnswer1 = (TextView) v.findViewById(R.id.q_answer1);
        mAnswer2 = (TextView) v.findViewById(R.id.q_answer2);
        mAnswer3 = (TextView) v.findViewById(R.id.q_answer3);


        //Generate Question and answers


        //On selected Answer
        mAnswer0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerPressed(0);
            }
        });
        mAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerPressed(1);
            }
        });
        mAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerPressed(2);
            }
        });
        mAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerPressed(3);
            }
        });


        return v;
    }

    public void onAnswerPressed(int selectedAns) {
        if (mListener != null) {
            mListener.onAnswerSubmittedListener(selectedAns);
            Log.d("Rony", "AnswerPressed");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QuestionCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onAnswerSubmittedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface QuestionCallback {
        public void onAnswerSubmittedListener(int selectedAns);

    }

    public void populate(String[] answers){
        mAnswer0.setText(answers[0]);
        mAnswer1.setText(answers[1]);
        mAnswer2.setText(answers[2]);
        mAnswer3.setText(answers[3]);
    }


}
