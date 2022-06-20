package com.picon.utils.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class IntentData implements Serializable {

    @Nullable
    private final Object mData;
    @NonNull
    private final ArrayList<?> mList;

    public IntentData(@NonNull ArrayList<?> list) {
        this.mData = list.size() > 0 ? list.get(0) : null;
        this.mList = list;
    }

    public IntentData(@Nullable Object data) {
        this.mData = data;
        this.mList = new ArrayList<>();
    }

    public boolean isExist() {
        return getData() != null;
    }

    public boolean isExists() {
        return mList.size() > 0;
    }

    public <T> boolean forData(@NonNull Class<T> cls) {
        return isExists() && cls.isInstance(getData());
    }

    public <T> boolean forList(@NonNull Class<T> cls) {
        return isExists() && cls.isInstance(mList.get(0));
    }

    @Nullable
    public Object getData() {
        return mData != null ? mData : mList.size() > 0 ? mList.get(0) : null;
    }

    @Nullable
    public <T> T getData(@NonNull Class<T> cls) {
        return getData() != null ? cls.cast(getData()) : null;
    }

    @NonNull
    public <T> ArrayList<T> getList(@NonNull Class<T> cls) {
        final ArrayList<T> list = new ArrayList<>();
        if (mList.size() > 0) {
            for (Object value : mList) {
                if (cls.isInstance(value)) {
                    list.add(cls.cast(value));
                }
            }
        }
        return list;
    }

}
