package com.picon.utils.helpers;

import android.content.Context;

import androidx.annotation.NonNull;

public class DialogHelper {

    public final Context mContext;

    public DialogHelper(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    public interface OnDataChangeListener<T> {
        void onChange(@NonNull T t);
    }

}
