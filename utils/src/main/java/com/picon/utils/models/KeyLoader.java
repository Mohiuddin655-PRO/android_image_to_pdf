package com.picon.utils.models;

import androidx.annotation.NonNull;

public class KeyLoader {

    @NonNull
    public final String mKey;
    public final Object mFinder;

    public KeyLoader(@NonNull String mKey, Object mFinder) {
        this.mKey = mKey;
        this.mFinder = mFinder;
    }
}
