package com.example.dhruv.newsfeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by dhruv on 14/9/16.
 */

public class TopStoryReference extends Fragment {

    public View view;
    public ImageView toi;
    public ImageView indiat;
    public ImageView ndtv;
    public ImageView hindu;
    public ImageView abc;
    public ImageView telegraph;
    public static WebView wb;
    public String url;
    public static RelativeLayout rlWithTop;
    public static RelativeLayout getRlWithTopWb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.top_story_reference,container,false);

        wb = (WebView) view.findViewById(R.id.webViewForTop);

        toi = (ImageView) view.findViewById(R.id.toi);
        indiat = (ImageView) view.findViewById(R.id.india_today);
        ndtv = (ImageView) view.findViewById(R.id.ndtv);
        hindu = (ImageView) view.findViewById(R.id.hindu);
        abc = (ImageView) view.findViewById(R.id.abc);
        telegraph = (ImageView) view.findViewById(R.id.telegraph);

        rlWithTop = (RelativeLayout) view.findViewById(R.id.rlWithTop);
        getRlWithTopWb = (RelativeLayout) view.findViewById(R.id.rlWithTopWV);

        wb.setInitialScale(1);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wb.setScrollbarFadingEnabled(false);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setDisplayZoomControls(false);

        toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://timesofindia.indiatimes.com/";

                rlWithTop.setVisibility(View.GONE);
                getRlWithTopWb.setVisibility(View.VISIBLE);
                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);
                // if not loading check the Search_class
            }
        });

        indiat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://indiatoday.intoday.in/";

                rlWithTop.setVisibility(View.GONE);
                getRlWithTopWb.setVisibility(View.VISIBLE);
                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);
                // if not loading check the Search_class
            }
        });

        hindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://www.thehindu.com/";

                rlWithTop.setVisibility(View.GONE);
                getRlWithTopWb.setVisibility(View.VISIBLE);
                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);
                // if not loading check the Search_class
            }
        });
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://abcnews.go.com/";

                rlWithTop.setVisibility(View.GONE);
                getRlWithTopWb.setVisibility(View.VISIBLE);
                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);
                // if not loading check the Search_class
            }
        });
        telegraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://www.telegraph.co.uk/";

                rlWithTop.setVisibility(View.GONE);
                getRlWithTopWb.setVisibility(View.VISIBLE);
                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);
                // if not loading check the Search_class
            }
        });

        ndtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://www.ndtv.com/";

                rlWithTop.setVisibility(View.GONE);
                getRlWithTopWb.setVisibility(View.VISIBLE);
                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);
            }
        });

        return view;
    }
}
