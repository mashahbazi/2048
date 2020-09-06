package com.example.my2048.modules.mainactivity;

import android.os.Bundle;
import android.view.GestureDetector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.my2048.R;
import com.example.my2048.databinding.ActivityMainBinding;
import com.example.my2048.helpers.CustomGestureDetector;
import com.example.my2048.helpers.SharedPreferencesHelper;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityFactory factory = new MainActivityFactory(SharedPreferencesHelper.getInstance(this));
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        ActivityMainBinding binding=DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        init();
    }

    private void init() {
        GestureDetector gestureDetector = new GestureDetector(this, new CustomGestureDetector(viewModel));
        findViewById(R.id.base_parent).setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            v.performClick();
            return false;
        });
    }
}
