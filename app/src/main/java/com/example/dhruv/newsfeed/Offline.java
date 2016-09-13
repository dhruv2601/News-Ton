package com.example.dhruv.newsfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhruv on 7/9/16.
 */
public class Offline extends Fragment {

    private static final String TAG = "MainAct";
    AVLoadingIndicatorView avi;
    public static ListView listViewTopStories;
    public static ListView listViewSports;
    public static ListView listViewTech;
    public static ListView listViewWold;
    public static ListView savedArticle;
    private View view;
    public static Uri uri;
    public static int pos;
    public int size[] = new int[15];
    public String passTopic[] = new String[]
            {
                    "topstories",
                    "sports",
                    "tech",
                    "world",
                    "hindi"  // at no. 9 though
            };

    public String passTopicKey[] = new String[]
            {
                    "getTitle",
                    "getLink",
                    "getDate",
                    "getCategory",
                    "getThumbnail"  // at no. 9 though
            };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getContext().getSharedPreferences("sharedPrefSize", 0);
        for (int i = 0; i < 15; i++) {
            size[i] = sPref.getInt("size" + i, 0);
            Log.d(TAG, "size " + size[i]);
        }

        setRetainInstance(true);
        pos = MainActivity.position;
//        startService();       //_________________________________________
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
            listViewTopStories = (ListView) view.findViewById(R.id.listViewTopStories);
//            listViewTopStories.setOnItemClickListener(this);

            savedArticle = (ListView) view.findViewById(R.id.savedArticle);

            listViewSports = (ListView) view.findViewById(R.id.listViewSports);
//            listViewSports.setOnItemClickListener(this);

            listViewTech = (ListView) view.findViewById(R.id.listViewTech);
//            listViewTech.setOnItemClickListener(this);

            listViewWold = (ListView) view.findViewById(R.id.listViewWorld);
//            listViewWold.setOnItemClickListener(this);
//            avi.show();

            List<RssItem> items = new ArrayList<RssItem>();
            int l = 0;
            SharedPreferences pref = getContext().getSharedPreferences("items", 0);

            int i = MainActivity.position;   // try using i-1 in rssItem setter below
//            for(int i=0;i<5;i++)
//            {
            for (int f = 0; f <= 10; f++) {
                Log.d(TAG, "sharedPrefSize " + MainActivity.sharedPrefSize[f]);
            }

            for (int k = 0; k < MainActivity.sharedPrefSize[pos]; k++) {
                int j = 0;
                RssItem rssItem = new RssItem(pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j] + k, null));
                items.add(l++, rssItem);
            }
//            }

            RssAdapter adapter = new RssAdapter(getActivity(), items);
//            avi.hide();

            if (pos == 1) {
                listViewTopStories.setAdapter(adapter);
                listViewTopStories.setVisibility(View.VISIBLE);
                listViewTech.setVisibility(View.GONE);
                listViewWold.setVisibility(View.GONE);
                listViewSports.setVisibility(View.GONE);
                savedArticle.setVisibility(View.GONE);
            }

            if (pos == 2) {
                listViewTech.setAdapter(adapter);
                listViewTech.setVisibility(View.VISIBLE);
                listViewTopStories.setVisibility(View.GONE);
                listViewWold.setVisibility(View.GONE);
                listViewSports.setVisibility(View.GONE);
                savedArticle.setVisibility(View.GONE);
            }
            if (pos == 3) {
                listViewSports.setAdapter(adapter);
                listViewSports.setVisibility(View.VISIBLE);
                listViewTopStories.setVisibility(View.GONE);
                listViewWold.setVisibility(View.GONE);
                listViewTech.setVisibility(View.GONE);
                savedArticle.setVisibility(View.GONE);
            }
            if (pos == 4) {
                listViewWold.setAdapter(adapter);
                listViewWold.setVisibility(View.VISIBLE);
                listViewTech.setVisibility(View.GONE);
                listViewTopStories.setVisibility(View.GONE);
                listViewTech.setVisibility(View.GONE);
                savedArticle.setVisibility(View.GONE);
            }
        } else {
            // If we are returning from a configuration change:
            // "view" is still attached to the previous view hierarchy
            // so we need to remove it and re-attach it to the current one
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }
}