package com.picon.utils.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.picon.utils.builders.IntentBuilder;
import com.picon.utils.loaders.ScreenLoader;

public abstract class ActivityHelper extends AppCompatActivity {

    private static final String DEFAULT_KEY = "default_key";

    public Activity mActivity;
    public Context mContext;
    public Bundle mBundle;
    public IntentBuilder mIntent;
    public String mUid;
    public String mParentDocumentId;
    public String mCurrentDocumentId;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ScreenLoader.Animation.slideRight(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
        mIntent = IntentBuilder.getInstance(getIntent());
        onLoadInfo();
        onCheckInfo();
        onInit();
        onDeclare();
        onListener();
        onReplacer();
        onResponse();
        onRetriever();
    }

    @CallSuper
    public void onCheckInfo() {
        mBundle = mBundle != null ? mBundle : new Bundle();
        mBundle.putString(ScreenLoader.UID, mUid);
        mBundle.putString(ScreenLoader.PARENT_DOCUMENT_ID, mParentDocumentId);
        mBundle.putString(ScreenLoader.CURRENT_DOCUMENT_ID, mCurrentDocumentId);
    }

    @CallSuper
    public void onLoadInfo() {
        mBundle = mIntent.getBundleExtra(ScreenLoader.BUNDLE);
        mUid = mIntent.getStringExtra(ScreenLoader.UID);
        mParentDocumentId = mIntent.getStringExtra(ScreenLoader.PARENT_DOCUMENT_ID);
        mCurrentDocumentId = mIntent.getStringExtra(ScreenLoader.CURRENT_DOCUMENT_ID);
    }

    public void onInit() {
    }

    public void onDeclare() {
    }

    public void onListener() {
    }

    public final void onRefresh() {
        onRefresh(DEFAULT_KEY);
        onRetriever();
    }

    public void onRefresh(@NonNull String key) {
    }

    public void onReplacer() {
    }

    public void onResponse() {
    }

    public final void onRetriever() {
        onRetriever(DEFAULT_KEY);
    }

    public void onRetriever(@NonNull String key) {
    }

    public void hideStatusBar() {
        if (getWindow() != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(@ColorRes int color) {
        if (color != 0 && getWindow() != null) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP | Build.VERSION_CODES.KITKAT)
    public void setTransparentStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public void setWindowFlag(@NonNull Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
