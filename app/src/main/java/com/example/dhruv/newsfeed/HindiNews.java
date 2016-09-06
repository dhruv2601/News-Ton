package com.example.dhruv.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class HindiNews extends AppCompatActivity {

    private static final String TAG = "HindiNews";
    public ImageView nation;
    public ImageView sportsHindi;
    public ImageView automobile;
    public ImageView bollywoodHindi;
    public ImageView astrology;
    public ImageView tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi_news);

        nation = (ImageView) findViewById(R.id.nation);
        sportsHindi = (ImageView) findViewById(R.id.sportsHindi);
        automobile = (ImageView) findViewById(R.id.auto);
        bollywoodHindi = (ImageView) findViewById(R.id.bollywoodHindi);
        astrology = (ImageView) findViewById(R.id.astrology);
        tech = (ImageView) findViewById(R.id.tech);

        Intent intent1 = getIntent();
        if (intent1 != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
            if (b == false) {
                // add a layout with img and reload btn
                Intent intent = new Intent(HindiNews.this, NoInternet.class);
                startActivity(intent);
            }
        }

        nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(HindiNews.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(HindiNews.this, MainActivity.class);
                    Log.d(TAG, "position= " + -1);
                    i.putExtra("topic", -1);
                    startActivity(i);
                }
            }
        });

        sportsHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(HindiNews.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(HindiNews.this, MainActivity.class);
                    Log.d(TAG, "position= " + -2);
                    i.putExtra("topic", -2);
                    startActivity(i);
                }
            }
        });

        automobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(HindiNews.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(HindiNews.this, MainActivity.class);
                    Log.d(TAG, "position= " + -3);
                    i.putExtra("topic", -3);
                    startActivity(i);
                }
            }
        });


        bollywoodHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(HindiNews.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(HindiNews.this, MainActivity.class);
                    Log.d(TAG, "position= " + -4);
                    i.putExtra("topic", -4);
                    startActivity(i);
                }
            }
        });

        astrology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(HindiNews.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(HindiNews.this, MainActivity.class);
                    Log.d(TAG, "position= " + -5);
                    i.putExtra("topic", -5);
                    startActivity(i);
                }
            }
        });

        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(HindiNews.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(HindiNews.this, MainActivity.class);
                    Log.d(TAG, "position= " + -6);
                    i.putExtra("topic", -6);
                    startActivity(i);
                }
            }
        });
    }

}
