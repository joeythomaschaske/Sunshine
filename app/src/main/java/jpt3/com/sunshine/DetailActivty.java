package jpt3.com.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailActivty extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_activty);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.container, new DetailFragment()).commit();
            }
        }
        catch (Exception e){
            Log.e("DetailActivity Exception(onCreate)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = null;

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
                startActivity(new Intent(this, SettingsActivity.class));
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
    public static class DetailFragment extends Fragment {

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String recievedMessage = "";
            Intent recievedIntent = null;
            TextView detailTextView = null;
            View rootView = null;

            try{
                rootView = inflater.inflate(R.layout.fragment_detail_activty, container, false);
                recievedIntent = getActivity().getIntent();
                recievedMessage = recievedIntent.getStringExtra(Intent.EXTRA_TEXT);
                detailTextView = (TextView) rootView.findViewById(R.id.detail_text_view);
                detailTextView.setText(recievedMessage);
            }
            catch (Exception e){
                Log.e("DetailFragment Exception(onCreateView)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return rootView;
        }

            @Override
            public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
                MenuItem menuItem = null;
                ShareActionProvider shareActionProvider = null;

                try{
                    inflater.inflate(R.menu.detailfragment, menu);
                    menuItem = menu.findItem(R.id.action_share);
                    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
                    shareActionProvider.setShareIntent(createShareForecastIntent());
                } catch (Exception e){
                    Log.e("DetailFragment Exception(onCreateOptionsMenu)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
                }
            }

        private Intent createShareForecastIntent(){
            Intent shareIntent = null;
            Intent recievedIntent = null;

            try{
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("text/plain");
                recievedIntent = getActivity().getIntent();
                shareIntent.putExtra(Intent.EXTRA_TEXT, recievedIntent.getStringExtra(Intent.EXTRA_TEXT) + " #SunshineApp");

            } catch(Exception e){
                Log.e("DetailFragment Exception(createShareForecastIntent)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }

            return shareIntent;
        }
    }
}
