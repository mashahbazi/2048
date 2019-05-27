package com.example.my2048;

import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Observer;

public abstract class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    int swipeType;
    final SingleObservable<Integer> singleObservable;

    CustomGestureDetector() {
        Observer o = (o1, arg) -> {
            if ((int) arg == 4)
                addNewNum();
        };
        singleObservable = new SingleObservable<>(0);
        singleObservable.addObserver(o);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY())) {
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                onLeftSwipe();
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                onRightSwipe();

        } else {
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
                onTopSwipe();
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
                onDownSwipe();

        }
        return false;
    }

    abstract void onLeftSwipe();

    abstract void onRightSwipe();

    abstract void onTopSwipe();

    abstract void onDownSwipe();

    abstract void addNewNum();
}

