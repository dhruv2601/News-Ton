package com.example.dhruv.newsfeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Photos extends AppCompatActivity {

    private static final String TAG = "Photo";
    JSONArray temp;
    String imageLinks[];
    int k=0;
    public String getImageUrl;

    String links[] = {
//            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=newest&phrase=premier league",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=government",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=imdb",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=cars",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=times of india",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=top news"
    };


    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        for (int i = 0; i < links.length; i++) {
            getImageUrl = links[i];

            getBackgroundImage();

            for(int j=0;j<3;j++)
            {
                JSONObject temp1 = null;
                JSONArray displayArraySize = null;
                JSONObject temp2 = null;
                try {
                    Log.d(TAG, "temp:: "+temp);
                    temp1 = temp.getJSONObject(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (temp1 != null) {
                        displayArraySize = temp1.getJSONArray("display_size");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (displayArraySize != null) {
                        temp2 = displayArraySize.getJSONObject(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    imageLinks[k] = temp2.getString("uri");
                    k++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        GridAdapter adapter = new GridAdapter(Photos.this, imageLinks);
        grid = (GridView) findViewById(R.id.gridView);
        grid.setAdapter(adapter);
    }

    private void getBackgroundImage() {
        Log.d(TAG, "enteredGBI");
        // TODO Auto-generated method stub
        final HashMap<String, String> header = new HashMap<String, String>();
        header.put("Api-Key", getString(R.string.getty_id));

        Log.d(TAG, "beforeGetImageData");
        JsonObjectRequest getImageData = new JsonObjectRequest(getImageUrl, null, new Response.Listener<JSONObject>()
        {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse");
                try {
                    onResponseGetImages(response);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d(TAG,"getheader");
                return header;
            }
        };
        Log.d(TAG, "breforeAppControler");
        Log.d(TAG, "getImageData "+getImageData);
        AppController.getInstance().addToRequestQueue(getImageData, "Get Image Data");
    }

    private void onResponseGetImages(JSONObject response) throws JSONException {
        int resultCount = Integer.parseInt(response.getString("result_count"));
        Log.d(TAG,"resultCount "+resultCount);
        if(resultCount > 0)
        {
            temp = response.getJSONArray("images");
            Log.d(TAG, "temp "+temp);
        }
    }
}