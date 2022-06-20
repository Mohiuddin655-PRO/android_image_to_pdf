package com.picon.utils.models;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class KeyValue {

    @NonNull
    public final String mKey;
    @NonNull
    public final Object mValue;
    @NonNull
    public DataType mType;

    public KeyValue(@NonNull String mKey, @NonNull Object mValue) {
        this.mKey = mKey;
        this.mValue = mValue;
        this.mType = getType();
    }

    @NonNull
    public String getKey() {
        return mKey;
    }

    @NonNull
    public Object getValue() {
        return mValue;
    }

    @Nullable
    public <T> T getValue(@NonNull Class<T> cls) {
        return cls.cast(mValue);
    }

    @NonNull
    private DataType getType() {
        if (mValue instanceof Boolean) {
            return DataType.BOOLEAN;
        } else if (mValue instanceof Bundle) {
            return DataType.BUNDLE;
        } else if (mValue instanceof Byte) {
            return DataType.BYTE;
        } else if (mValue instanceof Character) {
            return DataType.CHARACTER;
        } else if (mValue instanceof Double) {
            return DataType.DOUBLE;
        } else if (mValue instanceof Float) {
            return DataType.FLOAT;
        } else if (mValue instanceof Integer) {
            return DataType.INTEGER;
        } else if (mValue instanceof Long) {
            return DataType.LONG;
        } else if (mValue instanceof Short) {
            return DataType.SHORT;
        } else if (mValue instanceof String) {
            return DataType.STRING;
        } else if (mValue instanceof Parcelable) {
            return DataType.PARCELABLE;
        } else if (mValue instanceof String[]) {
            return DataType.STRING_ARRAY;
        } else if (mValue instanceof Parcelable[]) {
            return DataType.PARCELABLE_ARRAY;
        } else if (mValue instanceof ArrayList<?>) {
            return DataType.ARRAY_LIST;
        } else {
            return DataType.SERIALIZABLE;
        }
    }

    public KeyValue setType(@NonNull DataType type) {
        this.mType = type;
        return this;
    }

}
