package com.picon.utils.loaders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.ArrayRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

public class ResourceLoader {

    public static boolean getBoolean(@NonNull Context context, @BoolRes int resId) {
        return context.getResources().getBoolean(resId);
    }

    public static int getColor(@NonNull Context context, @ColorRes int resId) {
        return ResourcesCompat.getColor(context.getResources(), resId, context.getTheme());
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int resId) {
        return ResourcesCompat.getColorStateList(context.getResources(), resId, context.getTheme());
    }

    public static float getDimension(@NonNull Context context, @DimenRes int resId) {
        return context.getResources().getDimension(resId);
    }

    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return ResourcesCompat.getDrawable(context.getResources(), resId, context.getTheme());
    }

    public static float getFloat(@NonNull Context context, @DimenRes int resId) {
        return ResourcesCompat.getFloat(context.getResources(), resId);
    }

    public static Typeface getFont(@NonNull Context context, @FontRes int resId) {
        return ResourcesCompat.getFont(context, resId);
    }

    public static int getInteger(@NonNull Context context, @IntegerRes int resId) {
        return context.getResources().getInteger(resId);
    }

    public static int[] getIntegers(@NonNull Context context, @ArrayRes int resId) {
        return context.getResources().getIntArray(resId);
    }

    public static String getString(@NonNull Context context, @StringRes int resId) {
        return context.getResources().getString(resId);
    }

    public static String getString(@NonNull Context context, @StringRes int resId, Object... formatArgs) {
        return context.getResources().getString(resId, formatArgs);
    }

    public static String[] getStrings(@NonNull Context context, @ArrayRes int resId) {
        return context.getResources().getStringArray(resId);
    }

}
