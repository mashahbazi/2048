package com.example.my2048;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CustomTextView[][] textViews = new CustomTextView[4][4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startGame();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        GestureDetector gestureDetector = new GestureDetector(this, getCustomGestureDetector());
        findViewById(R.id.base_parent).setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return false;
        });

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                textViews[i][j] = findViewById(getResources().getIdentifier("tv_" + i + "_" + j, "id", getPackageName()));
            }
        }
    }

    private void startGame() {
        int i = MathUtils.getInstance().getRoundInt(3), j = MathUtils.getInstance().getRoundInt(3);
        textViews[i][j].setText(2, 0);
        i = MathUtils.getInstance().getRoundInt(3);
        j = MathUtils.getInstance().getRoundInt(3);
        textViews[i][j].setText(4, 0);
    }

    private CustomGestureDetector getCustomGestureDetector() {
        return new CustomGestureDetector() {
            @Override
            void onLeftSwipe() {
                int a = 0;
            }

            @Override
            void onRightSwipe() {
                int a = 0;

            }

            @Override
            void onTopSwipe() {

            }

            @Override
            void onDownSwipe() {
            }
        };
    }
}
