package com.example.dhruv.newsfeed;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */

public class PcWorldRssParser {

    private static final String TAG = "pcWorld";
    // We don't use namespaces
    private final String ns = null;

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

//    public static List<RssItem> senditems()
//    {
//        return items;
//    }


    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        List<RssItem> items = new ArrayList<RssItem>();
        String title = null;
        String link = null;
        String date = null;
        String category = null;
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
                Log.d(TAG, "turtle= " + title);
            }
            else if (name.equals("link")) {
                link = readLink(parser);
            }
            else if (name.equals("category")) {
                category = readCategory(parser);
                Log.d(TAG, "channelname "+category);
            }
            else if(name.equals("pubDate"))
            {
                date = readDate(parser);
                Log.d(TAG,"date: "+date);
            }
            if (title != null && link != null) {
                RssItem item = new RssItem(title, link, date, category);
                items.add(item);
                title = null;
                link = null;
                date = null;
                category = null;
            }
        }
        return items;
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
    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}