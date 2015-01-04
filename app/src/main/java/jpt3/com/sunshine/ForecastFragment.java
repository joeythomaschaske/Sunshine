package jpt3.com.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFragment extends Fragment {
    public ForecastFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<String> weekForcast = null;
        ArrayAdapter<String> listViewAdapter = null;
        ListView listView = null;
        View rootView = null;
        String forecastJsonStr = "";

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
            new FetchWeatherTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=53211,USA&units=metric&cnt=7&mode=json", null, forecastJsonStr);
        }
        catch (Exception e) {
            Log.e("Sunshine Exception(onCreateView)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
        return rootView;
    }
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            StringBuffer buffer = null;
            String forecastJsonStr = "";
            BufferedReader reader = null;
            String line = "";

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Exception e) {
                Log.e("Sunshine Exception(doInBackground) " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
            }
            return forecastJsonStr;
        }

    }
}