package com.appchee.learnews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryBarFragment extends Fragment {


    private StoryBarCallback mListener;
    private Button mContinueButtom;
    private ImageView mSaveStoryButton;
    private ImageView mSendStoryButton;
    private TextView mHeadlineText;
    private ImageView mNewsSiteImg;


    public StoryBarFragment() {
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
        View v=inflater.inflate(R.layout.fragment_story_bar, container, false);


        //initialize view members
        mContinueButtom= (Button) v.findViewById(R.id.cont_button);
        mSaveStoryButton= (ImageView) v.findViewById(R.id.save_story_button);
        mSendStoryButton= (ImageView) v.findViewById(R.id.send_story_button);
        mHeadlineText= (TextView) v.findViewById(R.id.article_headline);
        mNewsSiteImg= (ImageView) v.findViewById(R.id.newsite_img);


        mContinueButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueButtonPressed();
            }
        });

        mSaveStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveStoryButtonPressed();
            }
        });

        mSendStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveStoryButtonPressed();
            }
        });

        return v;
    }

    public void onContinueButtonPressed() {
        if (mListener != null) {
            mListener.onContinueButtonListener();
        }
    }

    public void onSaveStoryButtonPressed() {
        if (mListener != null) {
            mListener.onSaveStoryButtonListener();
        }
    }

    public void onSendStoryButtonPressed() {
        if (mListener != null) {
            mListener.onSendStoryButtonListener();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StoryBarCallback) activity;
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

    public interface StoryBarCallback {
        public void onContinueButtonListener();
        public void onSaveStoryButtonListener();
        public void onSendStoryButtonListener();
    }

}
