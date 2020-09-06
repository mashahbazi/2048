package com.example.my2048.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.VisibleForTesting;

public class SharedPreferencesHelper {
    private static SharedPreferencesHelper instance;

    private SharedPreferences sharedPreferences;

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    @VisibleForTesting
    public void mock(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("2048", 0);
    }

    public void setBestRecord(long bestRecord) {
        sharedPreferences.edit().putLong(Keys.BEST_RECORD, bestRecord).apply();
    }

    public long getBestRecord() {
        return sharedPreferences.getLong(Keys.BEST_RECORD, 0);
    }

    @VisibleForTesting
    public static class Keys {
        public static final String BEST_RECORD = "best_record";
    }
}
