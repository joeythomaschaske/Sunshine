package jpt3.com.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ArrayList<String> weekForcast = null;
            ArrayAdapter<String> listViewAdapter = null;
            ListView listView = null;
            View rootView = null;

            try {
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                weekForcast = new ArrayList<>();
                weekForcast.add("Today-Sunny-88/63");
                weekForcast.add("Tomorrow-Foggy-70/46");
                weekForcast.add("Weds-Cloudy-72/63");
                weekForcast.add("Thurs-Rainy-64/51");
                weekForcast.add("Fri -Foggy-70/46");
                weekForcast.add("Sat-Sunny-76/68");
                listView = (ListView) rootView.findViewById(R.id.listview_forecast);
                listViewAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForcast);
                listView.setAdapter(listViewAdapter);
            }
            catch (Exception e) {
                Log.e("Sunshine Exception(onCreateView)" + Thread.currentThread().getStackTrace()[2].getLineNumber(), e.getMessage(), e);
            }
            return rootView;
        }
    }
}
