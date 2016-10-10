package com.example.dhruv.newsfeed;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */

public class PcWorldRssParser extends AsyncTask {

    private static final String TAG = "pcWorld";
    // We don't use namespaces
    private final String ns = null;
    int count = 0;

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {


        parser.require(XmlPullParser.START_TAG, null, "rss");
        List<RssItem> items = new ArrayList<RssItem>();
        String title = null;
        String link = null;
        String date = null;
        String category = null;
        String shortDesc = null;
        String thumbnail = null;

        while ((parser.next() != XmlPullParser.END_DOCUMENT) && count <= 50) {    //set a count condition to keep a check on the no.of feeds displayed

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("title")) {
                title = readTitle(parser);
                ++count;
                Log.d(TAG, "turtle= " + title);
            }

            if (name.trim().equals("link")) {
                link = readLink(parser);
                String desc = link.replace("<![CDATA[", "");
                desc = desc.replace("]]>", "");
                link = desc;

                Log.d(TAG, "linkisss" + link);
            }
            if (name.equals("category")) {
                category = readCategory(parser);
                Log.d(TAG, "channelname " + category + " link " + link);
            }

            if (name.equals("pubDate")) {
                date = readDate(parser);

                Date d1 = null;
                Date currD = null;
                SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                try {
                    d1 = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar c = Calendar.getInstance();
                Log.d(TAG, "calendar " + c);
                Log.d(TAG, "calendarTime " + c.getTime());

                String date2 = format.format(c.getTime());
                Log.d(TAG, "calendarDate2 " + date2);
                java.sql.Time ppsTime = null;
                java.sql.Time currTime = new java.sql.Time(c.getTime().getTime());
                if (d1 != null) {
                    ppsTime = new java.sql.Time(d1.getTime());
                    if (ppsTime != null) {
                        Log.d(TAG, "ppsTime.getTime  " + ppsTime.getTime());
                        MainActivity.timeholder[MainActivity.timeInd] = currTime.getTime() - ppsTime.getTime();
//                    Log.d(TAG,"diffff "+(currTime.getTime() - ppsTime.getTime()));
                        MainActivity.timeInd++;
                    }
                }
                Log.d(TAG, "ppsTime  " + ppsTime);


//                RssService.dateString[RssService.x] = date;
//              ++RssService.x;
//                Log.d(TAG, "date: " + date + "i     " + RssService.x);
            }

//            if(name.equals("description"))          // works fine and good with economic time only!!!
//            {
//                String desc = readDesc(parser);
//                title = desc;
//                Log.d(TAG,"desc   "+desc);
//            }

            if (name.trim().equals("description")) {
                thumbnail = parser.getAttributeValue(null, "img");
                Log.d(TAG, "src  " + thumbnail);
            }

            if (name.trim().equals("thumbnail")) {
                thumbnail = parser.getAttributeValue(null, "url");
                Log.d(TAG, "thumb " + thumbnail);
            }

            if (name.trim().equals("media:thumbnail")) {
                thumbnail = parser.getAttributeValue(null, "url");
                Log.d(TAG, "pictureLink" + thumbnail);
            }

            if (name.trim().equals("media:content")) {
                {
                    thumbnail = parser.getAttributeValue(null, "url");
                    Log.d(TAG, "VideoContent " + thumbnail);
                }
            }

            if (title != null && link != null) {
                Log.d(TAG, "links: " + link);
                RssItem item = new RssItem(title, link, date, category, thumbnail);
                items.add(item);
                title = null;
                link = null;
                date = null;
                category = null;
//                thumbnail = null;
            }
        }
        return items;
    }

    private String readDesc(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return link;
    }


    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readCategory(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "category");
        String channel = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "category");
        return channel;
    }

    private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return date;
    }

    private String readThumbnail(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:thumbnail");
        String thumbnailUrl = parser.getAttributeValue(null, "url");
        parser.nextTag();
        return thumbnailUrl;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }
}