package com.example.dhruv.newsfeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.Locale;

public class
MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static ViewPager viewPager;
    private static final String TAG = "MainAct";
    public static RelativeLayout relativeLayout;
    public static ListView listViewTopStories;
    public static ListView listViewSports;
    public static ListView listViewTech;
    public static ListView listViewWold;
    public TabLayout tabLayout;
    public int eye = 0;
    public static int savedArticleSize;
    public static int sharedPrefSize[] = new int[15];
    public static SharedPreferences sref;
    public static SharedPreferences.Editor editor;
    public static int flag[] = new int[15];
    public static int position;
    public static boolean b;
    public static TextToSpeech t1;
    public static final String appDirectoryName = "News Feed";
    public static final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), appDirectoryName);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        sref = MainActivity.this.getSharedPreferences("sharedPrefSize", 0);
        for (int i = 0; i < 15; i++) {
            sharedPrefSize[i] = sref.getInt("size" + i, 0);
        }

        for (int i = 0; i < 15; i++) {
            flag[i] = 0;
        }

        SharedPreferences saved = MainActivity.this.getSharedPreferences("savedArticle", 0);
        savedArticleSize = saved.getInt("size", 0);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        if (!b) {
            Toast.makeText(MainActivity.this, "A P P  R U N N I N G  I N  O F F L I N E   M O D E", Toast.LENGTH_LONG).show();
        }

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
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SEARCH")); //0
        Log.d(TAG, "afterTabLayout");
        tabLayout.addTab(tabLayout.newTab()); //1
        tabLayout.addTab(tabLayout.newTab().setText("Top Stories"));    //2
        Log.d(TAG, "tab1Added");
        tabLayout.addTab(tabLayout.newTab().setText("PAPERS"));   //3
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));         //4
        Log.d(TAG, "tab2Added");
        tabLayout.addTab(tabLayout.newTab().setText("PAPERS"));   //5
        tabLayout.addTab(tabLayout.newTab().setText("Tech"));       //6
        Log.d(TAG, "tab3Added");
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.activity_main_two));       //7
        tabLayout.addTab(tabLayout.newTab().setText("HINDI"));      //8

        tabLayout.addTab(tabLayout.newTab().setText("HindiPapers"));//9

        tabLayout.addTab(tabLayout.newTab().setText("Weather")); //weather      //10

        tabLayout.addTab(tabLayout.newTab().setText("Saved Articles")); // 11


        tabLayout.post(tabLayoutConfig);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Log.d(TAG, "viewPagerCAll");
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        MainActivity.viewPager = viewPager;
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        Log.d(TAG, "setAdapter");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("SEARCH");
        tabLayout.getTabAt(1).setText("SAVED ARTICLES");
        tabLayout.getTabAt(2).setText("TOP STORIES");
        tabLayout.getTabAt(3).setText("PAPERS");
        tabLayout.getTabAt(4).setText("SPORTS");
        tabLayout.getTabAt(5).setText("PAPERS");
        tabLayout.getTabAt(6).setText("TECH");
        tabLayout.getTabAt(8).setText("HINDI");
        tabLayout.getTabAt(9).setText("PAPERS");
        tabLayout.getTabAt(10).setText("WEATHER");


//        tabLayout.setBackgroundColor(getResources().getColor(R.color.black));
//        tabLayout.setBackgroundColor(R.style.MyCustomTabLayout);

        viewPager.setOffscreenPageLimit(1);           //look into it once all tabs set
        Log.d(TAG, "adapterAllSet");
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG,"viewPagerCalled");
                return false;
            }
        });

        tabLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG,"tabLayoutKa");
                return false;
            }
        });

//        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
//
//            public void onTabChanged() {
//                Log.d(TAG,"onTabChangedCalled");
////                viewPager.setAdapter(adapter);
////                viewPager.setOffscreenPageLimit(1);
//                viewPager.animate();
//            }
//
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                TabLayout.Tab tabs = tabLayout.getTabAt(tab.getPosition());
//                tabs.select();
////                MainActivity.position = tab.getPosition();
////                Log.d(TAG," changedPos "+MainActivity.position);
////                PagerAdapter pgAd = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
////                viewPager.setAdapter(pgAd);
//
//////                viewPager.arrowScroll(View.FOCUS_RIGHT);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                TabLayout.Tab tabs = tabLayout.getTabAt(tab.getPosition());
//                tabs.select();
////                onTabChanged();
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
////                onTabChanged();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (Search_class.webView.canGoBack()) {
                        Log.d(TAG, "searchClassCanGoBack");
                        Search_class.webView.goBack();
                    } else if (TopStoryReference.wb.canGoBack()) {
                        eye = 1;
                        Log.d(TAG, "topCanGoBack");
                        TopStoryReference.wb.goBack();
                    } else if (SportsReference.wb.canGoBack()) {
                        eye = 2;
                        SportsReference.wb.goBack();
                    } else if (HindiReference.wb.canGoBack()) {
                        eye = 3;
                        HindiReference.wb.goBack();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    Runnable tabLayoutConfig = new Runnable() {
        @Override
        public void run() {

            if (tabLayout.getWidth() < MainActivity.this.getResources().getDisplayMetrics().widthPixels) {
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                ViewGroup.LayoutParams mParams = tabLayout.getLayoutParams();
                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                tabLayout.setLayoutParams(mParams);

            } else {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }
    };

    @Override
    public void onBackPressed() {
        Log.d(TAG, "backPressedCalled");
//        Toast.makeText(MainActivity.this, "Press Again To Exit News Feed", Toast.LENGTH_SHORT).show();
//        t1.shutdown();
//        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            MainActivity.position = 4;
            PagerAdapterTwo adapter = new PagerAdapterTwo
                    (getSupportFragmentManager(), tabLayout.getTabCount());

            Log.d(TAG, "setAdapter");
            if (viewPager != null) {
                Log.d(TAG, "viewPagerKeAndar");
                viewPager.setAdapter(adapter);
            } else {
                Log.d(TAG, "viewPagerNotEntered");
            }
//            viewPager.setOffscreenPageLimit(1);
//            tabLayout.setupWithViewPager(viewPager1);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dhruv.newsfeed/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dhruv.newsfeed/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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

//class introTimeCalc extends AsyncTask
//{
//    @Override
//    protected Object doInBackground(Object[] params) {
//
//        while(SystemClock.uptimeMillis()-MainActivity.currTime<=6000)
//        {
//
//        }
////        MainActivity.layoutDisappear();
//        return null;
//    }
//}