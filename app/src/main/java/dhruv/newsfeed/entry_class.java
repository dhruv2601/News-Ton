package dhruv.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import dhruv.newsfeed.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dhruv on 11/9/16.
 */
public class entry_class extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static Context context;
    public static String text;
    public static long currTime;

    de.hdodenhof.circleimageview.CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_page);

        context = entry_class.this;

        currTime = SystemClock.uptimeMillis();
        circleImageView = (CircleImageView) findViewById(R.id.img);
        TextView txt = (TextView) findViewById(R.id.news);
        TextView txt1 = (TextView) findViewById(R.id.feed);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movers);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        animation1.setRepeatCount(Animation.INFINITE);
        circleImageView.startAnimation(animation1);
        txt1.startAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i =  new Intent(entry_class.this, MainActivity.class);
                startActivity(i);
            }
        }, 3500);

    }
}