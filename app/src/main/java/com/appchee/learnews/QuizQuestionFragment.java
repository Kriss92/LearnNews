package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;




public class QuizQuestionFragment extends Fragment {

    private QuestionCallback mListener;
    private Button mSubmitCorrectAns;
    private Button mSubmitWrongAns;

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

        mSubmitCorrectAns= (Button) v.findViewById(R.id.submit_ans_button);
        mSubmitWrongAns= (Button) v.findViewById(R.id.submit_ans_button_delete);
        //Generate Question

        //Wait for user
        mSubmitCorrectAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(true);
            }
        });

        mSubmitWrongAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(false);
            }
        });


        return v;
    }

    public void onButtonPressed(boolean isCorrect) {
        if (mListener != null) {
            mListener.onAnswerSubmittedListener(isCorrect);
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
        public void onAnswerSubmittedListener(boolean isCorrect);
    }

}
