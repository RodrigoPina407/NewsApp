package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rodrigo on 04/07/2017.
 */

public class ListAdapter extends ArrayAdapter<Story> {

    public ListAdapter(Context context, List<Story> story) {
        super(context, 0, story);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Story currentStory = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.list_title);
        TextView category = (TextView) listItemView.findViewById(R.id.list_category);
        TextView date = (TextView) listItemView.findViewById(R.id.list_date);

        title.setText(currentStory.getTitle());
        category.setText(currentStory.getCategory());
        date.setText(currentStory.getDate());



        return listItemView;
    }
}
