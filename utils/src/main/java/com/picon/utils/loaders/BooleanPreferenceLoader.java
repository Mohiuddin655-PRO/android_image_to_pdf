package com.picon.utils.loaders;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.picon.utils.constains.DefaultPreferencePath;
import com.picon.utils.preferences.PreferenceHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BooleanPreferenceLoader<T> {

    private static final String TAG = BooleanPreferenceLoader.class.getSimpleName();
    private static final String NAME = DefaultPreferencePath.DEFAULT_PREFERENCES;
    private static final int MODE = Context.MODE_PRIVATE;

    public static void setPreference(@NonNull Context context, @NonNull Class<?> cls) {
        setPreference(context, cls, true);
    }

    public static void setPreference(@NonNull Context context, @NonNull Class<?> cls, boolean value) {
        final PreferenceHelper helper = new PreferenceHelper(context, NAME, MODE);
        final String key = cls.getSimpleName();
        helper.setBoolean(key, value);
    }

    @SafeVarargs
    public final void load(@NonNull Context context, @NonNull OnPreferenceLoaderListener<T> listener, @NonNull T... types) {

        if (types.length > 0) {

            final PreferenceHelper helper = new PreferenceHelper(context, NAME, MODE);

            for (int index = 0; index < types.length; index++) {

                T type = types[index];

                if (type != null) {

                    final OnPreferenceLoaderListener.Event<T> event = new OnPreferenceLoaderListener.Event<>(context, type, types);

                    if (isExisted(helper, type)) {
                        if (listener.onExisted(event)) break;
                    } else {
                        if (listener.onUnExisted(event)) break;
                    }

                    if (types.length == index + 1) listener.onDefault(context);
                }
            }
        }
    }

    public boolean isExisted(@NonNull PreferenceHelper helper, @NonNull T t) {
        try {
            return helper.getBoolean(t.getClass().getSimpleName());
        } catch (Exception e) {
            Log.e(TAG, "isExisted: " + e.getMessage(), e);
            return false;
        }
    }

    public interface OnPreferenceLoaderListener<T> {

        default void onDefault(@NonNull Context context) {
        }

        default boolean onExisted(@NonNull Event<T> event) {
            return false;
        }

        boolean onUnExisted(@NonNull Event<T> event);

        class Event<T> {

            public final Context mContext;
            public final T mResult;
            public final List<T> mTypes;

            @SafeVarargs
            public Event(@NonNull Context mContext, @NonNull T mResult, @NonNull T... mTypes) {
                this.mContext = mContext;
                this.mResult = mResult;
                this.mTypes = mTypes.length > 0 ? Arrays.asList(mTypes) : new ArrayList<>();
            }
        }

    }

}
