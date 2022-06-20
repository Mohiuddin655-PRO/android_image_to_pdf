package com.picon.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Set;

public class DefaultPreference {

    private final SharedPreferences mPreferences;

    public DefaultPreference(@NonNull Context context) {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isAdded(@NonNull String key) {
        return mPreferences != null && mPreferences.contains(key);
    }

    public boolean isNotAdded(@NonNull String key) {
        return mPreferences != null && !mPreferences.contains(key);
    }

    public Map<String, ?> getDefaultAll() {
        return mPreferences != null ? mPreferences.getAll() : null;
    }

    public boolean getDefaultBoolean(@NonNull String key) {
        return mPreferences.getBoolean(key, false);
    }

    public float getDefaultFloat(@NonNull String key) {
        return mPreferences.getFloat(key, 0F);
    }

    public int getDefaultInt(@NonNull String key) {
        return mPreferences.getInt(key, 0);
    }

    public long getDefaultLong(@NonNull String key) {
        return mPreferences.getLong(key, 0L);
    }

    public String getDefaultString(@NonNull String key) {
        return mPreferences.getString(key, null);
    }

    public Set<String> getDefaultStringSet(@NonNull String key) {
        return mPreferences.getStringSet(key, null);
    }

    public void setDefaultBoolean(@NonNull String key, boolean value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setDefaultFloat(@NonNull String key, float value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putFloat(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setDefaultInt(@NonNull String key, int value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setDefaultLong(@NonNull String key, long value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putLong(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setDefaultString(@NonNull String key, String value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setDefaultStringSet(@NonNull String key, Set<String> values) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putStringSet(key, values);
            editor.commit();
            editor.apply();
        }
    }

    public void removeDefaultItem(@NonNull String key) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.remove(key);
            editor.commit();
            editor.apply();
        }
    }

    public void clearDefaultItems() {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.commit();
            editor.apply();
        }
    }

    public void setPreferenceChangeListener(@NonNull SharedPreferences.OnSharedPreferenceChangeListener listener) {
        if (mPreferences != null) {
            mPreferences.registerOnSharedPreferenceChangeListener(listener);
            mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
        }
    }

}
