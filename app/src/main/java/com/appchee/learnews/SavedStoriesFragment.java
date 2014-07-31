package com.appchee.learnews;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SavedStoriesFragment extends Fragment {

    private ListView mList;
    private StoryListAdapter mAdapter;
    private StoryBean[] mStories;


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
            // TODO
            return -1;
        }

        @Override
        public Object getItem(int position) {
            // TODO:
            return null;
        }

        @Override
        public long getItemId(int position) {
            //TODO:
            return -1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            StoryViewHolder holder;
            if (null == convertView) {
                // create a new view
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.story_item, null);

                // create a ViewHolder
                holder = new StoryViewHolder();
                holder.title = (TextView) view.findViewById(R.id.story_headline);
                holder.date = (TextView) view.findViewById(R.id.story_date);
                holder.icon = (ImageView) view.findViewById(R.id.story_source_ic);
                view.setTag(holder);
            }
            else {
                view = convertView;
                holder = (StoryViewHolder) view.getTag();
            }

            StoryBean current = mStories[position];
            holder.title.setText(current.getTitle());
            holder.date.setText(current.getDate().toString());
            holder.icon.setImageURI(Uri.parse(current.getSiteIconUrl()));

            // TODO: Image downloading.
            return view;
        }
    };

    private class StoryViewHolder {
        public TextView title;
        public TextView date;
        public ImageView icon;
    };

};