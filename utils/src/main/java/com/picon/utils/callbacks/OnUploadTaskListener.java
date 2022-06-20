package com.picon.utils.callbacks;

import android.view.View;

import androidx.annotation.NonNull;

import com.picon.utils.constains.ErrorCode;

public interface OnUploadTaskListener<T, RD> {

    void onUploadListener(@NonNull T t, int index, @NonNull FirebaseStorageCallback.OnCallbackResponseListener<RD> listener);

    default void onUploadCompleted(@NonNull T t, @NonNull String url, int index) {
    }

    default void onUploadFailed(@NonNull T t, int index, @NonNull Exception e, @ErrorCode int errorCode) {
    }

    default boolean onUploadCancelRequest(@NonNull View view, int index) {
        return view.isActivated();
    }
}
