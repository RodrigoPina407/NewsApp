package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Rodrigo on 04/07/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<Story>> {

    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        List<Story> stories = NewsQuery.getNews(mUrl);

        return stories;
    }
}



