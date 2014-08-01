package com.appchee.learnews;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;

import android.os.Bundle;
import android.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appchee.learnews.beans.StoryBean;
import com.appchee.learnews.database.DbInteractions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedStoriesFragment extends Fragment {

    private ListView mList;
    private StoryListAdapter mAdapter;
    private List<StoryBean> mStories = new ArrayList<StoryBean>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.saved_stories_fragment, null);

        StoryListAdapter adapter = new StoryListAdapter();
        mList = (ListView) view.findViewById(R.id.saved_stories_list);
        mList.setAdapter(adapter);
        readStories();
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


    public class StoryListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStories.size();
        }

        @Override
        public Object getItem(int position) {
            return mStories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            StoryViewHolder holder;
            if (null == convertView) {
                // create a new view
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.story_item, null);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = (Uri) v.getTag();
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });

                // create a ViewHolder
                holder = new StoryViewHolder();
                holder.title = (TextView) view.findViewById(R.id.story_headline);
                holder.title.setTextColor(getResources().getColor(R.color.text));
                holder.icon = (ImageView) view.findViewById(R.id.story_source_ic);
                view.setTag(holder);

                holder.title.setMovementMethod(LinkMovementMethod.getInstance());
            }
            else {
                view = convertView;
                holder = (StoryViewHolder) view.getTag();
            }

            StoryBean current = mStories.get(position);
            holder.title.setText(current.getTitle());
            holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.bbc));
            view.setTag(current.getUri());

            return view;
        }
    }

    private class StoryViewHolder {
        public TextView title;
        public ImageView icon;
    }

    private List<StoryBean> readStories() {
        mStories.clear();
        List<StoryBean> fetched = new DbInteractions(getActivity().getApplicationContext()).readSavedStories();
        for (StoryBean story : fetched) {
            mStories.add(story);
        }
        return mStories;
    }


    private List<StoryBean> createFakeStoryBeans() {
        StoryBean[] stories = new StoryBean[3];
        stories[0] = new StoryBean();
        stories[0].setTitle("MLA plane shot down in Ukraine");
        stories[0].setDate(Date.valueOf("2014-03-27"));
        stories[0].setNewsIconId(5);
        stories[0].setUri(Uri.parse("http://www.bbc.com/news/world-europe-28357880"));

        stories[1] = new StoryBean();
        stories[1].setTitle("Results of the EU vote");
        stories[1].setDate(Date.valueOf("2014-02-22"));
        stories[1].setNewsIconId(2);

        stories[2] = new StoryBean();
        stories[2].setTitle("A woman had a baby");
        stories[2].setDate(Date.valueOf("2012-02-02"));
        stories[2].setNewsIconId(3);

        return Arrays.asList(stories);
    }

}