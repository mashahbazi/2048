package com.example.my2048.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.VisibleForTesting;

import com.example.my2048.modules.mainactivity.MainActivityViewModel;

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

    public void restLastState() {
        int rows = MainActivityViewModel.MAX_CELL_IN_ROW;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                editor.putInt(Keys.LAST_STATE + i + j, 0);
            }
        }
        editor.apply();
    }

    public void setLastStateByIndex(int value, int i, int j) {
        sharedPreferences.edit().putInt(Keys.LAST_STATE + i + j, value).apply();
    }

    public int getLastStateByIndex(int i, int j) {
        return sharedPreferences.getInt(Keys.LAST_STATE + i + j, 0);
    }

    public void setLastRecord(long record) {
        sharedPreferences.edit().putLong(Keys.LAST_RECORD, record).apply();
    }

    public long getLastRecord() {
        return sharedPreferences.getLong(Keys.LAST_RECORD, 0L);
    }

    @VisibleForTesting
    public static class Keys {
        public static final String BEST_RECORD = "best_record";
        public static final String LAST_STATE = "last_state";
        public static final String LAST_RECORD = "last_state";
    }
}
