package dhruv.newsfeed;

import android.widget.Button;

/**
 * Created by dhruv on 7/8/16.
 */
public class RssItem {
    private String title;
    private String link;
    private String date;
    private String category;
    private String thumbnail;

    public RssItem(String title, String link, String date, String category, String thumbnail) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}