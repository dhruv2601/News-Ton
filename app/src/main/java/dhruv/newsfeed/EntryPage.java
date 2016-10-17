package dhruv.newsfeed;

/**
 * Created by dhruv on 29/8/16.
 */
import android.os.Bundle;
import dhruv.newsfeed.R;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EntryPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("test1","tab1pass");
        return inflater.inflate(R.layout.entry_layout, container, false);
    }
}