package com.picon.utils.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.view.ContextThemeWrapper;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.detectlanguage.DetectLanguage;
import com.picon.utils.defaults.DefaultData;
import com.picon.utils.validators.Validator;

import java.util.Locale;

public class LocaleHelper extends ContextThemeWrapper {

    public LocaleHelper(@NonNull Context base, @StyleRes int theme) {
        super(base, theme);
    }

    public static ContextWrapper wrap(@NonNull Context context, @StyleRes int theme, @NonNull String language) {
        Configuration config = context.getResources().getConfiguration();
        if (Validator.isValidString(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale);
            } else {
                setSystemLocaleLegacy(config, locale);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale);
                context = context.createConfigurationContext(config);
            } else {
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }
        }
        return new LocaleHelper(context, theme);
    }

    private static Locale getSystemLocaleLegacy(@NonNull Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Locale getSystemLocale(@NonNull Configuration config) {
        return config.getLocales().get(0);
    }

    private static void setSystemLocaleLegacy(@NonNull Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static void setSystemLocale(@NonNull Configuration config, Locale locale) {
        config.setLocale(locale);
    }

    public static String getCountry() {
        return Locale.getDefault().getDisplayCountry();
    }

    public static String getCountry(@NonNull String language) {
        return new Locale(language).getDisplayCountry();
    }

    public static String getISO2Country() {
        return Locale.getDefault().getCountry();
    }

    public static String getISO2Country(@NonNull String language) {
        return new Locale(language).getCountry();
    }

    public static String getISO3Country() {
        return Locale.getDefault().getISO3Country();
    }

    public static String getISO3Country(@NonNull String language) {
        return new Locale(language).getISO3Country();
    }

    public static String getISO2Language() {
        return Locale.getDefault().getLanguage();
    }

    public static String getISO2Language(@NonNull String language) {
        return new Locale(language).getLanguage();
    }

    public static String getISO3Language() {
        return Locale.getDefault().getISO3Language();
    }

    public static String getISO3Language(@NonNull String language) {
        return new Locale(language).getISO3Language();
    }

    public static String getLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

    public static String getLanguage(@NonNull String language) {
        return new Locale(language).getDisplayLanguage();
    }

    public static String getDetectedISO2Language(@NonNull String text) {
        try {
            return DetectLanguage.simpleDetect(text);
        } catch (Exception e) {
            e.printStackTrace();
            return DefaultData.CountryData.DEFAULT_LANGUAGE_CODE;
        }
    }
}