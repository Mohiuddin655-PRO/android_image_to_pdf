package com.picon.utils.helpers;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.picon.utils.response.Response;

public class BackgroundTaskHelper {

    public final LifecycleOwner mLifecycleOwner;
    public final ViewModelStoreOwner mViewModelStoreOwner;
    public final OnBackgroundTaskListener mListener;

    public BackgroundTaskHelper(@NonNull Activity mActivity, @NonNull OnBackgroundTaskListener mListener) {
        this.mListener = mListener;
        this.mLifecycleOwner = (LifecycleOwner) mActivity;
        this.mViewModelStoreOwner = (ViewModelStoreOwner) mActivity;
    }

    public static BackgroundTaskHelper getInstance(@NonNull Activity mActivity, @NonNull OnBackgroundTaskListener mListener) {
        return new BackgroundTaskHelper(mActivity, mListener);
    }

    @NonNull
    public ViewModelStoreOwner getViewModelStoreOwner() {
        return mViewModelStoreOwner;
    }

    @NonNull
    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    @NonNull
    public OnBackgroundTaskListener getListener() {
        return mListener;
    }

    public interface OnBackgroundTaskListener {

        void onCompleted(@NonNull Response<?> response);

        void onCanceled(@NonNull Response<?> response);

        void onError(@NonNull String message);

    }
}
