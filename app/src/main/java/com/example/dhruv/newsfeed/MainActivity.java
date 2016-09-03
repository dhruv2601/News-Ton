package com.example.dhruv.newsfeed;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAct";
    public static int position;
    public static TextToSpeech t1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    if (MainActivity.position < 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            t1.setLanguage(Locale.forLanguageTag("values-hi-rIN"));
                        } else {
                            t1.setLanguage(Locale.ENGLISH);
                        }
                    }
                }
            }
        });
        t1.setSpeechRate(0.8f);


        Log.d(TAG, "beforeTabLayout");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("INTRO"));
        Log.d(TAG, "afterTabLayout");
        tabLayout.addTab(tabLayout.newTab().setText("Top Stories"));
        Log.d(TAG, "tab1Added");
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        Log.d(TAG, "tab2Added");
        tabLayout.addTab(tabLayout.newTab().setText("Tech"));
        Log.d(TAG, "tab3Added");
        tabLayout.addTab(tabLayout.newTab().setText("World"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        Log.d(TAG, "viewPagerCAll");
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        Log.d(TAG, "setAdapter");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("INTRO");

        tabLayout.getTabAt(1).setText("TopStories");
        tabLayout.getTabAt(1).setIcon(R.drawable.topstories);
        tabLayout.getTabAt(2).setIcon(R.drawable.sportshindi);
        tabLayout.getTabAt(3).setIcon(R.drawable.tech);
        tabLayout.getTabAt(4).setIcon(R.drawable.world);


        tabLayout.setBackgroundColor(getResources().getColor(R.color.teal));
//        tabLayout.setBackgroundColor(R.style.MyCustomTabLayout);

        viewPager.setOffscreenPageLimit(0);           //look into it once all tabs set
        Log.d(TAG, "adapterAllSet");
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() > 0) {
                    MainActivity.position = tab.getPosition() - 1;
                } else {
                    MainActivity.position = tab.getPosition();
                }

                viewPager.setCurrentItem(tab.getPosition());
//                viewPager.arrowScroll(View.FOCUS_RIGHT);
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() > 0) {
                    MainActivity.position = tab.getPosition() - 1;
                } else {
                    MainActivity.position = tab.getPosition();
                }
            }
        });
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}