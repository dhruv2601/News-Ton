package dhruv.newsfeed;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */
public class RssService extends IntentService implements java.io.Serializable {

    public static long millis[] = new long[300];
    public static List<RssItem> rssItems = new ArrayList<RssItem>();
    public List<RssItem> temp = Collections.synchronizedList(new ArrayList<RssItem>());

    public static final String TAG = "RssService";
    public int pos;
    private long tempAr[] = new long[300];
    private int len;
    public static long[] arr;
    public static int i = 0;
    public static int x = 0;
    public static int topStoriesCount;
    public static int sportsCount;
    public static int techCount;
    public static int worldCount;
    public static int count = 0;
    public int totalNewsCount = 0;


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


    public String[] topStories = new String[]
            {
                    "http://www.thehindu.com/?service=rss",
                    "http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms",
                    "http://economictimes.indiatimes.com/rssfeedstopstories.cms",
//                    "http://feeds.abcnews.com/abcnews/topstories",
//                    "http://rss.nytimes.com/services/xml/rss/nyt/AsiaPacific.xml"
            };

    public String sports[] = new String[]
            {
                    "http://feeds.abcnews.com/abcnews/sportsheadlines",
                    "http://www.mirror.co.uk/sport/football/rss.xml",  // football with love
                    "http://www.espncricinfo.com/rss/content/story/feeds/6.xml",
                    "http://timesofindia.indiatimes.com/rssfeeds/4719148.cms"
            };
    public String tech[] = new String[]
            {
                    "http://feeds.feedburner.com/TechCrunch/",
                    "http://www.pcworld.com/index.rss",
                    "http://www.pcworld.com/reviews/index.rss"
            };

    public String hindi[] = new String[]
            {
                    "http://www.amarujala.com/rss/national-news.xml",
                    "https://news.google.co.in/news?cf=all&ned=hi_in&hl=hi&output=rss",
                    "http://www.amarujala.com/rss/sports-news.xml",
                    "http://www.amarujala.com/rss/entertainment.xml"
            };

    public String entertainment[] = new String[]
            {
                    "http://www.music-news.com/rss/UK/news",
                    "http://english.newstracklive.com/rss-feed/bollywood.xml",
                    "http://feeds.reuters.com/reuters/entertainment?format=xml",
                    "http://feeds.feedburner.com/thr/television"
            };
    public String business[] = new String[]
            {
                    "http://feeds.feedburner.com/ndtvprofit-latest",
                    "http://www.businessinsider.in/rss_section_feeds/2147477994.cms",
                    "http://www.business-standard.com/rss/markets-106.rss",
                    "http://economictimes.indiatimes.com/markets/rssfeeds/1977021501.cms"
            };

    public String automobiles[] = new String[]
            {
                    "http://www.autocarindia.com/RSS/rss.ashx",
                    "http://feeds.feedburner.com/AutomotiveNewsFutureProductPipelineFeed",
                    "http://feeds.highgearmedia.com/?sites=TheCarConnection&type=all",
                    "http://feeds.feedburner.com/autonews/BreakingNews"
            };

    public String politics[] = new String[]
            {
                    "http://www.livemint.com/rss/economy_politics",
                    "http://feeds.washingtonpost.com/rss/rss_election-2012",
                    "http://feeds.feedburner.com/realclearpolitics/qlMj"
            };

//    public String education[] = new String[]
//            {
//                    "http://www.hindustantimes.com/rss/education/rssfeed.xml",
//                    "http://educationnext.org/feed/",
//                    "http://feeds.bbci.co.uk/news/education/rss.xml?edition=uk",
//            };


    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        pos = MainActivity.position;
//        rssItems = null;
//        rssItems.clear();            // should do something like this to stop them from  mixing up
        Log.d(TAG, "Service started");
        Log.d(TAG, "pos= " + pos);
//        Log.d(TAG, "len= " + passTopic[pos].toString().length());

        int j = 0;

        if (pos == 1) {
            topStoriesCount = 0;

            count = 0;
            for (int i = 0; i < topStories.length; i++) {
                try {
                    Log.d(TAG, "startingLoop");
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(topStories[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() / 1.5; k++) {            // make it back to temp.size()-10
                        Log.d(TAG, "temp: " + temp.get(l).getTitle());
                        rssItems.add(k, temp.get(l));
                        topStoriesCount++;
                        count++;
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;

                } catch (XmlPullParserException e) {
                    Log.w(e.getMessage(), e);
                } catch (IOException e) {
                    Log.w(e.getMessage(), e);
                }
            }
        }

        if (pos == 2) {
            sportsCount = 0;
            count = 0;
            for (int i = 0; i < sports.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(sports[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "temp: " + temp.get(l).getTitle());
                        rssItems.add(k, temp.get(l));
                        sportsCount++;
                        count++;
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;

                } catch (XmlPullParserException e) {
                    Log.w(e.getMessage(), e);
                } catch (IOException e) {
                    Log.w(e.getMessage(), e);
                }
            }
        }

        if (pos == 3) {
            techCount = 0;
            count = 0;
            for (int i = 0; i < tech.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(tech[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "temp: " + temp.get(l).getTitle());
                        rssItems.add(k, temp.get(l));
                        techCount++;
                        count++;
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;

                } catch (XmlPullParserException e) {
                    Log.w(e.getMessage(), e);
                } catch (IOException e) {
                    Log.w(e.getMessage(), e);
                }
            }
        }

        if (pos == 4) {
            for (int i = 0; i < hindi.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();

                    temp = parser.parse(getInputStream(hindi[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "hinditemp: " + temp);
                        rssItems.add(k, temp.get(l));
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (pos == 5) {
            for (int i = 0; i < entertainment.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(entertainment[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "EntertainmentTemp: " + temp);
                        rssItems.add(k, temp.get(l));
                        Log.d(TAG, "Entertainment_turtle " + temp.get(l).getLink());
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (pos == 6) {
            for (int i = 0; i < business.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(business[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "EntertainmentTemp: " + temp);
                        rssItems.add(k, temp.get(l));
                        Log.d(TAG, "Entertainment_turtle " + temp.get(l).getLink());
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (pos == 7) {
            for (int i = 0; i < automobiles.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(automobiles[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "EntertainmentTemp: " + temp);
                        rssItems.add(k, temp.get(l));
                        Log.d(TAG, "Entertainment_turtle " + temp.get(l).getLink());
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (pos == 8) {
            for (int i = 0; i < politics.length; i++) {
                try {
                    PcWorldRssParser parser = new PcWorldRssParser();
                    temp = parser.parse(getInputStream(politics[i]));
                    int l = 2;
                    for (int k = j; k < temp.size() - 3; k++) {
                        Log.d(TAG, "EntertainmentTemp: " + temp);
                        rssItems.add(k, temp.get(l));
                        Log.d(TAG, "Entertainment_turtle " + temp.get(l).getLink());
                        ++l;
                        totalNewsCount++;
                    }
                    ++j;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Log.d(TAG, "timeInd " + MainActivity.timeInd);
        Log.d(TAG, "timeHolder" + MainActivity.timeholder[10]);
        for (int g = 0; g < totalNewsCount; g++) {
            Log.d(TAG, "timeDiff " + MainActivity.timeholder[g]);
        }

        bubble();
        for (int g = 0; g < totalNewsCount; g++) {
            Log.d(TAG, "SortedTimeDiff " + MainActivity.timeholder[g]);
        }

        for (int g = 0; g <= MainActivity.timeInd; g++) {
            MainActivity.timeholder[g] = 0;
        }

        MainActivity.timeInd = 0;
        Log.d(TAG, "lenOfArrayList " + rssItems.size());
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        bundle.putSerializable(passTopic[pos], (Serializable) rssItems); // to save list state to get later when tabs are switched
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    private int nSquare(long val) {
        int h;
        for (h = 0; h <= len; h++) {
            Log.d(TAG, "millis::: " + millis[h]);
            if (millis[h] == val) {
                Log.d(TAG, "iis" + h);
                Log.d(TAG, "meet " + millis[h] + " val " + val);
                break;
            }
        }
        return h;
    }


    private void bubble() {
        for (int i = 0; i < totalNewsCount - 1; i++) {
            for (int j = 0; j < totalNewsCount - i - 1; j++) {
                if (MainActivity.timeholder[j] > MainActivity.timeholder[j + 1]) {
                    RssItem itemTemp = rssItems.get(j);
                    long tempTime = MainActivity.timeholder[j];
                    rssItems.set(j, rssItems.get(j + 1));
                    MainActivity.timeholder[j] = MainActivity.timeholder[j + 1];
//                    rssItems.add(j,rssItems.get(j+1));
                    MainActivity.timeholder[j + 1] = tempTime;
                    rssItems.set(j + 1, itemTemp);
                }
            }
        }
    }


    private int BS(long val, int low, int high) {
        int middle = (low + high) / 2;

        while (low <= high) {
            if (arr[middle] < val)
                low = middle + 1;
            else if (arr[middle] == val) {
                break;
            } else
                low = middle - 1;

            middle = (low + high) / 2;
        }
        Log.d(TAG, "BS " + middle);
        return middle;
    }

    private void sort() {
        this.arr = millis;
        mergeSort(0, len);
    }

    private void mergeSort(int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(low, mid);
            mergeSort(mid + 1, high);
            mergeParts(low, mid, high);
        }
    }

    private void mergeParts(int low, int mid, int high) {

        for (int i = low; i <= high; i++) {
            tempAr[i] = arr[i];
        }

        int i = low, j = mid + 1, k = low;
        while (i <= mid && j <= high) {
            if (tempAr[i] >= tempAr[j]) {
                arr[k] = tempAr[i];
                i++;
            } else {
                arr[k] = tempAr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
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