package com.example.my2048.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String BEST_RECORD = "best_record";
    private static SharedPreferencesHelper instance;

    private SharedPreferences sharedPreferences;

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("2048", 0);
    }

    public void setBestRecord(long bestRecord) {
        sharedPreferences.edit().putLong(BEST_RECORD, bestRecord).apply();
    }

    public long getBestRecord() {
        return sharedPreferences.getLong(BEST_RECORD, 0);
    }
}
