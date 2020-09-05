package com.example.my2048.helpers;

import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Observer;

public class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 60;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    ActionListener actionListener;

    public CustomGestureDetector(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY())) {
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                actionListener.onLeftSwipe();
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                actionListener.onRightSwipe();

        } else {
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
                actionListener.onTopSwipe();
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
                actionListener.onDownSwipe();

        }
        return false;
    }

    public interface ActionListener {
        void onLeftSwipe();

        void onRightSwipe();

        void onTopSwipe();

        void onDownSwipe();
    }
}

