package com.appchee.learnews;

import android.app.Activity;
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

public class CategoryDrawerFragment extends Fragment {
    private String[] mCategories = {"All", "World", "UK", "Technology & Science", "Arts & Entertainment"};
    private final View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String category = (String) v.getTag();
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

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.categories_list);
        for (String category : mCategories) {
            View categoryView= inflater.inflate(R.layout.category_button, layout, false);
            Button categoryButton = (Button) categoryView.findViewById(R.id.category_button);
            categoryButton.setText(category);
            categoryButton.setTag(category);
            categoryButton.setOnClickListener(mListener);
            layout.addView(categoryView);
        }

        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface DrawerCallback {
        public void onOpenDrawerListener(boolean isCorrect);
    }


}