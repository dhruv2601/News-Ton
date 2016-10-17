package dhruv.newsfeed;

import android.os.Bundle;
import android.os.Handler;
import dhruv.newsfeed.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by dhruv on 9/10/16.
 */

public class businessRef extends Fragment {
    public View view;
    public ImageView espn;
    public ImageView mirror;
    public ImageView neo;
    public ImageView tensports;
    public ImageView ndtvS;
    public ImageView crick;
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
        view = inflater.inflate(R.layout.business, container, false);

        wb = (WebView) view.findViewById(R.id.webViewForSports);

        espn = (ImageView) view.findViewById(R.id.espn);
        mirror = (ImageView) view.findViewById(R.id.mirror);
        neo = (ImageView) view.findViewById(R.id.newSports);
        tensports = (ImageView) view.findViewById(R.id.tenSports);
        ndtvS = (ImageView) view.findViewById(R.id.ndtvSports);
        crick = (ImageView) view.findViewById(R.id.crickBuzz);

        rlWithTop = (RelativeLayout) view.findViewById(R.id.rlWithBusiness);
        getRlWithTopWb = (RelativeLayout) view.findViewById(R.id.rlWithSportsWV);

        wb.setInitialScale(1);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wb.setScrollbarFadingEnabled(false);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setDisplayZoomControls(false);

        espn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://economictimes.indiatimes.com/";

                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, rlWithTop.getHeight());
                animate.setDuration(700);
                animate.setFillAfter(true);
                rlWithTop.startAnimation(animate);

                rlWithTop.setVisibility(View.GONE);

                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRlWithTopWb.setVisibility(View.VISIBLE);
                    }
                }, 700);
                // if not loading check the Search_class
            }
        });

        neo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://www.business-standard.com/";

                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, rlWithTop.getHeight());
                animate.setDuration(700);
                animate.setFillAfter(true);
                rlWithTop.startAnimation(animate);

                rlWithTop.setVisibility(View.GONE);

                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRlWithTopWb.setVisibility(View.VISIBLE);
                    }
                }, 700);
                // if not loading check the Search_class
            }
        });

        ndtvS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://timesofindia.indiatimes.com/business";

                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, rlWithTop.getHeight());
                animate.setDuration(700);
                animate.setFillAfter(true);
                rlWithTop.startAnimation(animate);

                rlWithTop.setVisibility(View.GONE);

                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRlWithTopWb.setVisibility(View.VISIBLE);
                    }
                }, 700);
                // if not loading check the Search_class
            }
        });
        tensports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://www.ft.com/";

                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, rlWithTop.getHeight());
                animate.setDuration(700);
                animate.setFillAfter(true);
                rlWithTop.startAnimation(animate);

                rlWithTop.setVisibility(View.GONE);

                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRlWithTopWb.setVisibility(View.VISIBLE);
                    }
                }, 700);
                // if not loading check the Search_class
            }
        });
        mirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "www.businessinsider.in/";

                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, rlWithTop.getHeight());
                animate.setDuration(700);
                animate.setFillAfter(true);
                rlWithTop.startAnimation(animate);

                rlWithTop.setVisibility(View.GONE);

                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRlWithTopWb.setVisibility(View.VISIBLE);
                    }
                }, 700);
                // if not loading check the Search_class
            }
        });

        crick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://www.telegraphindia.com/section/business/index.jsp#.V_pXMe0vDQo";

                TranslateAnimation animate = new TranslateAnimation(0, 0, 0, rlWithTop.getHeight());
                animate.setDuration(700);
                animate.setFillAfter(true);
                rlWithTop.startAnimation(animate);

                rlWithTop.setVisibility(View.GONE);

                wb.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wb.loadUrl(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRlWithTopWb.setVisibility(View.VISIBLE);
                    }
                }, 700);
            }
        });

        return view;
    }
}
