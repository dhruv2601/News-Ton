package com.example.dhruv.newsfeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TopicSelector extends AppCompatActivity {

    int count[] = new int[600];
    ArrayList<String> dateList = new ArrayList<String>();
    ArrayList<String> bb = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ProgressDialog pd;
    public static final String TAG = "Topics";
    public ImageView topstories;
    public ImageView sports;
    public ImageView tech;
    public ImageView bussiness;
    public ImageView delhi;
    public ImageView world;
    public ImageView bollywood;
    public ImageView fashion;
    public ImageView mumbai;
    public ImageView banglore;

    public TextView sportsTxt;
    public TextView sportsTime;
    public TextView topstoriesTxt;
    public TextView topstoriesTime;

    public int i;
    public String deterTime;

    public String links[] =
            {
                    "http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms",
                    "http://timesofindia.indiatimes.com/rssfeeds/4719148.cms",
                    "http://timesofindia.indiatimes.com/rssfeeds/5880659.cms", //tech
                    "http://timesofindia.indiatimes.com/rssfeeds/1898055.cms",//bussiness
                    "http://timesofindia.indiatimes.com/rssfeeds/-2128839596.cms",//delhi
                    "http://timesofindia.indiatimes.com/rssfeeds/296589292.cms",//world
                    "http://timesofindia.indiatimes.com/rssfeeds/1081479906.cms",//bollywood
                    "http://timesofindia.indiatimes.com/rssfeeds/2886704.cms",//fashion
                    "http://timesofindia.indiatimes.com/rssfeeds/-2128838597.cms",//mumbai
                    "" // hindi
            };

    public ImageView imgArr[] =
            {
                    topstories,
                    sports,
                    tech,
                    bussiness,
                    delhi,
                    world,
                    bollywood,
                    fashion,
                    mumbai,
                    banglore
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_topic_selector);

        count[0] = 0;
        pd = new ProgressDialog(TopicSelector.this);

        topstories = (ImageView) findViewById(R.id.topStories);
        sports = (ImageView) findViewById(R.id.sports);
        tech = (ImageView) findViewById(R.id.photos);
        bussiness = (ImageView) findViewById(R.id.bussiness);
        delhi = (ImageView) findViewById(R.id.delhi);
        world = (ImageView) findViewById(R.id.world);
        bollywood = (ImageView) findViewById(R.id.bollywood);
        fashion = (ImageView) findViewById(R.id.fashion);
        mumbai = (ImageView) findViewById(R.id.mumbai);
        banglore = (ImageView) findViewById(R.id.banglore);

        topstoriesTxt = (TextView) findViewById(R.id.topStoriestxt);
        topstoriesTime = (TextView) findViewById(R.id.topStoriesTime);
        sportsTxt = (TextView) findViewById(R.id.sportsTxt);
        sportsTime = (TextView) findViewById(R.id.sportsTime);

        for (i = 0; i < 10; i++) {
            new TheTask(i).execute();
            pd.show();
        }

        Intent intent1 = getIntent();
        if (intent1 != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
            if (b == false) {
                // add a layout with img and reload btn
                Intent intent = new Intent(TopicSelector.this, NoInternet.class);
                startActivity(intent);
            }
        }


        topstories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 0);
                    i.putExtra("topic", 0);
                    startActivity(i);
                }
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 1);
                    i.putExtra("topic", 1);
                    startActivity(i);
                }


            }
        });

        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 2);
                    i.putExtra("topic", 2);
                    startActivity(i);
                }


            }
        });

        bussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 3);
                    i.putExtra("topic", 3);
                    startActivity(i);
                }


            }
        });

        delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 4);
                    i.putExtra("topic", 4);
                    startActivity(i);
                }


            }
        });

        world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 5);
                    i.putExtra("topic", 5);
                    startActivity(i);
                }


            }
        });

        bollywood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 6);
                    i.putExtra("topic", 6);
                    startActivity(i);
                }

            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 7);
                    i.putExtra("topic", 7);
                    startActivity(i);
                }


            }
        });

        mumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, MainActivity.class);
                    Log.d(TAG, "position= " + 8);
                    i.putExtra("topic", 8);
                    startActivity(i);
                }
            }
        });

        banglore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean b = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
                if (b == false) {
                    // add a layout with img and reload btn
                    Intent i = new Intent(TopicSelector.this, NoInternet.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TopicSelector.this, HindiNews.class);
                    Log.d(TAG, "position= " + 9);
                    i.putExtra("topic", 9);
                    startActivity(i);
                }
            }
        });


    }

    class TheTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "thetask";

        public int i;

        public TheTask(int i) {
            TheTask.this.i = i;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(links[i]);
                Log.d(TAG, "iis " + i);
                Log.d(TAG, "urlis " + url);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(url.openConnection().getInputStream(), "UTF_8");
                //xpp.setInput(getInputStream(url), "UTF-8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                title.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equals("pubDate")) {
                            if (insideItem) {
                                dateList.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {

                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem)
                                count[i]++;
                            bb.add(xpp.nextText()); //extract the link of article
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }

                count[i + 1] += count[i];
//                Document doc = Jsoup.parse(bb.get(3).toString());
//                org.jsoup.nodes.Element link = doc.select("img").first();
//                Log.d(TAG, "linkkk" + link);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
// TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.d(TAG, "bb " + bb);
            Log.d(TAG, "issss" + i);
            Document doc;

            org.jsoup.nodes.Element link = null;
            int j = 0;

//            String s = "https://www.google.co.in/search?q=news&client=ubuntu&hs=9CS&channel=fs&biw=1301&bih=671&tbm=isch&source=lnms&sa=X&ved=0ahUKEwim-fW4pNLOAhXBq48KHR3rBPcQ_AUICygE&dpr=1#imgrc=1NfHlKsVsMoJnM%3A";

            String s = null;
            int flag = 0;

            if (i == 9)   // for hindi
            {
                s = "forhindinewsyouneednothing!!";
            }

            if (s == null) {
                Log.d(TAG, "insideLoop");
                while (flag != 1) {
                    j++;
                    if (i == 0) {
                        doc = Jsoup.parse(bb.get(j).toString());
                    } else {
                        doc = Jsoup.parse(bb.get(count[i - 1] + j).toString());
                    }
                    Log.d(TAG, "doc:: " + doc);
                    link = doc.select("img").first();
                    Log.d(TAG, "link:: " + link);
                    if (link != null) {
                        s = link.attr("src");
                        if (s != null) {
                            flag = 1;
                        }
                    }
                    Log.d(TAG, "src:: " + s + "j:: " + j);
                }
            }

            pd.dismiss();

            if (i == 0) {
                Picasso.with(TopicSelector.this).load(s).fit().into(topstories);
                Log.d(TAG, "jis: " + j);
                String text = (title.get(j).toString());
                topstoriesTxt.setText("\n" + text);
                Log.d(TAG, "TEXTTTT " + text);

                Date date = null;
                String dateStr = dateList.get(j).toString();
                DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                try {
                    date = formatter.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long x = System.currentTimeMillis() - date.getTime();
                x = calcTime(x);
                String fix = " " + String.valueOf(x) + " " + deterTime;
                topstoriesTime.setText(fix);
            }

            if (i == 1) {

                Picasso.with(TopicSelector.this).load(s).fit().into(sports);
                String text = title.get(count[0] + j - 1).toString();
                Log.d(TAG, "TEXTTT " + text);
                sportsTxt.setText("\n" + text);

                Date date = null;
                String dateStr = dateList.get(count[0] + j).toString();
                Log.d(TAG, "dateStr " + dateStr);
                DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                try {
                    date = formatter.parse(dateStr);
                    Log.d(TAG, "DATE " + date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long x = System.currentTimeMillis() - date.getTime();
                Log.d(TAG, "timeX " + x);
                x = calcTime(x);
                String fix = " " + String.valueOf(x) + " " + deterTime;
                sportsTime.setText(fix);
            }

            if (i == 2) {
                Picasso.with(TopicSelector.this).load(s).fit().into(tech);
            }

            if (i == 3) {
                Picasso.with(TopicSelector.this).load(s).fit().into(bussiness);
            }

            if (i == 4) {
                Picasso.with(TopicSelector.this).load(s).fit().into(delhi);
            }

            if (i == 5) {
                Picasso.with(TopicSelector.this).load(s).fit().into(world);
            }

            if (i == 6) {
                Picasso.with(TopicSelector.this).load(s).fit().into(bollywood);
            }

            if (i == 7) {
                Picasso.with(TopicSelector.this).load(s).fit().into(fashion);
            }

            if (i == 8) {
                Picasso.with(TopicSelector.this).load(s).fit().into(mumbai);
            }

            if (i == 9) {
                Log.d(TAG, "into9");
                banglore.setImageResource(R.drawable.hindi);
            }

        }

        private long calcTime(long x) {
            x /= (60 * 1000);
            if (x / 60 != 0) {
                deterTime = "Hrs";
                x /= 60;
                return x;
            } else {
                deterTime = "Min.";
                return x;
            }
        }
    }
}