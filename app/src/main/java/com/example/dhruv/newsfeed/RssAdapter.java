package com.example.dhruv.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */
public class RssAdapter extends BaseAdapter {

    private static final String TAG = "RSSAdapter";
    private final List<RssItem> items;
    public static String s;
    private final Context context;

    public RssAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.rss_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            holder.rand = (ImageView) convertView.findViewById(R.id.rand);
            holder.category = (TextView) convertView.findViewById(R.id.channel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            Log.d(TAG, "pos==" + position);
            s = items.get(position).getLink();
            Log.d(TAG, "s==" + s);
        }

//        Log.d(TAG, "channel "+items.get(position).getChannel().toString());
        holder.itemTitle.setText(items.get(position).getTitle());

        if (items.get(position).getCategory() != "") {
            holder.category.setText(items.get(position).getCategory());
        }
        else
        {
            holder.category.setText("News");
        }
        return convertView;
    }


    static class ViewHolder {
        TextView itemTitle;
        ImageView rand;
        TextView category;

    }
}