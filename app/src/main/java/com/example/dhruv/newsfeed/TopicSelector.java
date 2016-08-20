package com.example.dhruv.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TopicSelector extends AppCompatActivity {

    Integer[] images = new Integer[]
            {
                    R.drawable.topstories,
                    R.drawable.sportsback,
                    R.drawable.sportsback
            };
    String[] name = new String[]
            {
                    "top stories",
                    "sports",
                    "photos"
            };

    String passTopic[] = new String[]
            {
                    "topstories",
                    "sports",
                    "photos"
            };

    ListView lv;
    public static final String TAG = "Topics";
//    public static Button listeToAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_topic_selector);
        lv = (ListView) findViewById(R.id.lv);
//        listeToAll = (Button) findViewById(R.id.listentoall);
        Intent intent1 = getIntent();
        if (intent1 != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
            if (b == false) {
                // add a layout with img and reload btn
                Intent intent = new Intent(TopicSelector.this, NoInternet.class);
                startActivity(intent);
            }
        }



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                }

                if(position == 2)
                {
                    Intent i = new Intent(TopicSelector.this, Photos.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + position);
                    i.putExtra("topic", position);
                    startActivity(i);
                }
            }
        });


        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < images.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("imageV", Integer.toString(images[i]));
            hm.put("stateName", name[i]);

            aList.add(hm);
        }
        String from[] = {"imageV", "stateName"};
        int[] to = {R.id.listImg, R.id.listTxt};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listviewlayout, from, to);

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }


}
