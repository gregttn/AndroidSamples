package com.gregttn.datastorage.stores;

import android.content.Context;
import android.content.SharedPreferences;

import com.gregttn.datastorage.R;

public class SharedPreferencesDataStore implements DataStore {
    private final String KEY = "sample_key";
    SharedPreferences preferences;

    public SharedPreferencesDataStore(Context context) {
        String preferencesFile = context.getString(R.string.preferences_file);
        this.preferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
    }

    @Override
    public void save(String data) {
        preferences.edit()
                .putString(KEY, data)
                .commit();
    }

    @Override
    public String retrieve() {
        return preferences.getString(KEY, "");
    }

    @Override
    public void close() {}
}