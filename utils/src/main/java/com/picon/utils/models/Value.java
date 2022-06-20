package com.picon.utils.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Value<T> {

    @NonNull
    public final String mScreenName;
    @NonNull
    public final DataType mDataType;
    @Nullable
    public T mData;

    public Value(@NonNull DataType mDataType, @NonNull String mScreenName) {
        this.mScreenName = mScreenName;
        this.mDataType = mDataType;
    }

    public Value(@NonNull DataType mDataType, @NonNull String mScreenName, @Nullable T mData) {
        this(mDataType, mScreenName);
        this.mData = mData;
    }

}
