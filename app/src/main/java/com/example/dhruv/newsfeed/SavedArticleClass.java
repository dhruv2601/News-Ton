package com.example.dhruv.newsfeed;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhruv on 12/9/16.
 */
public class SavedArticleClass extends Fragment {

    private static final String TAG = "SavedArticle";
    public int pos;
    public static ListView savedArticle;
    private View view;
    public static ListView listViewTopStories;
    public static ListView listViewSports;
    public static ListView listViewTech;
    public static TextView noSavedArt;
    public static ListView listViewEnter;
    public static ListView listViewBusiness;
    public static ListView listViewAutombile;
    public static ListView listViewPolitics;
    public static ListView listViewWold;
    private Button delThis;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "SavedArticleCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout_saved, container, false);

            noSavedArt = (TextView) view.findViewById(R.id.noSavedArt);
            savedArticle = (ListView) view.findViewById(R.id.savedArticle);

            listViewTopStories = (ListView) view.findViewById(R.id.listViewTopStories);

            listViewSports = (ListView) view.findViewById(R.id.listViewSports);

            listViewTech = (ListView) view.findViewById(R.id.listViewTech);

            listViewWold = (ListView) view.findViewById(R.id.listViewWorld);

            listViewEnter = (ListView) view.findViewById(R.id.listViewEnter);

            final SharedPreferences savedArtPref = getContext().getSharedPreferences("savedArticle", 0);
            List<RssItem> rssItem = new ArrayList<RssItem>();

            int l = 0;

            if(MainActivity.savedArticleSize==0)
            {
                noSavedArt.setVisibility(View.VISIBLE);
            }

            for (int i = MainActivity.savedArticleSize - 1; i >= 0; i--)    // check if initial should be -1
            {
                RssItem item = new RssItem(savedArtPref.getString("title" + i, null), savedArtPref.getString("link" + i, null), savedArtPref.getString("date" + i, null), savedArtPref.getString("category" + i, null), savedArtPref.getString("thumbnail" + i, null));
                Log.d(TAG, "savedArt " + savedArtPref.getString("title" + i, null));
                rssItem.add(l++, item);
            }

            final RssAdapterSaved adapter = new RssAdapterSaved(getActivity(), rssItem);
            savedArticle.setAdapter(adapter);
            savedArticle.setVisibility(View.VISIBLE);
            listViewSports.setVisibility(View.GONE);
            listViewTopStories.setVisibility(View.GONE);
            listViewWold.setVisibility(View.GONE);
            listViewTech.setVisibility(View.GONE);
            listViewEnter.setVisibility(View.GONE);

            FloatingActionButton listen = (FloatingActionButton) view.findViewById(R.id.play);
            listen.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#f44336"))
            );

            FloatingActionButton del  = (FloatingActionButton) view.findViewById(R.id.deleteAll);

            del.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#f44336"))
            );

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    noSavedArt.setVisibility(View.VISIBLE);
                    View dialogLayout = inflater.inflate(R.layout.dialogue_alert, null);

                    Log.d(TAG,"inside onClick");
                    AlertDialog.Builder delAlert = new AlertDialog.Builder(getContext());
                    delAlert.setTitle("Delete All Saved Articles ?");
                    delAlert.setView(dialogLayout);
                    delAlert.setNegativeButton("YES ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = savedArtPref.edit();
                            editor.clear();
                            editor.apply();

                            List<RssItem> empty = new ArrayList<RssItem>();
                            RssAdapter adapter1 = new RssAdapter(getActivity(), empty);
                            savedArticle.setAdapter(adapter1);
                            MainActivity.savedArticleSize = 0;

                            Toast.makeText(getContext(), "Cleared Saved Article", Toast.LENGTH_SHORT).show();

                        }
                    });

                    delAlert.setPositiveButton("                                               NO ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    delAlert.show();
                }
            });


        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "SavedOnCreate");
        setRetainInstance(true);
        pos = MainActivity.position;
//        startService();       //_________________________________________
    }
}
