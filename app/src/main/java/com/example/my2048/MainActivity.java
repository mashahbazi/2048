package com.example.my2048;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CustomTextView[][] textViews = new CustomTextView[4][4];
    private SingleObservable<Long> sumOfNum = new SingleObservable<>(0L);
    private TextView sumTV;

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
        findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.restart_iv).setOnClickListener(this);

        sumTV = findViewById(R.id.sum_tv);
        sumOfNum.addObserver((o, arg) -> sumTV.setText(arg.toString()));

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
                swipeType = Constants.SwipeType.TOP;
                singleObservable.setField(0);
                swipeTop(singleObservable);
            }

            @Override
            void onDownSwipe() {
                swipeType = Constants.SwipeType.DOWN;
                singleObservable.setField(0);
                swipeDown(singleObservable);
            }

            @Override
            void addNewNum() {
                MainActivity.this.addNewNum(swipeType);
            }
        };
    }

    private void swipeLeft(SingleObservable<Integer> singleObservable) {
        for (int i = 0; i < 4; i++) {
            CustomTextView[] lineTextViews = this.textViews[i];
            new Handler().post(() -> onSwipe(lineTextViews, singleObservable));
        }
    }

    private void swipeTop(SingleObservable<Integer> singleObservable) {
        for (int i = 0; i < 4; i++) {
            CustomTextView[] lineTextViews = {this.textViews[0][i], this.textViews[1][i], this.textViews[2][i], this.textViews[3][i]};
            new Handler().post(() -> onSwipe(lineTextViews, singleObservable));
        }
    }

    private void swipeDown(SingleObservable<Integer> singleObservable) {
        for (int i = 0; i < 4; i++) {
            CustomTextView[] lineTextViews = {this.textViews[3][i], this.textViews[2][i], this.textViews[1][i], this.textViews[0][i]};
            new Handler().post(() -> onSwipe(lineTextViews, singleObservable));
        }
    }

    private void swipeRight(SingleObservable<Integer> singleObservable) {
        for (int i = 0; i < 4; i++) {
            CustomTextView[] lineTextViews = {this.textViews[i][3], this.textViews[i][2], this.textViews[i][1], this.textViews[i][0]};
            new Handler().post(() -> onSwipe(lineTextViews, singleObservable));
        }
    }

    private void onSwipe(CustomTextView[] lineTextView, SingleObservable<Integer> singleObservable) {
        for (int j = 0; j < 3; j++) {
            if (lineTextView[j].getCurrentNum() == 0) continue;
            for (int k = j + 1; k < 4; k++) {
                if (lineTextView[k].getCurrentNum() == 0) continue;
                Boolean b = sumViews(lineTextView[j], lineTextView[k]);
                if (b != null && !b) k = 4;
            }
        }
        for (int v = 0; v < 3; v++) {
            int lastEmpty = lineTextView[0].getCurrentNum() == 0 ? 0 : -1;
            for (int j = 1; j < 4; j++) {
                lastEmpty = checkChange(lineTextView, lastEmpty, j);
            }
        }
        singleObservable.setField(singleObservable.getField() + 1);
    }

    private int checkChange(CustomTextView[] textViews, int lastEmpty, int j) {
        if (textViews[j].getCurrentNum() == 0) {
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
        int num = first.getCurrentNum();
        first.setInt(second.getCurrentNum());
        second.setInt(num);
    }

    private Boolean sumViews(CustomTextView first, CustomTextView second) {
        if (first.getCurrentNum() == 0 || second.getCurrentNum() == 0) return null;
        if (first.getCurrentNum() == second.getCurrentNum()) {
            first.setInt(first.getCurrentNum() * 2);
            second.setInt(0);
            sumOfNum.setField(sumOfNum.getField() + first.getCurrentNum());
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
                    if (customTextView.getCurrentNum() == 0)
                        customTextViews.add(customTextView);
                }
                break;
            case Constants.SwipeType.LEFT:
                for (int i = 0; i < 4; i++) {
                    CustomTextView customTextView = textViews[i][3];
                    if (customTextView.getCurrentNum() == 0)
                        customTextViews.add(customTextView);
                }
                break;
            case Constants.SwipeType.TOP:
                for (int i = 0; i < 4; i++) {
                    CustomTextView customTextView = textViews[3][i];
                    if (customTextView.getCurrentNum() == 0)
                        customTextViews.add(customTextView);
                }
                break;
            case Constants.SwipeType.DOWN:
                for (int i = 0; i < 4; i++) {
                    CustomTextView customTextView = textViews[0][i];
                    if (customTextView.getCurrentNum() == 0)
                        customTextViews.add(customTextView);
                }
                break;
        }
        if (customTextViews.size() != 0)
            customTextViews.get(MathUtils.getInstance().getRoundInt(customTextViews.size() - 1)).addNewNum();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                for (CustomTextView[] lineTextView : textViews) {
                    for (CustomTextView customTextView : lineTextView) {
                        customTextView.goBackNums();
                    }
                }
                break;
            case R.id.restart_iv:
                for (CustomTextView[] lineTextView : textViews) {
                    for (CustomTextView customTextView : lineTextView) {
                        customTextView.setInt(0);
                        this.sumOfNum.setField(0L);
                    }
                }
                startGame();
                break;
        }
    }
}
