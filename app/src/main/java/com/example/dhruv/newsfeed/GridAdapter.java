package com.example.dhruv.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by dhruv on 17/8/16.
 */
public class GridAdapter extends BaseAdapter{

    private String[] links;
    private Context context;

    public GridAdapter(Context c, String[] links) {
        this.links = links;
        context = c;
    }

    @Override
    public int getCount() {
        return links.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            grid = new View(context);
            grid = inflater.inflate(R.layout.gridlayout, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.gridImg);
            Picasso.with(context).load(links[position]).into(imageView);
        }
        else
        {
            grid = convertView;
        }
        return grid;
    }
}
