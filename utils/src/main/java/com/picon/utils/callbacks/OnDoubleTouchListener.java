package com.picon.utils.callbacks;

import android.view.GestureDetector;
import android.view.MotionEvent;

public interface OnDoubleTouchListener extends GestureDetector.OnDoubleTapListener {

    @Override
    default boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    default boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    default boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
