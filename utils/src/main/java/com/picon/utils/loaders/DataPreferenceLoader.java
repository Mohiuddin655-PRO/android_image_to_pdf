package com.picon.utils.loaders;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.constains.DefaultPreferencePath;
import com.picon.utils.models.DataType;
import com.picon.utils.models.KeyValue;
import com.picon.utils.preferences.PreferenceHelper;

import java.util.Arrays;
import java.util.List;

public class DataPreferenceLoader {

    private static final String TAG = DataPreferenceLoader.class.getSimpleName();
    private static final String NAME = DefaultPreferencePath.DEFAULT_PREFERENCES;
    private static final int MODE = Context.MODE_PRIVATE;

    public static void setPreference(@NonNull Context context, @NonNull String key) {
        setPreference(context, key, true);
    }

    public static void setPreference(@NonNull Context context, @NonNull String key, boolean value) {
        setPreference(context, key, value, NAME);
    }

    public static void setPreference(@NonNull Context context, @NonNull String key, @NonNull String preferenceName) {
        setPreference(context, key, true, preferenceName);
    }

    public static void setPreference(@NonNull Context context, @NonNull String key, boolean value, @NonNull String preferenceName) {
        PreferenceHelper helper = new PreferenceHelper(context, preferenceName, MODE);
        helper.setBoolean(key, value);
    }

    public static void load(@NonNull Context context, @NonNull OnDataPreferenceListener listener, @NonNull KeyValue... keyValues) {
        if (keyValues.length > 0) load(context, listener, Arrays.asList(keyValues));
    }

    public static void load(@NonNull Context context, @NonNull OnDataPreferenceListener listener, @NonNull List<KeyValue> keyValues) {
        load(context, NAME, listener, keyValues);
    }

    public static void load(@NonNull Context context, @NonNull String preferenceName, @NonNull OnDataPreferenceListener listener, @NonNull KeyValue... keyValues) {
        if (keyValues.length > 0) load(context, preferenceName, listener, Arrays.asList(keyValues));
    }

    public static void load(@NonNull Context context, @NonNull String preferenceName, @NonNull OnDataPreferenceListener listener, @NonNull List<KeyValue> keyValues) {

        if (keyValues.size() > 0) {

            final PreferenceHelper helper = new PreferenceHelper(context, preferenceName, MODE);

            for (int index = 0; index < keyValues.size(); index++) {

                final KeyValue keyValue = keyValues.get(index);

                if (keyValue != null) {

                    final OnDataPreferenceListener.Event event = new OnDataPreferenceListener.Event(context, keyValue);

                    if (isExisted(helper, keyValue)) {
                        if (listener.onExisted(event)) break;
                    } else {
                        if (listener.onUnExisted(event)) break;
                    }

                    if (keyValues.size() == index + 1) listener.onDefault(context);

                }
            }
        }
    }

    private static boolean isExisted(@NonNull PreferenceHelper preference, @NonNull KeyValue keyValue) {

        final String key = keyValue.mKey;

        try {
            switch (keyValue.mType) {
                case BOOLEAN:
                    return preference.getBoolean(key);
                case FLOAT:
                    return preference.getFloat(key) != 0F;
                case INTEGER:
                    return preference.getInt(key) != 0;
                case LONG:
                    return preference.getLong(key) != 0L;
                case STRING:
                    return preference.getString(key) != null;
                default:
                    return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "isExisted: " + e.getMessage(), e);
            return false;
        }
    }

    public interface OnDataPreferenceListener {

        default void onDefault(@NonNull Context context) {
        }

        default boolean onExisted(@NonNull Event event) {
            return false;
        }

        boolean onUnExisted(@NonNull Event event);

        class Event {

            @NonNull
            public final Context mContext;
            @NonNull
            public final String mKey;
            @NonNull
            public final DataType mType;
            @Nullable
            public final Object mValue;

            public Event(@NonNull Context mContext, @NonNull KeyValue mResult) {
                this.mContext = mContext;
                this.mKey = mResult.mKey;
                this.mType = mResult.mType;
                this.mValue = mResult.mValue;
            }
        }
    }

}
