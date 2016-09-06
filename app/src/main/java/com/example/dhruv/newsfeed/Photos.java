package com.example.dhruv.newsfeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Photos extends AppCompatActivity {

    private static final String TAG = "Photo";
    public static JSONArray temp;
    public static String imageLinks[];
    public static int k = 0;
    public static String getImageUrl;
    public static Context c;
    public static GridAdapter adapter;

    public static String links[] = {
//            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=newest&phrase=premier league",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=government",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=imdb",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=cars",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=times of india",
            "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&sort_order=best&phrase=top news"
    };


    public static GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        c = Photos.this;

        Button b = (Button) findViewById(R.id.GoBtn);
        grid = (GridView) findViewById(R.id.gridView);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backGrn back = new backGrn();
                back.execute();

                adapter = new GridAdapter(Photos.this, imageLinks);
//                grid.setAdapter(adapter);
            }
        });
    }

    public static void onResponseGetImages(JSONObject response) throws JSONException {
        int resultCount = Integer.parseInt(response.getString("result_count"));
        Log.d(TAG, "resultCount " + resultCount);
        if (resultCount > 0) {
            temp = response.getJSONArray("images");
            Log.d(TAG, "temp " + temp);
        }
    }
}

class backGrn extends AsyncTask {

    private static final String TAG = "backgrn";

    @Override
    protected Object doInBackground(Object[] params) {

  //      for (int i = 0; i < Photos.links.length; i++) {
            Photos.getImageUrl = Photos.links[0];

            Log.d(TAG, "enteredGBI");
            // TODO Auto-generated method stub
            final HashMap<String, String> header = new HashMap<String, String>();
            header.put("Api-Key", "na7hqhx9pygkwg5953evmcgs");

            Log.d(TAG, "beforeGetImageData");
            JsonObjectRequest getImageData = new JsonObjectRequest(Photos.getImageUrl, null, new Response.Listener<JSONObject>() {
                @SuppressLint("NewApi")
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "onResponse");
                    try {
                        Photos.onResponseGetImages(response);
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
                    Log.d(TAG, "getheader");
                    return header;
                }
            };
            Log.d(TAG, "breforeAppControler");
            Log.d(TAG, "getImageData " + getImageData);
            AppController.getInstance().addToRequestQueue(getImageData, "Get Image Data");

//            for (int j = 0; j < 3; j++) {
                JSONObject temp1 = null;
                JSONArray displayArraySize = null;
                JSONObject temp2 = null;
                try {
                    Log.d(TAG, "temp:: " + Photos.temp);
                    temp1 = Photos.temp.getJSONObject(0);
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
                    Photos.imageLinks[Photos.k] = temp2.getString("uri");
                    Photos.k++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//            }

  //      }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Photos.grid.setAdapter(Photos.adapter);
    }
}