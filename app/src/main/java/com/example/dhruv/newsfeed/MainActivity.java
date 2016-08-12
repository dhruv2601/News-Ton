package com.example.dhruv.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;


public class MainActivity extends FragmentActivity {

    Uri uri;
    public static int position = 0;
    public static String link;
    public static final String TAG = "MainAct";

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.black));
        }
        setContentView(R.layout.main);

        if (savedInstanceState == null) {
            addRssFragment();
        }
        Intent i = getIntent();
        position = i.getExtras().getInt("topic");
        Log.d(TAG, "MainPos= " + position);
    }

    public String passType() {
        return "top stories";
    }


    private void addRssFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        if (b == false) {
            // add a layout with img and reload btn
            Intent i = new Intent(this, NoInternet.class);
            startActivity(i);
        } else {

            RssFragment fragment = new RssFragment();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

    public static void fullArt(View v) {

        Log.d(TAG, "onfullart"+link);
//        uri = RssFragment.readFull();
//        Intent i = new Intent(MainActivity.this, FullArticle.class);
//        i.putExtra("url", s);
//        startActivity(i);
    }



    public void shareItBaby(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        Log.d(TAG,"beforeuricall");
        shareIntent.setType("text/html");
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This Article was sent by a Bitching App \n");   // instead send the description here

//        uri = RssFragment.readFull();

        Log.d(TAG,"afteruricall");
        Log.d(TAG, "uri+"+uri);
        String articleLink = uri.toString();          // yahan 3 ki jgah RssService ki list view mn jo bhi position p ye send vala btn hoga vo aayega
        Log.d(TAG,"tostring "+articleLink);
        //shareIntent.putExtra(Intent.EXTRA_TEXT, "This Article was sent by a Bitching App \n \n");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This Article was sent by a Bitching App \n \n" + uri);
        startActivity(Intent.createChooser(shareIntent, "Share Article"));   // share ke badd app p nhi ja rha hai!!
    }
}