package dhruv.newsfeed;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

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
  private GoogleApiClient client;
  public static WebView wv;
  public static ProgressBar progressBar;
  Random rnd;
  public TextView onItemClickSubs;


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
    Log.d(TAG, "getItem " + position);
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
      convertView = View.inflate(context, R.layout.rss_item_two, null);
//            onItemClickSubs = (Button) convertView.findViewById(R.id.onItemClickSubs);
      onItemClickSubs = (TextView) convertView.findViewById(R.id.itemTitle);
      holder = new ViewHolder();
      holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
      holder.rand = (ImageView) convertView.findViewById(R.id.rand);
      holder.category = (TextView) convertView.findViewById(R.id.channel);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
      globalPos = position;
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

    final LinearLayout llsave = (LinearLayout) convertView.findViewById(R.id.llsave);

    llsave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        SavedArticleClass.noSavedArt.setVisibility(View.GONE);
        SharedPreferences addToList;
        addToList = context.getSharedPreferences("savedArticle", 0);
        SharedPreferences.Editor editor = addToList.edit();

        //  Log.d(TAG, "posFind::: " + addImg.getVerticalScrollbarPosition());
        editor.putString("title" + MainActivity.savedArticleSize, items.get(globalPos).getTitle());
        editor.putString("link" + MainActivity.savedArticleSize, items.get(globalPos).getLink());
        editor.putString("date" + MainActivity.savedArticleSize, items.get(globalPos).getDate());
        editor.putString("category" + MainActivity.savedArticleSize, items.get(globalPos).getCategory());
        editor.putString("thumbnail" + MainActivity.savedArticleSize, items.get(globalPos).getThumbnail());

        MainActivity.savedArticleSize++;
        editor.putInt("size", MainActivity.savedArticleSize);
        editor.apply();

        Toast.makeText(context, "Article Added To Reading List", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Article Added To Reading List");
      }
    });

    Button btSave = (Button) convertView.findViewById(R.id.save);
    btSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


        SavedArticleClass.noSavedArt.setVisibility(View.GONE);
        SharedPreferences addToList;
        addToList = context.getSharedPreferences("savedArticle", 0);
        SharedPreferences.Editor editor = addToList.edit();

        editor.putString("title" + MainActivity.savedArticleSize, items.get(globalPos).getTitle());
        editor.putString("link" + MainActivity.savedArticleSize, items.get(globalPos).getLink());
        editor.putString("date" + MainActivity.savedArticleSize, items.get(globalPos).getDate());
        editor.putString("category" + MainActivity.savedArticleSize, items.get(globalPos).getCategory());
        editor.putString("thumbnail" + MainActivity.savedArticleSize, items.get(globalPos).getThumbnail());

        MainActivity.savedArticleSize++;
        editor.putInt("size", MainActivity.savedArticleSize);
        editor.apply();

        Toast.makeText(context, "Article Added To Reading List", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Article Added To Reading List");

      }
    });

    Button share = (Button) convertView.findViewById(R.id.share);
    share.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/html");
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This Article Was Sent By News Feed App" + "\n");   // instead send the description here

        String articleLink = RssAdapter.items.get(position).getLink();
        shareIntent.putExtra(Intent.EXTRA_TEXT, items.get(position).getTitle() + "\n\n" + articleLink);
        context.startActivity(Intent.createChooser(shareIntent, "Share Article"));
      }
    });


    ImageView shareimg = (ImageView) convertView.findViewById(R.id.shareimg);
    shareimg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/html");
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This Article Was Sent By News Feed App" + "\n");   // instead send the description here

        String articleLink = RssAdapter.items.get(position).getLink();    // yahan 3 ki jgah RssService ki list view mn jo bhi position p ye send vala btn hoga vo aayega
        shareIntent.putExtra(Intent.EXTRA_TEXT, items.get(position).getTitle() + "\n" + articleLink);
        context.startActivity(Intent.createChooser(shareIntent, "Share Article"));   // share ke badd app p nhi ja rha hai!!
      }
    });

    onItemClickSubs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(RssAdapter.context, R.style.MyDialogTheme);
        alert.setTitle(RssAdapter.items.get(position).getTitle());

        wv = new WebView(RssAdapter.context);
        wv.setInitialScale(1);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv.setScrollbarFadingEnabled(false);

        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);

        wv.loadUrl(RssAdapter.items.get(position).getLink());
        wv.setWebViewClient(new WebViewClient() {
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
          }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
          }
        });
        alert.show();

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

//        Button listeToAll = (Button) convertView.findViewById(R.id.listenToAll);
//        listeToAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i < RssService.rssItems.size(); i++) {
//                    Log.d(TAG, "listening");
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
      holder.rand.setBackgroundResource(R.drawable.news_feed_photo);
    }


    Picasso.with(RssAdapter.this.context).load(items.get(position).getThumbnail()).into(holder.rand);
    return convertView;
  }

  private void zoomImageFromThumb(final View thumbView) {

    if (mCurrentAnimator != null) {
      mCurrentAnimator.cancel();
    }

//        final Dialog dialog = new Dialog(RssAdapter.context);
//        dialog.setContentView(R.layout.expand_image);

    AlertDialog.Builder alert = new AlertDialog.Builder(RssAdapter.context, R.style.MyImageTheme);
    alert.setTitle("Image");

    ImageView img = new ImageView(RssAdapter.context);
    alert.setView(img);

    Picasso.with(RssAdapter.this.context).load(items.get(globalPos).getThumbnail()).resize(800, 600).into(img);

    alert.setPositiveButton("     Close  ", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        dialog.dismiss();
      }
    });


    alert.setNegativeButton("DOWNLOAD    ", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

        final File direct = new File(Environment.getExternalStorageDirectory() + "/NewsFeed");

        if (!direct.exists()) {
          File wallpaperDirectory = new File("/sdcard/NewsFeed/");
          wallpaperDirectory.mkdirs();
        }


        Target target = new Target() {
          @Override
          public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {
              @Override
              public void run() {
                File file = new File(
                        Environment.getExternalStorageDirectory().getPath() + "/Download"
                                + "/saved" + System.currentTimeMillis() % 10000 + ".jpg");

//                                File file = new File(new File("/sdcard/NewsFeed/"), direct.toString()+System.currentTimeMillis()%10000+".jpg");

                Log.d(TAG, "fileName " + file);
                try {
                  file.createNewFile();
                  FileOutputStream ostream = new FileOutputStream(file);
                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                  Log.d(TAG, "bitmap " + bitmap);
                  ostream.close();
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }).start();
            Toast.makeText(context, "Image Saved In Downloads", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onBitmapFailed(Drawable errorDrawable) {
          }

          @Override
          public void onPrepareLoad(Drawable placeHolderDrawable) {
          }
        };

        Log.d(TAG, "target " + target);
        Picasso.with(RssAdapter.this.context).load(
                items.get(globalPos).getThumbnail()).into(target);
      }
    });

    alert.show();

    // Calculate the starting and ending bounds for the zoomed-in image.
    // This step involves lots of math. Yay, math.
//        final Rect startBounds = new Rect();
//        final Rect finalBounds = new Rect();
//        final Point globalOffset = new Point();
//
//        // The start bounds are the global visible rectangle of the thumbnail,
//        // and the final bounds are the global visible rectangle of the container
//        // view. Also set the container view's offset as the origin for the
//        // bounds, since that's the origin for the positioning animation
//        // properties (X, Y).
//        thumbView.getGlobalVisibleRect(startBounds);
//        view1.findViewById(R.id.rss_item_rl)                   //  yahan p R.id.rss_item_rl dal ke as a debugger use kr dekhne ke lye ki konse image khan set hue hai ;p
//                .getGlobalVisibleRect(finalBounds, globalOffset);
//        startBounds.offset(-globalOffset.x, -globalOffset.y);
//        finalBounds.offset(-globalOffset.x, -globalOffset.y);
//
//        // Adjust the start bounds to be the same aspect ratio as the final
//        // bounds using the "center crop" technique. This prevents undesirable
//        // stretching during the animation. Also calculate the start scaling
//        // factor (the end scaling factor is always 1.0).
//        float startScale;
//        if ((float) finalBounds.width() / finalBounds.height()
//                > (float) startBounds.width() / startBounds.height()) {
//            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
//        } else {
//            // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height();
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.bottom += deltaHeight;
//        }
//
//        // Hide the thumbnail and show the zoomed-in view. When the animation
//        // begins, it will position the zoomed-in view in the place of the
//        // thumbnail.
//        thumbView.setAlpha(0f);
//        expandedImageView.setVisibility(View.VISIBLE);
//
//        // Set the pivot point for SCALE_X and SCALE_Y transformations
//        // to the top-left corner of the zoomed-in view (the default
//        // is the center of the view).
//        expandedImageView.setPivotX(0f);
//        expandedImageView.setPivotY(0f);
//
//        // Construct and run the parallel animation of the four translation and
//        // scale properties (X, Y, SCALE_X, and SCALE_Y).
//        AnimatorSet set = new AnimatorSet();
//        set
//                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
//                        startBounds.left, finalBounds.left))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
//                        startBounds.top, finalBounds.top))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
//                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
//                View.SCALE_Y, startScale, 1f));
//        set.setDuration(mShortAnimationDuration);
//        set.setInterpolator(new DecelerateInterpolator());
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mCurrentAnimator = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                mCurrentAnimator = null;
//            }
//        });
//        set.start();
//        mCurrentAnimator = set;
//
//        // Upon clicking the zoomed-in image, it should zoom back down
//        // to the original bounds and show the thumbnail instead of
//        // the expanded image.
//        final float startScaleFinal = startScale;
//
//        expandedImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurrentAnimator != null) {
//                    mCurrentAnimator.cancel();
//                }
//
//                // Animate the four positioning/sizing properties in parallel,
//                // back to their original values.
//                AnimatorSet set = new AnimatorSet();
//                set.play(ObjectAnimator
//                        .ofFloat(expandedImageView, View.X, startBounds.left))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.Y, startBounds.top))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_X, startScaleFinal))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_Y, startScaleFinal));
//                set.setDuration(mShortAnimationDuration);
//                set.setInterpolator(new DecelerateInterpolator());
//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//                });
//                set.start();
//                mCurrentAnimator = set;
//            }
//        });

  }

  private void rssTime() {

  }

  private String calcTime(long x) {
    String ret;
    x /= (1000 * 60);
    if (x > (1440)) {
      x /= 1440;
      ret = String.valueOf(x) + " Day";
    } else if (x > 60) {
      x /= 60;
      ret = String.valueOf(x) + " Hr.";
    } else {
      ret = String.valueOf(x) + " Min";
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
