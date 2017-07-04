package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 04/07/2017.
 */

public final class NewsQuery {

    private static final String LOG_TAG = NewsQuery.class.getName();
    private static List<Story> mStories;

    private NewsQuery(){}

    public static List<Story> getNews(String requestUrl) {

        URL url = getUrl(requestUrl);

        String jsonResponse = null;

        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e(LOG_TAG, "HTTP request failed!", e);

        }

        List<Story> stories = extractFeatureFromJson(jsonResponse);

        mStories = stories;

        return mStories;

    }

    public static List<Story> extractFeatureFromJson(String storyJson){

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(storyJson)) {
            return null;
        }

        List<Story> stories = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(storyJson);

            JSONObject response = baseJsonResponse.getJSONObject("response");

            JSONArray results = response.getJSONArray("results");


            for(int i=0; i<results.length(); i++){

                JSONObject tempObject = results.getJSONObject(i);

                String title = tempObject.getString("webTitle");
                String category = tempObject.getString("sectionName");
                String date = tempObject.getString("webPublicationDate");
                String url = tempObject.getString("webUrl");

                stories.add(new Story(title, category, date, url));

            }




        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }


        return stories;


    }

    private static URL getUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // Check if url is null
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* milliseconds */);
            urlConnection.setConnectTimeout(150000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Test if the response code from the request is 200

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {

                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
