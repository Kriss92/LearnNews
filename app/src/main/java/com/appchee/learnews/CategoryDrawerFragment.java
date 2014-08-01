package com.appchee.learnews;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoryDrawerFragment extends Fragment {
    private LinearLayout mLinearLayout;
    private String[] mCategories = {"World", "UK", "Technology & Science", "Arts & Entertainment"};
    private View[] mButtons = new View[mCategories.length];
    private CategoryCallback mCallback;
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.select_all_categories_button == v.getId()) {
                for (int i = 0; i < mCategories.length; i++) {
                    View view = mButtons[i];
                    Button button = (Button) view.findViewById(R.id.category_button);
                    ButtonTag thisTag = (ButtonTag) button.getTag();
                    thisTag.setSelected(true);
                }
                mCallback.onCategoriesSelected(mCategories);
            }
            else if (R.id.unselect_all_categories_button == v.getId()) {
                for (int i = 0; i < mCategories.length; i++) {
                    View view = mButtons[i];
                    Button button = (Button) view.findViewById(R.id.category_button);
                    ButtonTag thisTag = (ButtonTag) button.getTag();
                    thisTag.setSelected(false);
                }
                mCallback.onCategoriesSelected(null);
            }
            else {
                ButtonTag tag = (ButtonTag) v.getTag();
                List<String> selected = new ArrayList<String>();
                tag.setSelected(!tag.isSelected());
                for (int i = 0; i < mCategories.length; i++) {
                    View view = mButtons[i];
                    Button button = (Button) view.findViewById(R.id.category_button);
                    if (button == null) {
                        Log.d("MyLog", "Null button");
                    }
                    ButtonTag thisTag = (ButtonTag) button.getTag();
                    if (thisTag.isSelected()) {
                        selected.add(mCategories[i]);
                    }
                }
                mCallback.onCategoriesSelected(selected.toArray(new String[0]));
            }

        }
    };


    public static CategoryDrawerFragment newInstance(String param1, String param2) {
        CategoryDrawerFragment fragment = new CategoryDrawerFragment();
        return fragment;
    }

    public CategoryDrawerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_category_drawer, container, false);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.categories_list);
        for (int i = 0; i < mCategories.length; i++) {
            View categoryView= inflater.inflate(R.layout.category_button, mLinearLayout, false);
            Button categoryButton = (Button) categoryView.findViewById(R.id.category_button);
            categoryButton.setText(mCategories[i]);
            ButtonTag tag = new ButtonTag(i, categoryView);
            categoryButton.setTag(tag);
            categoryButton.setOnClickListener(mListener);
            mLinearLayout.addView(categoryView);
            mButtons[i] = categoryButton;
        }

        mLinearLayout.findViewById(R.id.select_all_categories_button).setOnClickListener(mListener);
        mLinearLayout.findViewById(R.id.unselect_all_categories_button).setOnClickListener(mListener);



        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CategoryCallback) {
            mCallback = (CategoryCallback) activity;
        }
        else {
            throw new IllegalStateException("Activity should implement CategoryCallback interface.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface CategoryCallback {
        public void onCategoriesSelected(String[] categories);
    }

    private class ButtonTag {
        private final int categoryId;
        private boolean isSelected;
        private final View mView;

        public ButtonTag(int id, View view) {
            categoryId = id;
            mView = view;
            setSelected(false);
            // The button is unselected by default
        }

        public int getCategoryId() {
            return categoryId;
        }
        public boolean isSelected() {
            return isSelected;
        }
        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
            mView.setBackgroundColor(isSelected ? Color.GREEN : Color.RED);
        }
    }

}