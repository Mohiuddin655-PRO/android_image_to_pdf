package com.picon.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Set;

public class PreferenceHelper extends DefaultPreference {

    public final SharedPreferences mPreferences;

    public PreferenceHelper(@NonNull Context context, @NonNull String name, int mode) {
        super(context);
        this.mPreferences = context.getSharedPreferences(name, mode);
    }

    public boolean isAdded(@NonNull String key) {
        return mPreferences != null && mPreferences.contains(key);
    }

    public boolean isNotAdded(@NonNull String key) {
        return mPreferences != null && !mPreferences.contains(key);
    }

    public Map<String, ?> getAll() {
        return mPreferences != null ? mPreferences.getAll() : null;
    }

    public boolean getBoolean(@NonNull String key) {
        return mPreferences.getBoolean(key, false);
    }

    public float getFloat(@NonNull String key) {
        return mPreferences.getFloat(key, 0F);
    }

    public int getInt(@NonNull String key) {
        return mPreferences.getInt(key, 0);
    }

    public long getLong(@NonNull String key) {
        return mPreferences.getLong(key, 0L);
    }

    public String getString(@NonNull String key) {
        return mPreferences.getString(key, null);
    }

    public Set<String> getStringSet(@NonNull String key) {
        return mPreferences.getStringSet(key, null);
    }

    public void setBoolean(@NonNull String key, boolean value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setFloat(@NonNull String key, float value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putFloat(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setInt(@NonNull String key, int value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setLong(@NonNull String key, long value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putLong(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setString(@NonNull String key, String value) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(key, value);
            editor.commit();
            editor.apply();
        }
    }

    public void setStringSet(@NonNull String key, Set<String> values) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putStringSet(key, values);
            editor.commit();
            editor.apply();
        }
    }

    public void removeItem(@NonNull String key) {
        if (mPreferences != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.remove(key);
            editor.commit();
            editor.apply();
        }
    }

    public void clearItems() {
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
