package com.example.dhruv.newsfeed;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */

public class RssFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainAct";
    AVLoadingIndicatorView avi;
    public static ListView listView;
    private View view;
    int pos;
    public static Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        MainActivity mnAc = new MainActivity();
        String type = mnAc.passType();
        pos = mnAc.position;
        Log.d(TAG, "type: " + type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
            listView = (ListView) view.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            avi.show();
            startService();
        } else {
            // If we are returning from a configuration change:
            // "view" is still attached to the previous view hierarchy
            // so we need to remove it and re-attach it to the current one
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        intent.putExtra("topic", pos);
        getActivity().startService(intent);
    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this ResultReceiver.
     */

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {

        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);

            if (items != null) {
                RssAdapter adapter = new RssAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
            avi.hide();
            listView.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(i);
        uri = Uri.parse(item.getLink());
        Intent intent = new Intent(RssFragment.super.getContext(), FullArticle.class);
    }

}