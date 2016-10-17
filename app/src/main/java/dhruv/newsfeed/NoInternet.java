package dhruv.newsfeed;

import android.content.Intent;
import dhruv.newsfeed.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoInternet extends AppCompatActivity {

    Button reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        reload = (Button) findViewById(R.id.reload);
//        reload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(NoInternet.this, TopicSelector.class);
//                startActivity(i);
//            }
//        });
    }
}