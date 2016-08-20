package com.example.dhruv.newsfeed;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */
public class RssService extends IntentService {

    public static long millis[] = new long[300];
    public static List<RssItem> rssItems = new ArrayList<RssItem>();
    public List<RssItem> temp = new ArrayList<RssItem>();
    public List<RssItem> temp2 = new ArrayList<RssItem>();
    public static String[] dateString = new String[300];

    public static final String TAG = "RssService";
    public int pos;
    private long tempAr[] = new long[300];
    private int len;
    public static long[] arr;
    public static int i=0;
    public static int x=0;

    public String passTopic[] = new String[]
            {
                    "topstories",
                    "sports"
            };


    public String[] topStories = new String[]
            {
                    "http://economictimes.indiatimes.com/rssfeedstopstories.cms",
                    "http://www.thehindu.com/?service=rss",
                    "http://timesofindia.indiatimes.com/rssfeedstopstories.cms",
                    "http://feeds.abcnews.com/abcnews/topstories",
                    "http://rss.nytimes.com/services/xml/rss/nyt/AsiaPacific.xml"
            };

    public String sports[] = new String[]
            {
                    "http://www.mirror.co.uk/sport/football/rss.xml",  // football with love
                    "http://www.espncricinfo.com/rss/content/story/feeds/6.xml",
                    "http://timesofindia.indiatimes.com/rssfeeds/4719148.cms",
                    "http://feeds.abcnews.com/abcnews/sportsheadlines"
            };

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        pos = MainActivity.position;
        Log.d(TAG, "Service started");
        Log.d(TAG, "pos= " + pos);
        Log.d(TAG, "len= " + passTopic[pos].toString().length());

        int j = 0;

        if (pos == 0) {
            for (int i = 0; i < topStories.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(topStories[i]));
                    int l = 2;
                    for (int k = j; k < temp.size()/1.5; k++) {            // make it back to temp.size()-10
                        Log.d(TAG, "temp: " + temp.get(l).getTitle());
                        rssItems.add(k, temp.get(l));
                        ++l;
                    }
                    ++j;

                } catch (XmlPullParserException e) {
                    Log.w(e.getMessage(), e);
                } catch (IOException e) {
                    Log.w(e.getMessage(), e);
                }
            }
        }

        if (pos == 1) {
            for (int i = 0; i < sports.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(sports[i]));
                    int l = 2;
                    for (int k = j; k < temp.size()-3;k++) {
                        Log.d(TAG, "temp: " + temp.get(l).getTitle());
                        rssItems.add(k, temp.get(l));
                        ++l;
                    }
                    ++j;

                } catch (XmlPullParserException e) {
                    Log.w(e.getMessage(), e);
                } catch (IOException e) {
                    Log.w(e.getMessage(), e);
                }
            }
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    private int nSquare(long val)
    {
        int h;
        for(h=0;h<=len;h++)
        {
            Log.d(TAG, "millis::: "+millis[h]);
            if(millis[h]==val)
            {
                Log.d(TAG, "iis"+h);
                Log.d(TAG,"meet "+millis[h]+" val "+val);
                break;
            }
        }
        return h;
    }


    private int BS(long val,int low,int high)
    {
        int middle = (low + high)/2;

        while( low <= high )
        {
            if ( arr[middle] < val )
                low = middle + 1;
            else if ( arr[middle] == val )
            {
                break;
            }
            else
                low = middle - 1;

            middle = (low + high)/2;
        }
        Log.d(TAG, "BS "+middle);
        return middle;
    }

    private void sort()
    {
        this.arr = millis;
        mergeSort(0,len);
    }

    private void mergeSort(int low, int high)
    {
        if(low<high)
        {
            int mid = (low+high)/2;
            mergeSort(low,mid);
            mergeSort(mid+1,high);
            mergeParts(low,mid,high);
        }
    }

    private void mergeParts(int low,int mid,int high)
    {

        for(int i=low;i<=high;i++)
        {
            tempAr[i] = arr[i];
        }

        int i= low,j=mid+1,k =low;
        while(i<=mid && j<=high)
        {
            if(tempAr[i]>=tempAr[j])
            {
                arr[k] = tempAr[i];
                i++;
            }
            else
            {
                arr[k] = tempAr[j];
                j++;
            }
            k++;
        }
        while(i<=mid)
        {
            arr[k] = tempAr[i];
            k++;
            i++;
        }
    }


    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}