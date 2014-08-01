package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class QuizQuestionFragment extends Fragment {

    private QuestionCallback mListener;
    private Button mSkipButton;
    private List<TextView> mAnswers;

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
        View v = inflater.inflate(R.layout.fragment_quiz_question, container, false);
        mSkipButton = (Button) v.findViewById(R.id.skip_button);
        mAnswers = new ArrayList<TextView>();
        mAnswers.add((TextView) v.findViewById(R.id.q_answer0));
        mAnswers.add((TextView) v.findViewById(R.id.q_answer1));
        mAnswers.add((TextView) v.findViewById(R.id.q_answer2));
        mAnswers.add((TextView) v.findViewById(R.id.q_answer3));


        //Generate Question and answers

        //On skip
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSkipPressed();
            }
        });
        //On selected Answer
        for (int i = 0; i < mAnswers.size(); i++) {
            mAnswers.get(i).setOnClickListener(new AnswersOnClickListener(i));
        }
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.animate().setDuration(200).scaleX(0.75f).scaleY(0.75f);
                }
                return false;
            }
        };
        mSkipButton.setOnTouchListener(touchListener);

        return v;
    }

    public void onSkipPressed() {
        if (mListener != null) {
            mListener.onSkipButtonListener();
            Log.d("Rony", "Skip!");
        }
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
        public void onSkipButtonListener();
    }

    public void populate(List<String> answers){
        for(int i = 0; i < answers.size(); i++) {
            mAnswers.get(i).setText(answers.get(i));
        }
    }

    private class AnswersOnClickListener implements View.OnClickListener {

        int mIndex = 0;

        public AnswersOnClickListener(int index) {
            mIndex = index;
        }

        @Override
        public void onClick(View v) {
            Log.d("Id = ", ((Integer)mIndex).toString());
            onAnswerPressed(mIndex);

        }
    }

}
