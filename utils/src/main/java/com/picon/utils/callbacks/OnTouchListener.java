package com.picon.utils.callbacks;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public interface OnTouchListener extends View.OnTouchListener {

    @Override
    default boolean onTouch(View view, MotionEvent event) {
        if (view != null) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_BUTTON_PRESS:
                    return onButtonPress(view);
                case MotionEvent.ACTION_BUTTON_RELEASE:
                    return onButtonRelease(view);
                case MotionEvent.ACTION_CANCEL:
                    return onCancel(view);
                case MotionEvent.ACTION_DOWN:
                    return onDown(view);
                case MotionEvent.ACTION_HOVER_ENTER:
                    return onHoverEnter(view);
                case MotionEvent.ACTION_HOVER_EXIT:
                    return onHoverExit(view);
                case MotionEvent.ACTION_HOVER_MOVE:
                    return onHoverMove(view);
                case MotionEvent.ACTION_MOVE:
                    return onMove(view, x, y);
                case MotionEvent.ACTION_OUTSIDE:
                    return onOutside(view);
                case MotionEvent.ACTION_POINTER_DOWN:
                    return onPointerDown(view);
                case MotionEvent.ACTION_POINTER_UP:
                    return onPointerUp(view);
                case MotionEvent.ACTION_SCROLL:
                    return onScroll(view);
                case MotionEvent.ACTION_UP:
                    return onUp(view);
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    default boolean onButtonPress(@NonNull View view) {
        return false;
    }

    default boolean onButtonRelease(@NonNull View view) {
        return false;
    }

    default boolean onCancel(@NonNull View view) {
        return false;
    }

    default boolean onDown(@NonNull View view) {
        return false;
    }

    default boolean onHoverEnter(@NonNull View view) {
        return false;
    }

    default boolean onHoverExit(@NonNull View view) {
        return false;
    }

    default boolean onHoverMove(@NonNull View view) {
        return false;
    }

    default boolean onMove(@NonNull View view, int x, int y) {
        return false;
    }

    default boolean onOutside(@NonNull View view) {
        return false;
    }

    default boolean onPointerDown(@NonNull View view) {
        return false;
    }

    default boolean onPointerUp(@NonNull View view) {
        return false;
    }

    default boolean onScroll(@NonNull View view) {
        return false;
    }

    default boolean onUp(@NonNull View view) {
        return false;
    }
}