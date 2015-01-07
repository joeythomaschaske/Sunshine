package jpt3.com.sunshine;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;


public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));
        } catch (Exception e){
            Log.e("SettingsActivity Exception(onCreate)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
    }

    private void bindPreferenceSummaryToValue(Preference preference) {

        try {
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
        } catch (Exception e){
            Log.e("SettingsActivity Exception(bindPreferencesSummaryToValue)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = "";

        try {
            stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
        } catch (Exception e){
            Log.e("SettingsActivity Exception(onPreferenceChange)" + Thread.currentThread().getStackTrace()[1].getLineNumber(), e.getMessage(), e);
        }
        return true;
    }

}