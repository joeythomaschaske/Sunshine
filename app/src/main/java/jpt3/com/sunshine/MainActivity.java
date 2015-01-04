package jpt3.com.sunshine;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ForecastFragment())
                        .commit();
            }
        } catch (Exception e){
            Log.e("MainActivity Exception(onCreate)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try{
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } catch (Exception e){
            Log.e("MainActivity Exception(onCreateOptionsMenu)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = -1;

        try{
            id = item.getItemId();
            if (id == R.id.action_settings)
                return true;
        } catch (Exception e){
            Log.e("MainActivity Exception(onCreateOptionsMenu)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
        return super.onOptionsItemSelected((item));
    }
}
