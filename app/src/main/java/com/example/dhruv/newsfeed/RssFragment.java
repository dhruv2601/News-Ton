package com.example.dhruv.newsfeed;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//import com.ldoublem.loadingviewlib.LVGhost;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static com.example.dhruv.newsfeed.RssAdapter.context;
import static com.example.dhruv.newsfeed.RssAdapter.wv;


/**
 * Created by dhruv on 7/8/16.
 */

public class RssFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainAct";
    public static ListView listViewTopStories;
    public static ListView listViewSports;
    public static ListView listViewTech;
    public static ListView listViewWold;
    public static ListView listViewBusiness;
    public static ListView listViewAutombile;
    public static ListView listViewPolitics;
    public static ListView savedArticle;
    public static ListView listViewEnter;
    public Dialog loading;
    public AVLoadingIndicatorView avi;
    public AVLoadingIndicatorView avi2;
    //    public LVGhost ghost;
    private View view;
    private View vPre;
    private View vCurr;
    public static Uri uri;
    public static int pos;
    private Animation animation;


    public String passTopic[] = new String[]
            {
                    "noUseJustToIncreaseIndexBy1",
                    "topstories",
                    "sports",
                    "entertainment",
                    "hindi",
                    "tech",
                    "business",
                    "automobile",
                    "politics"
            };

    public String passTopicKey[] = new String[]
            {
                    "getTitle",
                    "getLink",
                    "getDate",
                    "getCategory",
                    "getThumbnail"  // at no. 9 though
            };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        pos = MainActivity.position;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.hyper_space);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            listViewTopStories = (ListView) view.findViewById(R.id.listViewTopStories);
            listViewTopStories.setOnItemClickListener(this);

            listViewSports = (ListView) view.findViewById(R.id.listViewSports);
            listViewSports.setOnItemClickListener(this);

            listViewTech = (ListView) view.findViewById(R.id.listViewTech);
            listViewTech.setOnItemClickListener(this);

            listViewWold = (ListView) view.findViewById(R.id.listViewWorld);
            listViewWold.setOnItemClickListener(this);

            listViewEnter = (ListView) view.findViewById(R.id.listViewEnter);
            listViewEnter.setOnItemClickListener(this);

            listViewBusiness = (ListView) view.findViewById(R.id.listViewBusiness);
            listViewBusiness.setOnItemClickListener(this);

            listViewAutombile = (ListView) view.findViewById(R.id.listViewAutomobile);
            listViewAutombile.setOnItemClickListener(this);

            listViewPolitics = (ListView) view.findViewById(R.id.listViewPolitics);
            listViewPolitics.setOnItemClickListener(this);

            savedArticle = (ListView) view.findViewById(R.id.savedArticle);

            loading = new Dialog(getContext(), R.style.MyInvisibleDialog);
            loading.setCancelable(false);
            loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
//            ghost = (LVGhost) view.findViewById(R.id.ghost);

//            else
            {
                final List<RssItem> items = new ArrayList<RssItem>();
                int l = 0;
                SharedPreferences pref = getContext().getSharedPreferences("items", 0);

                int i = MainActivity.position;   // try using i-1 in rssItem setter below
//            for(int i=0;i<5;i++)
//            {
                for (int f = 0; f <= 10; f++) {
                    Log.d(TAG, "sharedPrefSize " + MainActivity.sharedPrefSize[f]);
                }

                for (int k = 0; k < MainActivity.sharedPrefSize[pos]; k++) {
                    int j = 0;
                    RssItem rssItem = new RssItem(pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j++] + k, null), pref.getString(passTopic[i - 1] + passTopicKey[j] + k, null));
                    items.add(l++, rssItem);
                }
//            }

                final FloatingActionButton pause = (FloatingActionButton) view.findViewById(R.id.stop);
                pause.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor("#43a047"))
                );

                final FloatingActionButton listenToAll = (FloatingActionButton) view.findViewById(R.id.listenToAll);
                listenToAll.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor("#c62828"))
                );

                listenToAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getContext(), "Touch Again To Stop Playing News", Toast.LENGTH_SHORT).show();

                        pause.setVisibility(View.VISIBLE);
                        listenToAll.setVisibility(View.GONE);

                        for (int i = 0; i < items.size(); i++) {
                            Log.d(TAG, "listening");
                            MainActivity.t1.speak(items.get(i).getTitle().toString() + ". . . . .  .", TextToSpeech.QUEUE_ADD, null);
                            Log.d(TAG, "i   " + i);

//                            listenToAll.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    MainActivity.t1.stop();
//                                    pause.setVisibility(View.GONE);
//                                    listenToAll.setVisibility(View.VISIBLE);
//                                    Toast.makeText(getContext(), "News Stopped Playing", Toast.LENGTH_SHORT).show();
//                                    listenToAll.clearFocus();
//                                }
//                            });
                            //MainActivity.t1.speak(finalNews, TextToSpeech.QUEUE_FLUSH, null);
                            Log.d(TAG, "afterListen");
                        }
                        listenToAll.clearFocus();
                    }
                });

                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pause.setVisibility(View.GONE);
                        listenToAll.setVisibility(View.VISIBLE);
                        MainActivity.t1.stop();
                        pause.setVisibility(View.GONE);
                        listenToAll.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "News Stopped Playing", Toast.LENGTH_SHORT).show();
                    }
                });

                RssAdapter adapter = new RssAdapter(getActivity(), items);
//                avi.hide();

                if (pos == 1) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewTopStories.setAdapter(adapter);
                    listViewTopStories.setVisibility(View.VISIBLE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewSports.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 5) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewTech.setAdapter(adapter);
                    listViewTech.setVisibility(View.VISIBLE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewSports.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }
                if (pos == 2) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewSports.setAdapter(adapter);
                    listViewSports.setVisibility(View.VISIBLE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }
                if (pos == 4) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewWold.setAdapter(adapter);
                    listViewWold.setVisibility(View.VISIBLE);
                    listViewTech.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }
                if (pos == 5) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    view.clearAnimation();
                    listViewEnter.setAdapter(adapter);
                    listViewEnter.setVisibility(View.VISIBLE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 6) {
                    Log.d(TAG, "ImmediateBusiness");
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewBusiness.setAdapter(adapter);
                    listViewBusiness.setVisibility(View.VISIBLE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 7) {
                    Log.d(TAG, "ImmediateAuto");
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewAutombile.setAdapter(adapter);
                    listViewAutombile.setVisibility(View.VISIBLE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 8) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewPolitics.setAdapter(adapter);
                    listViewPolitics.setVisibility(View.VISIBLE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }
            }
            if (MainActivity.flag[pos] == 0) {
                startService();
            }
        }
        return view;
    }

    public View getViewByPos(int pos, ListView lv) {
        final int first = lv.getFirstVisiblePosition();
        final int last = first + lv.getChildCount();

        if (pos < first || pos > last) {
            return lv.getAdapter().getView(pos, null, lv);
        } else {
            final int childInd = pos - first;
            return lv.getChildAt(childInd);
        }
    }

    private void startService() {
        loading.show();
        avi.show();
//        avi2.show();
//        ghost.startAnim();
//        listViewTopStories.requestDisallowInterceptTouchEvent(true);
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        Log.d(TAG, "positionAtRssFrag " + MainActivity.position);
        intent.putExtra("topic", MainActivity.position);
        getActivity().startService(intent);
    }


    /**
     * Once the {@link RssService} finishes its task, the result is sent to this ResultReceiver.
     */

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {

        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            loading.hide();
            avi.hide();
//            avi2.hide();

            MainActivity.flag[pos] = 1;
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);

            List<RssItem> temp = new ArrayList<RssItem>();
            for (int i = 1; i < RssService.count; i++) {
                temp.add(i - 1, items.get(i));
            }

            Log.d(TAG, "onResult " + pos);
            if (items != null) {
                SharedPreferences prefs;
                prefs = getContext().getSharedPreferences("items", 0);
                SharedPreferences.Editor editor = prefs.edit();

                Log.d(TAG, "itemsSizeBefore " + RssService.count);

                for (int i = 0; i < RssService.count; i++) {
                    int j = 0;
                    editor.putString(passTopic[pos - 1] + passTopicKey[j++] + i, items.get(i).getTitle());
                    editor.putString(passTopic[pos - 1] + passTopicKey[j++] + i, items.get(i).getLink());
                    editor.putString(passTopic[pos - 1] + passTopicKey[j++] + i, items.get(i).getDate());
                    editor.putString(passTopic[pos - 1] + passTopicKey[j++] + i, items.get(i).getCategory());
                    editor.putString(passTopic[pos - 1] + passTopicKey[j] + i, items.get(i).getThumbnail());
                }
                editor.putInt("itemSize", RssService.count);
                editor.apply();       //try commit here

                SharedPreferences sref;
                sref = getContext().getSharedPreferences("sharedPrefSize", 0);
                SharedPreferences.Editor editor1 = sref.edit();

                MainActivity.sharedPrefSize[pos] = RssService.count;
                for (int i = 0; i < 15; i++) {
                    editor1.putInt("size" + i, MainActivity.sharedPrefSize[i]);
                }
                editor1.apply();

                Log.d(TAG, "itemsSize " + items.size());
                Log.d(TAG, "passTopicPos " + passTopic[pos]);

                for (int f = 0; f <= 10; f++) {
                    Log.d(TAG, "sharedPrefSizeAFTERERER " + MainActivity.sharedPrefSize[f]);
                }

                RssAdapter adapter = new RssAdapter(getActivity(), temp);
                SharedPreferences pref2 = getContext().getSharedPreferences("items", 0);

                for (int i = 0; i < 3; i++) {
                    for (int k = 0; k < pref2.getInt("itemSize", 0); k++) {
                        int j = 0;
                        Log.d(TAG, "sharedpref2 " + i + pref2.getString(passTopic[i] + passTopicKey[j++] + k, null));
                        Log.d(TAG, "sharedpref2 " + i + pref2.getString(passTopic[i] + passTopicKey[j++] + k, null));
                        Log.d(TAG, "sharedpref2 " + i + pref2.getString(passTopic[i] + passTopicKey[j++] + k, null));
                        Log.d(TAG, "sharedpref2 " + i + pref2.getString(passTopic[i] + passTopicKey[j] + k, null));
                    }
                }

                if (pos == 1) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewTopStories.setAdapter(adapter);
                    listViewTopStories.setVisibility(View.VISIBLE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewSports.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }
                if (pos == 5) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    view.clearAnimation();
                    listViewEnter.setAdapter(adapter);
                    listViewEnter.setVisibility(View.VISIBLE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 2) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewSports.setAdapter(adapter);
                    listViewSports.setVisibility(View.VISIBLE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }
                if (pos == 4) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewWold.setAdapter(adapter);
                    listViewWold.setVisibility(View.VISIBLE);
                    listViewTech.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 3) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewTech.setAdapter(adapter);
                    listViewTech.setVisibility(View.VISIBLE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewSports.setVisibility(View.GONE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 6) {
                    Log.d(TAG, "ImmediateBusiness");
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewBusiness.setAdapter(adapter);
                    listViewBusiness.setVisibility(View.VISIBLE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewAutombile.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 7) {
                    Log.d(TAG, "ImmediateAuto");
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewAutombile.setAdapter(adapter);
                    listViewAutombile.setVisibility(View.VISIBLE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    listViewPolitics.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }

                if (pos == 8) {
                    animation.setDuration(500);
                    view.startAnimation(animation);
                    listViewPolitics.setAdapter(adapter);
                    listViewPolitics.setVisibility(View.VISIBLE);
                    listViewEnter.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewWold.setVisibility(View.GONE);
                    listViewTopStories.setVisibility(View.GONE);
                    listViewTech.setVisibility(View.GONE);
                    listViewBusiness.setVisibility(View.GONE);
                    savedArticle.setVisibility(View.GONE);
                }


            } else {
                Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
            avi.hide();
//            ghost.stopAnim();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Log.d(TAG, "onItemClikWhy");
//        RssAdapter adapter = (RssAdapter) parent.getAdapter();
//        RssItem item = (RssItem) adapter.getItem(i);
//        uri = Uri.parse(item.getLink());
//        Intent intent = new Intent(RssFragment.super.getContext(), FullArticle.class);
    }
}