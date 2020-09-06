package com.example.my2048.modules.mainactivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.my2048.helpers.SharedPreferencesHelper;

public class MainActivityFactory extends ViewModelProvider.NewInstanceFactory {
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public MainActivityFactory(SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainActivityViewModel.class) {
            return (T) new MainActivityViewModel(sharedPreferencesHelper);
        }
        return super.create(modelClass);
    }
}
