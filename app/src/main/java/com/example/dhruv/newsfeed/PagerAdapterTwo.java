package com.example.dhruv.newsfeed;

/**
 * Created by dhruv on 30/8/16.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PagerAdapterTwo extends FragmentStatePagerAdapter implements java.io.Serializable {
    private static final String TAG = "pagerAdapter";
    public static int mNumOfTabs;
    public String passTopicKey[] = new String[]
            {
                    "getTitle",
                    "getLink",
                    "getDate",
                    "getCategory",
                    "getThumbnail"  // at no. 9 though
            };


    public PagerAdapterTwo(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position) {

         Log.d(TAG, "mNumTabs " + getCount());
        switch (MainActivity.position) {
            case 0:
                MainActivity.position = 0;
                return new Search_class();

            case 1:
                MainActivity.position = 0;
                SavedArticleClass savedArticleClass = new SavedArticleClass();
                return savedArticleClass;

            case 2:
                Log.d(TAG, "beforeTab1");
                MainActivity.position = 1;

                if (MainActivity.b == false) {
                    MainActivity.position = 1;
                    Log.d(TAG, "appIsOffline");
                    return new Offline();
                } else {
                    RssFragment frag = new RssFragment();
                    return frag;
                }

            case 3:
                MainActivity.position = 0;
                return new TopStoryReference();

            case 4:
                Log.d(TAG, "beforeTab2");
                //sports_tab tab2 = new sports_tab();
                MainActivity.position = 2;
                if (MainActivity.b == false) {
                    MainActivity.position = 1;
                    return new Offline();
                } else {
                    RssFragment frag1 = new RssFragment();
                    Log.d(TAG, "afterTab2");
                    return frag1;
                }

            case 5:
                MainActivity.position = 0;
                return new SportsReference();

            case 6:
                MainActivity.position = 3;
                if (MainActivity.b == false) {
                    MainActivity.position = 2;
                    return new Offline();
                } else {
                    RssFragment frag2 = new RssFragment();
                    return frag2;
                }

            case 7:
                MainActivity.position = 0;
                return new EntryPage();

            case 8:
                MainActivity.position = 4;              //do it after 5th tab properly
                if (MainActivity.b == false) {
                    MainActivity.position = 3;
                    return new Offline();
                } else {
                    RssFragment frag3 = new RssFragment();
                    return frag3;
                }

            case 9:
                return new HindiReference();

            case 10:
                WeatherActivity weather = new WeatherActivity();
                return weather;

            case 11:
                return new EntryPage();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}