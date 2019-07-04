package com.example.android.clamps.mybestmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import android.support.v7.widget.RecyclerView;

public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {
    private static final String TAG = SettingsFragment.class.getSimpleName();
    public static final String KEY_PREF_LIST = "movie_orrder";
    SharedPreferences sharedPreferences;
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.pref_main,s);
        onSharedPreferenceChanged(null, "");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        onSharedPreferenceChanged(sharedPreferences, getString(R.string.movies_categories_key));


    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

           Preference preference=findPreference(key);
           if (preference instanceof ListPreference){
               ListPreference listPreference = (ListPreference) preference;
               int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
               if (prefIndex >= 0) {
                   preference.setSummary(listPreference.getEntries()[prefIndex]);


           }
           else {
                   preference.setSummary(sharedPreferences.getString(key, ""));
           }
           }


    }
}
