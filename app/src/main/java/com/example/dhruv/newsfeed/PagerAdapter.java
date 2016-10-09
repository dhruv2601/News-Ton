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

public class PagerAdapter extends FragmentStatePagerAdapter implements java.io.Serializable {
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


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position) {

        Log.d(TAG, " changedPos222 " + position);
        Log.d(TAG, " changedPosMAIN " + MainActivity.position);
        Log.d(TAG, "mNumTabs " + getCount());

        switch (position) {
            case 0:
                MainActivity.position = 0;
                SavedArticleClass savedArticleClass = new SavedArticleClass();
                return savedArticleClass;

            case 1:
                Log.d(TAG, "beforeTab1");
                MainActivity.position = 1;

                if (MainActivity.b == false) {
                    MainActivity.position = 0;
                    Log.d(TAG, "appIsOffline");
                    return new Offline();
                } else {
                    RssFragment frag = new RssFragment();
                    return frag;
                }

            case 2:
                MainActivity.position = 0;
                return new TopStoryReference();

            case 3:
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

            case 4:
                MainActivity.position = 0;
                return new SportsReference();

            case 5:
                MainActivity.position = 5;           // tech
                if (MainActivity.b == false) {
                    MainActivity.position = 2;
                    return new Offline();
                } else {
                    RssFragment frag2 = new RssFragment();
                    return frag2;
                }

            case 6:
                MainActivity.position = 0;       // add enterN ref
                return new EntryPage();

            case 7:
                MainActivity.position = 4;              //hindi
                if (MainActivity.b == false) {
                    MainActivity.position = 3;
                    return new Offline();
                } else {
                    RssFragment frag3 = new RssFragment();
                    return frag3;
                }

            case 8:
                return new HindiReference();

            case 9:
                MainActivity.position = 3;           // entertainment
                if (MainActivity.b == false) {
                    MainActivity.position = 2;
                    return new Offline();
                } else {
                    RssFragment frag2 = new RssFragment();
                    return frag2;
                }

            case 10:
                return new EntryPage();             // tech reference

            case 11:                                // business
                MainActivity.position = 6;
                if (MainActivity.b == false) {
                    MainActivity.position = 5;
                    return new Offline();
                } else {
                    RssFragment frag4 = new RssFragment();
                    return frag4;
                }
            case 12:
                return new EntryPage();             // business reference

            case 13:                                // auto
                MainActivity.position = 7;
                if (MainActivity.b == false) {
                    MainActivity.position = 5;
                    return new Offline();
                } else {
                    RssFragment frag4 = new RssFragment();
                    return frag4;
                }

            case 14:
                return new EntryPage();             // auto reference

            case 15:                                // politics
                MainActivity.position = 8;
                if (MainActivity.b == false) {
                    MainActivity.position = 5;
                    return new Offline();
                } else {
                    RssFragment frag4 = new RssFragment();
                    return frag4;
                }
            case 16:
                return new EntryPage();             // politics reference

            case 17:
                WeatherActivity weather = new WeatherActivity();
                return weather;
            case 18:
                MainActivity.position = 0;
                return new Search_class();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}