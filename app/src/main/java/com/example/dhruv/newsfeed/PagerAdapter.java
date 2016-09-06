package com.example.dhruv.newsfeed;

/**
 * Created by dhruv on 30/8/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PagerAdapter extends FragmentStatePagerAdapter implements java.io.Serializable {
    private static final String TAG = "pagerAdapter";
    public static int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position) {

        Log.d(TAG, "mNumTabs " + getCount());
        switch (position) {
            case 0:
                MainActivity.position =0;
                return new EntryPage();
            case 1:
                Log.d(TAG, "beforeTab1");
                MainActivity.position = 1;
                RssFragment frag = new RssFragment();
                return frag;
            case 2:
                Log.d(TAG, "beforeTab2");
                //sports_tab tab2 = new sports_tab();
                MainActivity.position = 2;
                RssFragment frag1 = new RssFragment();
                Log.d(TAG, "afterTab2");
                return frag1;
            case 3:
                MainActivity.position=3;
                RssFragment frag2 = new RssFragment();
                return frag2;
            case 4:
                MainActivity.position = 4;
                RssFragment frag3 = new RssFragment();
                return frag3;
            case 5:
               WeatherActivity weather = new WeatherActivity();
                return weather;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}