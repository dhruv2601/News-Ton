package dhruv.newsfeed;

import dhruv.newsfeed.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by dhruv on 11/9/16.
 */
public class Search_class extends Fragment {

    private static final String TAG = "searchClass";
    public View view;
    public static WebView webView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_web, container, false);

        final EditText edt = (EditText) view.findViewById(R.id.search_box);

        webView = (WebView) view.findViewById(R.id.search_web_view);
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String query;
                query = edt.getText().toString();
                query = editQuery(query);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

                Log.d(TAG, "query " + query);
                webView.loadUrl("https://www.google.co.in/#q=" + query + "&tbm=nws");
                return true;
            }
        });
        this.view = view;
        return view;
    }

    public String editQuery(String s) {
        String x = " ";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                x += '+';
            } else {
                x += s.charAt(i);
            }
        }
        return x;
    }


}
