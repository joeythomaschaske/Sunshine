package jpt3.com.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ForecastFragment extends Fragment {
    ArrayAdapter<String> listViewAdapter = null;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        } catch (Exception e){
            Log.e("ForecastFragment Exception(onCreate)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        try{
            inflater.inflate(R.menu.forecastfragment, menu);
        } catch (Exception e){
            Log.e("ForecastFragment Exception(onCreateOptionsMenu)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = -1;
        FetchWeatherTask weatherTask = null;

        try{
            id = item.getItemId();
            if (id == R.id.action_refresh) {
                weatherTask = new FetchWeatherTask();
                weatherTask.execute("53211");
                return true;
            }
        } catch (Exception e){
            Log.e("ForecastFragment Exception(onOptionsItemSelected)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<String> weekForcast = null;
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), listViewAdapter.getItem(position), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.e("ForecastFragment Exception(onCreateView)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
        return rootView;
    }
    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            int numDays = -1;
            final String BASE_URI_PARAM = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String COUNTRY_CODE_PARAM = ",USA";
            final String DAYS_PARAM = "cnt";
            final String FORMAT_PARAM = "mode";
            final String QUERY_PARAM = "q";
            final String UNITS_PARAM = "units";
            String forecastJsonStr = "";
            String format = "";
            String line = "";
            String units = "";
            BufferedReader reader = null;
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            StringBuffer buffer = null;
            String [] result = null;
            Uri builtUri = null;
            URL url = null;

            try {
                numDays = 7;
                format = "json";
                units = "metric";
                builtUri = Uri.parse(BASE_URI_PARAM).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0] +  COUNTRY_CODE_PARAM)
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .build();
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");
                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                result = getWeatherDataArray(forecastJsonStr);
                urlConnection.disconnect();
                reader.close();
            } catch (Exception e) {
                Log.e("ForecastFragment Exception(doInBackground) " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] result) {

            try{
                listViewAdapter.clear();
                for(String day: result){
                    listViewAdapter.add(day);
                }
            } catch (Exception e){
                Log.e("ForecastFragment Exception(onPostExecute) " + Thread.currentThread().getStackTrace()[3].getLineNumber(), e.getMessage(), e);
            }

        }

        private String[] getWeatherDataArray(String forecastJsonStr) throws JSONException {
            int index = -1;
            String day = "";
            String description = "";
            String highAndLow = "";
            JSONArray weatherArray = null;
            JSONObject dayJson = null;
            JSONObject forecastJson = null;
            JSONObject temperatureObject = null;
            JSONObject weatherObject = null;
            String[] results = null;

            try{
                forecastJson = new JSONObject(forecastJsonStr);
                weatherArray = forecastJson.getJSONArray("list");
                results = new String[7];
                for(index = 0; index < weatherArray.length(); ++index){
                    dayJson = weatherArray.getJSONObject(index);
                    day = getReadableDateString(dayJson.getLong("dt"));
                    weatherObject = dayJson.getJSONArray("weather").getJSONObject(0);
                    description = weatherObject.getString("main");
                    temperatureObject = dayJson.getJSONObject("temp");
                    highAndLow = formatHighLows(temperatureObject.getDouble("max"), temperatureObject.getDouble("min"));
                    results[index] = day + "-" + description + "-" + highAndLow;
                }
            }catch (Exception e){
                Log.e("ForecastFragment Exception(getWeatherDataArray) " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return results;
        }

        private String getReadableDateString(long time){
            String formatedDate = "";
            Date date = null;
            SimpleDateFormat format = null;

            try{
                date = new Date(time * 1000);
                format = new SimpleDateFormat("E, MMM d");
                formatedDate = format.format(date);
            }catch (Exception e){
                Log.e("ForecastFragment Exception(getReadableDateString) " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return formatedDate;
        }

        private String formatHighLows(double high, double low) {
            long roundedHigh = -1;
            long roundedLow = -1;
            String highLowStr = "";

            try{
                roundedHigh = Math.round(high);
                roundedLow = Math.round(low);
                highLowStr = roundedHigh + "/" + roundedLow;
            }catch (Exception e){
                Log.e("ForecastFragment Exception(formatHighLows) " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return highLowStr;
        }
    }
}