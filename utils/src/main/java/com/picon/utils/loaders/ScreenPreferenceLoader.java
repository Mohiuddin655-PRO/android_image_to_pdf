package com.picon.utils.loaders;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.constains.DefaultPreferencePath;
import com.picon.utils.models.DataType;
import com.picon.utils.models.Value;
import com.picon.utils.preferences.PreferenceHelper;

public class ScreenPreferenceLoader<T> {

    private static final String TAG = ScreenPreferenceLoader.class.getSimpleName();
    private static final String NAME = DefaultPreferencePath.DEFAULT_PREFERENCES;
    private static final int MODE = Context.MODE_PRIVATE;

    public static void setPreference(@NonNull Context context, @NonNull String mScreenName, @Nullable Object value) {

        final PreferenceHelper helper = new PreferenceHelper(context, NAME, MODE);

        if (value instanceof Boolean) {
            helper.setBoolean(mScreenName, (boolean) value);
        } else if (value instanceof Float) {
            helper.setFloat(mScreenName, (float) value);
        } else if (value instanceof Integer) {
            helper.setInt(mScreenName, (int) value);
        } else if (value instanceof Long) {
            helper.setLong(mScreenName, (long) value);
        } else if (value instanceof String) {
            helper.setString(mScreenName, (String) value);
        }
    }

    public static Object getPreference(@NonNull Context context, @NonNull String mScreenName, @NonNull DataType dataType) {

        final PreferenceHelper helper = new PreferenceHelper(context, NAME, MODE);

        switch (dataType) {
            case BOOLEAN:
                return helper.getBoolean(mScreenName);
            case FLOAT:
                return helper.getFloat(mScreenName);
            case INTEGER:
                return helper.getInt(mScreenName);
            case LONG:
                return helper.getLong(mScreenName);
            case STRING:
                return helper.getString(mScreenName);
            default:
                return null;
        }
    }

    public static boolean isExisted(@NonNull Context context, @NonNull String mScreenName) {
        final PreferenceHelper helper = new PreferenceHelper(context, NAME, MODE);
        return helper.getBoolean(mScreenName);
    }

    public boolean isExisted(@NonNull PreferenceHelper helper, @NonNull Value<T> value) {

        final String key = value.mScreenName;
        final DataType type = value.mDataType;

        try {
            if (type == DataType.BOOLEAN) {
                return helper.getBoolean(key);
            } else if (type == DataType.FLOAT) {
                return helper.getFloat(key) != 0F;
            } else if (type == DataType.INTEGER) {
                return helper.getInt(key) != 0;
            } else if (type == DataType.LONG) {
                return helper.getLong(key) != 0L;
            } else if (type == DataType.STRING) {
                return helper.getString(key) != null;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "isExisted: " + e.getMessage(), e);
            return false;
        }
    }

    @SafeVarargs
    public final void load(@NonNull Context context, @NonNull OnScreenPreferenceListener<T> listener, @NonNull Value<T>... values) {

        if (values.length > 0) {

            final PreferenceHelper helper = new PreferenceHelper(context, NAME, MODE);

            for (int index = 0; index < values.length; index++) {

                final Value<T> value = values[index];

                if (value != null) {

                    final OnScreenPreferenceListener.Event<T> event = new OnScreenPreferenceListener.Event<>(context, value);

                    if (isExisted(helper, value)) {
                        if (listener.onExisted(event)) break;
                    } else {
                        if (listener.onUnExisted(event)) break;
                    }

                    if (values.length == index + 1) listener.onDefault(context);
                }
            }
        }
    }

    public interface OnScreenPreferenceListener<T> {

        default void onDefault(@NonNull Context context) {
        }

        default boolean onExisted(@NonNull Event<T> event) {
            return false;
        }

        boolean onUnExisted(@NonNull Event<T> event);

        class Event<T> {

            @NonNull
            public final Context mContext;
            @Nullable
            public final T mData;
            @NonNull
            public final DataType mDataType;
            @NonNull
            public final String mScreenName;

            public Event(@NonNull Context mContext, @NonNull Value<T> mResult) {
                this.mContext = mContext;
                this.mData = mResult.mData;
                this.mDataType = mResult.mDataType;
                this.mScreenName = mResult.mScreenName;
            }
        }

    }

}
