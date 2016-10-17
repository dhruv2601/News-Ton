package dhruv.newsfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import dhruv.newsfeed.R;
import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class AppIntro extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.black)
                        .buttonsColor(dhruv.newsfeed.R.color.red)
                        .image(R.drawable.appintrosixone)
                        .title("OFFLINE MODE")
                        .description(" Open App In Offline Mode And Enjoy Features ")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AppIntro.this, "NEWS FEED WITH LOVE", Toast.LENGTH_SHORT).show();
                    }
                }, "NEWS FEED "));

//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.black)
//                        .buttonsColor(R.color.red)
//                        .image(R.drawable.appintrofiveone)
//                        .title("READ FULL ARTICLE")
//                        .description("Click on the article OR the Read Button for full article")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(AppIntro.this, "NEWS FEED WITH LOVE", Toast.LENGTH_SHORT).show();
//                    }
//                }, "NEWS FEED"));
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.black)
                        .buttonsColor(R.color.red)
                        .image(R.drawable.appintrooneeone)
                        .title("FULL IMAGE")
                        .description("Touch the image to view full Image")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AppIntro.this, "NEWS FEED WITH LOVE", Toast.LENGTH_SHORT).show();
                    }
                }, "NEWS FEED"));

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.black)
                        .buttonsColor(R.color.red)
                        .image(R.drawable.appintrotwoone)
                        .title("Read From NewsPapers")
                        .description("Touch On the newspaper icon and enjoy the real newspaper")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AppIntro.this, "NEWS FEED WITH LOVE", Toast.LENGTH_SHORT).show();
                    }
                }, "NEWS FEED"));

//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.black)
//                        .buttonsColor(R.color.red)
//                        .image(R.drawable.appintrothreeone)
//                        .title("SAVED LIST")
//                        .description("Touch the add icon and make a list of news to read later")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(AppIntro.this, "NEWS FEED WITH LOVE", Toast.LENGTH_SHORT).show();
//                    }
//                }, "NEWS FEED"));

//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.black)
//                        .buttonsColor(R.color.red)
//                        .image(R.drawable.appintrofourone)
//                        .title("LISTEN TO ALL NEWS")
//                        .description("Touch the play button to play all news in order and touch again to stop")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(AppIntro.this, "NEWS FEED WITH LOVE", Toast.LENGTH_SHORT).show();
//                    }
//                }, "NEWS FEED"));


    }
}