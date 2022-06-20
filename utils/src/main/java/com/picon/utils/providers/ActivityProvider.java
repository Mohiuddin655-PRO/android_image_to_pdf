package com.picon.utils.providers;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class ActivityProvider {

    public static void hideStatusBar(@NonNull Activity activity) {
        if (activity.getWindow() != null) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(@NonNull Activity activity, @ColorRes int color) {
        if (color != 0 && activity.getWindow() != null) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        }
    }

}
