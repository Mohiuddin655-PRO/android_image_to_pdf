package com.picon.utils.helpers;

import android.os.Handler;

public abstract class RunnableHelper {

    private final long duration;
    private final int counter;
    private int index;

    public RunnableHelper() {
        this(3000, 1);
    }

    public RunnableHelper(long duration) {
        this(duration, 1);
    }

    public RunnableHelper(long duration, int counter) {
        this(duration, counter, false);
    }

    public RunnableHelper(long duration, int counter, boolean beforeLoad) {
        this.duration = duration;
        this.counter = counter;
        if (beforeLoad) {
            beforeRun();
        } else {
            run();
        }
    }

    private void run() {
        new Handler().postDelayed(() -> {
            onLoad(index);
            index++;
            if (index < counter) {
                run();
            } else {
                onCompleted();
            }
        }, duration);
    }

    private void beforeRun() {
        onLoad(index);
        index++;
        if (index < counter) {
            run();
        } else {
            onCompleted();
        }
    }

    public boolean isCompleted() {
        return counter == index + 1;
    }

    public int getCounter() {
        return counter;
    }

    public int getIndexingSize() {
        return index + 1;
    }

    public int getInvertedIndex() {
        return (int) Math.max(getCounter() - getIndexingSize(), 0);
    }

    public abstract void onLoad(int index);

    public void onCompleted() {
    }

}