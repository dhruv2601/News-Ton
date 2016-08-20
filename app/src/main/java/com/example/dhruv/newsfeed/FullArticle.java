package com.example.dhruv.newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.wang.avi.AVLoadingIndicatorView;

public class FullArticle extends AppCompatActivity {

    private static final String TAG = "FullArt";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    AVLoadingIndicatorView avi;
    private long time;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);       // Make it full screen
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_article);

        WebView webView = (WebView) findViewById(R.id.webView);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
//        ProgressBar pBar = (ProgressBar) findViewById(R.id.pbar);



        String webLink = (String) getIntent().getExtras().get("url");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"link==="+url);
                view.loadUrl(url);
                return true;
            }
        });

        Log.d(TAG, "URL: " + String.valueOf(webLink));
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setWebViewClient(new WebViewClient());
        startAnim();
        webView.loadUrl(webLink.toString());

        // dont know how to stop the animation
        //toStop stop = new toStop();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    void startAnim() {
        avi.show();
    }

    void stopAnim() {
        avi.hide();
        // or avi.smoothToHide();
    }


    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
//        Intent i = new Intent(FullArticle.this, MainActivity.class);
//        startActivity(i);
//        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FullArticle Page", // TODO: Define a title for the content shown.
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
                "FullArticle Page", // TODO: Define a title for the content shown.
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
}

class toStop extends AsyncTask<String,Void,Void>
{

    private static final String TAG = "Async";

    @Override
    protected Void doInBackground(String... params) {
        long time = SystemClock.uptimeMillis();
        Log.d(TAG,"inasync");
        while(SystemClock.uptimeMillis()-time>=200)
        {

        }
        FullArticle fA = new FullArticle();
        fA.stopAnim();

        return null;
    }
}
