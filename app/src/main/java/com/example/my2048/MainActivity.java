package com.example.my2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
        textViews[i][j].addNewNum();
        i = MathUtils.getInstance().getRoundInt(3);
        j = MathUtils.getInstance().getRoundInt(3);
        textViews[i][j].addNewNum();
    }

    private CustomGestureDetector getCustomGestureDetector() {
        return new CustomGestureDetector() {
            @Override
            void onLeftSwipe() {
                swipeType = Constants.SwipeType.LEFT;
                singleObservable.setField(0);
                swipeLeft(singleObservable);
            }

            @Override
            void onRightSwipe() {
                swipeType = Constants.SwipeType.RIGHT;
                singleObservable.setField(0);
                swipeRight(singleObservable);
            }

            @Override
            void onTopSwipe() {

            }

            @Override
            void onDownSwipe() {
            }

            @Override
            void addNewNum() {
                MainActivity.this.addNewNum(swipeType);
            }
        };
    }

    private void swipeLeft(SingleObservable<Integer> singleObservable) {
        for (int i = 0; i < 4; i++) {
            CustomTextView[] textViews = this.textViews[i];
            new Handler().post(() -> {
                for (int v = 0; v < 3; v++) {
                    int lastEmpty = textViews[0].getLastNum() == 0 ? 0 : -1;
                    for (int j = 1; j < 4; j++) {
                        lastEmpty = checkChange(textViews, lastEmpty, j);
                    }
                }
                for (int j = 0; j < 3; j++) {
                    if (textViews[j].getLastNum() == 0) continue;
                    for (int k = j + 1; k < 4; k++) {
                        if (textViews[k].getLastNum() == 0) continue;
                        Boolean b = sumViews(textViews[j], textViews[k]);
                        if (b != null && !b) k = 4;
                    }
                }
                singleObservable.setField(singleObservable.getField() + 1);
            });
        }
    }


    private void swipeRight(SingleObservable<Integer> singleObservable) {
        for (int i = 0; i < 4; i++) {
            CustomTextView[] textViews = this.textViews[i];
            new Handler().post(() -> {
                for (int v = 0; v < 3; v++) {
                    int lastEmpty = textViews[3].getLastNum() == 0 ? 3 : -1;
                    for (int j = 2; j >= 0; j--) {
                        lastEmpty = checkChange(textViews, lastEmpty, j);
                    }
                }
                for (int j = 3; j >= 0; j--) {
                    if (textViews[j].getLastNum() == 0) continue;
                    for (int k = j - 1; k >= 0; k--) {
                        if (textViews[k].getLastNum() == 0) continue;
                        Boolean b = sumViews(textViews[j], textViews[k]);
                        if (b != null && !b) k = -1;
                    }
                }
                singleObservable.setField(singleObservable.getField() + 1);
            });
        }
    }

    private int checkChange(CustomTextView[] textViews, int lastEmpty, int j) {
        if (textViews[j].getLastNum() == 0) {
            if (lastEmpty == -1) lastEmpty = j;
            return lastEmpty;
        }
        if (lastEmpty != -1) {
            changeViewsText(textViews[lastEmpty], textViews[j]);
            lastEmpty = j;
        }
        return lastEmpty;
    }

    private void changeViewsText(CustomTextView first, CustomTextView second) {
        int num = first.getLastNum();
        first.setText(second.getLastNum(), 0);
        second.setText(num, 0);
    }

    private Boolean sumViews(CustomTextView first, CustomTextView second) {
        if (first.getLastNum() == 0 || second.getLastNum() == 0) return null;
        if (first.getLastNum() == second.getLastNum()) {
            first.setText(first.getLastNum() * 2, 0);
            second.setText(0, 0);
            return true;
        }
        return false;
    }

    private void addNewNum(int swipeType) {
        List<CustomTextView> customTextViews = new ArrayList<>();
        switch (swipeType) {
            case Constants.SwipeType.RIGHT:
                for (int i = 0; i < 4; i++) {
                    CustomTextView customTextView = textViews[i][0];
                    if (customTextView.getLastNum() == 0)
                        customTextViews.add(customTextView);
                }
                break;
            case Constants.SwipeType.LEFT:
                for (int i = 0; i < 4; i++) {
                    CustomTextView customTextView = textViews[i][3];
                    if (customTextView.getLastNum() == 0)
                        customTextViews.add(customTextView);
                }
                break;
        }
        customTextViews.get(MathUtils.getInstance().getRoundInt(customTextViews.size() - 1)).addNewNum();
    }
}
