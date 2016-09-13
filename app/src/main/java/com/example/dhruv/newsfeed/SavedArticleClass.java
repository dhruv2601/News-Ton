package com.example.dhruv.newsfeed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhruv on 12/9/16.
 */
public class SavedArticleClass extends Fragment {

    private static final String TAG = "SavedArticle";
    public int pos;
    public ListView savedArticle;
    private View view;
    public static ListView listViewTopStories;
    public static ListView listViewSports;
    public static ListView listViewTech;
    public static ListView listViewWold;
    private Button delAll;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "SavedArtonCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout_saved, container, false);

            savedArticle = (ListView) view.findViewById(R.id.savedArticle);
            listViewTopStories = (ListView) view.findViewById(R.id.listViewTopStories);

            listViewSports = (ListView) view.findViewById(R.id.listViewSports);

            listViewTech = (ListView) view.findViewById(R.id.listViewTech);

            listViewWold = (ListView) view.findViewById(R.id.listViewWorld);

            SharedPreferences savedArtPref = getContext().getSharedPreferences("savedArticle", 0);
            List<RssItem> rssItem = new ArrayList<RssItem>();

            int l = 0;
            for (int i = MainActivity.savedArticleSize-1; i >= 0; i--)    // check if initial should be -1
            {
                RssItem item = new RssItem(savedArtPref.getString("title" + i, null), savedArtPref.getString("link" + i, null), savedArtPref.getString("date" + i, null), savedArtPref.getString("category" + i, null), savedArtPref.getString("thumbnail" + i, null));
                Log.d(TAG, "savedArt " + savedArtPref.getString("title" + i, null));
                rssItem.add(l++, item);
            }
            RssAdapterSaved adapter = new RssAdapterSaved(getActivity(), rssItem);
            savedArticle.setAdapter(adapter);
            savedArticle.setVisibility(View.VISIBLE);
            listViewSports.setVisibility(View.GONE);
            listViewTopStories.setVisibility(View.GONE);
            listViewWold.setVisibility(View.GONE);
            listViewTech.setVisibility(View.GONE);


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
