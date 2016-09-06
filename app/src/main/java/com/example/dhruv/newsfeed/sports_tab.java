package com.example.dhruv.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by dhruv on 30/8/16.
 */

public class sports_tab extends Fragment implements Serializable {
    Uri uri;
    public static int itemPos;
    //    public static int position = -1;
    public static final String TAG = "MainAct";
    public static TextToSpeech t1;
    public static int width;
    public static String query = "";
    public static String searchedItem;
    public View view;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.tab_topstoris);
//
//        Log.d(TAG,"sportsTab");
//        if (savedInstanceState == null) {
////            addRssFragment();
//        }
//
//        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//                if (i != TextToSpeech.ERROR) {
//                    if (MainActivity.position < 0) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            t1.setLanguage(Locale.forLanguageTag("values-hi-rIN"));
//                        } else {
//                            t1.setLanguage(Locale.ENGLISH);
//                        }
//                    }
//                }
//            }
//        });
//        t1.setSpeechRate(0.8f);
//
//        MainActivity.position = 1;    // for topStories
////        Intent i = getIntent();
////        position = i.getExtras().getInt("topic");
////        Log.d(TAG, "MainPos= " + position);
//    }


    public String passType() {
        return "sports";
    }


    private void addRssFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        if (b == false) {
            // add a layout with img and reload btn
            Intent i = new Intent(getContext(), NoInternet.class);
            startActivity(i);
        } else {
            RssFragment fragment = new RssFragment();
            transaction.add(R.id.sportsContainer, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateViewSports");
        if (view == null) {
            Log.d(TAG, "sportsViewIsNull");
            view = inflater.inflate(R.layout.tab_sports, container, false);
        } else {
            Log.d(TAG, "sportsViewIsNotNull");
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
//        Log.d(TAG, "OnCreateViewSports_Tab ");
//        return inflater.inflate(R.layout.tab_sports, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setContentView(R.layout.tab_sports);

        Log.d(TAG, "sportsTabOnCreate");

        if (getActivity().findViewById(R.id.sportsContainer) != null) {

            if (savedInstanceState == null) {
                Log.d(TAG, "beforeCallingARFSports");
//                addRssFragment();                            // onCreateView is not being called, tried removing this fn call but to no effect and b/c oCV not called View set nhi ho pa rha hai
            } else {
                Log.d(TAG, "ARFNotCAlledSports");
                return;
            }

        }

        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
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

        MainActivity.position = 1;    // for topStories

    }
}