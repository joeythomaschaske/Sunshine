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


public class DetailActivty extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_activty);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
            }
        }
        catch (Exception e){
            Log.e("DetailActivity Exception(onCreate)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try{
            getMenuInflater().inflate(R.menu.menu_detail_activty, menu);
        }
        catch (Exception e){
            Log.e("DetailActivity Exception(onCreateOptionsMenu)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = -1;

        try{
            id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
        }
        catch (Exception e){
            Log.e("DetailActivity Exception(onOptionsItemSelected" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
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
            View rootView = null;

            try{
                rootView = inflater.inflate(R.layout.fragment_detail_activty, container, false);
            }
            catch (Exception e){
                Log.e("DetailActivity Exception(onCreateView)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return rootView;
        }
    }
}
