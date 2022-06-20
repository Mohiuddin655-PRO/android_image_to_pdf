package com.picon.utils.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.picon.utils.loaders.ScreenLoader;

public abstract class FragmentHelper extends Fragment {

    private static final String DEFAULT_KEY = "default_key";

    public int mPosition;
    public Activity mActivity;
    public Context mContext;
    public LifecycleOwner mLifecycleOwner;
    public Bundle mBundle;
    public String mUid;
    public String mParentDocumentId, mCurrentDocumentId;

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = requireActivity();
        mContext = requireContext();
        mLifecycleOwner = getViewLifecycleOwner();
        mBundle = getArguments() != null ? getArguments() : new Bundle();
        mUid = mBundle.getString(ScreenLoader.UID);
        mParentDocumentId = mBundle.getString(ScreenLoader.PARENT_DOCUMENT_ID);
        mCurrentDocumentId = mBundle.getString(ScreenLoader.CURRENT_DOCUMENT_ID);
        onLoadInfo();
        onCheckInfo();
        return onInit(inflater, container);
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onDeclare();
        onDeclareView();
        onListener();
        onReplacer();
        onRetriever();
        onViewAnimation();
    }

    public abstract View onInit(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    public void onCheckInfo() {
    }

    public void onLoadInfo() {
    }

    public void onInit() {
    }

    public void onDeclare() {
    }

    public void onDeclareView() {
    }

    public void onListener() {
    }

    public final void onRefresh() {
        onRefresh(DEFAULT_KEY);
        onRetriever();
    }

    @CallSuper
    public void onRefresh(@NonNull String key) {
        onRetriever(key);
    }

    public void onReplacer() {

    }

    public final void onRetriever() {
        onRetriever(DEFAULT_KEY);
    }

    public void onRetriever(@NonNull String key) {
    }

    public void onViewAnimation() {
    }

}
