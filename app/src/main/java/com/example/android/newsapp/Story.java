package com.example.android.newsapp;

/**
 * Created by Rodrigo on 04/07/2017.
 */

public class Story {

    private String mTitle;
    private String mCategory;
    private String mDate;
    private String mStoryUrl;


    public Story(String title, String category, String date, String url){

        mTitle = title;
        mCategory = category;
        mDate = date;
        mStoryUrl = url;


    }

    public String getTitle(){return mTitle;}

    public String getCategory(){return mCategory;}

    public String getDate(){return mDate;}

    public String getUrl(){return mStoryUrl;}


}
