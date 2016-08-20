package com.example.dhruv.newsfeed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dhruv on 7/8/16.
 */
public class RssAdapter extends BaseAdapter {

    private static final String TAG = "RSSAdapter";
    public static int globalPos;
    public static List<RssItem> items;
    public static String s;
    public static Context context;
    public static int mShortAnimationDuration;
    public static View thumb1View = null;
    public static Animator mCurrentAnimator;
    public static View convertView;
    public View view1 = null;

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
        final ViewHolder holder;
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

        this.convertView = convertView;
        thumb1View = convertView.findViewById(R.id.rand);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1 = view.getRootView();
                Log.d(TAG, "view1 " + view1);
                globalPos = position;
                zoomImageFromThumb(thumb1View);
                //  default news ki jgah the downloaded image
            }

        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = convertView.getResources().getInteger(
                android.R.integer.config_shortAnimTime);


        Button share = (Button) convertView.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/html");
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This Article Was Sent By a BITCHING App" + "\n");   // instead send the description here

                String articleLink = RssAdapter.items.get(position).getLink();    // yahan 3 ki jgah RssService ki list view mn jo bhi position p ye send vala btn hoga vo aayega
                shareIntent.putExtra(Intent.EXTRA_TEXT, items.get(position).getTitle() + "\n\n" + articleLink);
                context.startActivity(Intent.createChooser(shareIntent, "Share Article"));   // share ke badd app p nhi ja rha hai!!
            }
        });

        ImageView shareimg = (ImageView) convertView.findViewById(R.id.shareimg);
        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/html");
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This Article Was Sent By a BITCHING App" + "\n");   // instead send the description here

                String articleLink = RssAdapter.items.get(position).getLink();    // yahan 3 ki jgah RssService ki list view mn jo bhi position p ye send vala btn hoga vo aayega
                shareIntent.putExtra(Intent.EXTRA_TEXT, items.get(position).getTitle() + "\n" + articleLink);
                context.startActivity(Intent.createChooser(shareIntent, "Share Article"));   // share ke badd app p nhi ja rha hai!!
            }
        });

        ImageView fullart = (ImageView) convertView.findViewById(R.id.full);
        fullart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FullArticle.class);
                i.putExtra("url", RssAdapter.items.get(position).getLink().toString());
                Log.d(TAG, "link==" + RssAdapter.items.get(position).getLink().toString());
                context.startActivity(i);
            }
        });

        Button readFull = (Button) convertView.findViewById(R.id.readFull);
        readFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FullArticle.class);
                i.putExtra("url", RssAdapter.items.get(position).getLink().toString());
                Log.d(TAG, "link==" + RssAdapter.items.get(position).getLink().toString());
                context.startActivity(i);
            }
        });

        ImageView listenimg = (ImageView) convertView.findViewById(R.id.listenimg);
        listenimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.t1.speak(RssAdapter.items.get(position).getTitle().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Button listenbtn = (Button) convertView.findViewById(R.id.listenbtn);
        listenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.t1.speak(RssAdapter.items.get(position).getTitle().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //globalPos = position;
//                Intent i = new Intent(context, MainActivity.class);
                Log.d(TAG, "pos::" + position);
//                i.putExtra("position", position);
            }
        });



//        ----->>>>>    CAUTION  ::::::   DO NOT DELETE    <<<<<-------         //

//        Button listeToAll = (Button) convertView.findViewById(R.id.listentoall);
//        listeToAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i < RssService.rssItems.size(); i++) {
//                    Log.d(TAG, "listening" + finalNews);
//                    MainActivity.t1.speak(RssService.rssItems.get(i).getTitle().toString() + ", , , , , ,", TextToSpeech.QUEUE_ADD, null);
//                    Log.d(TAG, "i   " + i);
//
//                    //MainActivity.t1.speak(finalNews, TextToSpeech.QUEUE_FLUSH, null);
//                    Log.d(TAG, "afterListen");
//                }
//                //   MainActivity.t1.speak("",TextToSpeech.QUEUE_FLUSH,null);
//            }
//        });


//        ----->>>>>    CAUTION  ::::::   DO NOT DELETE    <<<<<-------         //


        holder.itemTitle.setText(items.get(position).getTitle());

        if (items.get(position).getCategory() != "") {
            holder.category.setText(items.get(position).getCategory());
        } else {
            holder.category.setText("News");
        }

        if (items.get(position).getThumbnail() == null) {
            holder.rand.setBackgroundResource(R.drawable.defaultnews);
            Log.d(TAG, "thumbnail is " + items.get(position).getThumbnail());
        }
        Picasso.with(RssAdapter.this.context).load(items.get(position).getThumbnail()).into(holder.rand);
        return convertView;
    }

    private void zoomImageFromThumb(final View thumbView) {

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final ImageView expandedImageView = (ImageView) view1.findViewById(R.id.expanded_image);

        Picasso.with(RssAdapter.this.context).load(items.get(globalPos).getThumbnail()).into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        view1.findViewById(R.id.rss_item_rl)                   //  yahan p R.id.rss_item_rl dal ke as a debugger use kr dekhne ke lye ki konse image khan set hue hai ;p
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;

        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });

    }

    private String calcTime(long x)
    {
        String ret;
        x/=(1000*60);
        if(x>(1440))
        {
            x/=1440;
            ret = String.valueOf(x)+" Day";
        }
        else if(x>60)
        {
            x/=60;
            ret = String.valueOf(x)+" Hr.";
        }
        else
        {
            ret = String.valueOf(x)+" Min";
        }
        return ret;
    }



    static class ViewHolder {
        TextView itemTitle;
        ImageView rand;
        TextView category;
        TextView showDate;
    }
}
