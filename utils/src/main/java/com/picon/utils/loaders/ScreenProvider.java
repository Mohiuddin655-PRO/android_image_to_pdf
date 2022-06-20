package com.picon.utils.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScreenProvider<T> {

    @SafeVarargs
    public final void load(@NonNull Context context, @NonNull OnScreenProviderListener<T> listener, @NonNull ScreenValue<T>... values) {

        if (values.length > 0) {

            for (int index = 0; index < values.length; index++) {

                final ScreenValue<T> value = values[index];

                if (value != null) {

                    final OnScreenProviderListener.Event<T> event = new OnScreenProviderListener.Event<>(context, value);

                    if (value.isValid) {
                        if (listener.onSkip(event)) break;
                    } else {
                        if (listener.onHold(event)) break;
                    }

                    if (values.length == index + 1) listener.onDefault(context);
                }
            }
        }
    }

    public interface OnScreenProviderListener<T> {

        default void onDefault(@NonNull Context context) {
        }

        default boolean onSkip(@NonNull Event<T> event) {
            return false;
        }

        boolean onHold(@NonNull Event<T> event);

        class Event<T> {

            @NonNull
            public final Context mContext;
            @Nullable
            public final T mScreen;

            public Event(@NonNull Context mContext, @NonNull ScreenValue<T> mResult) {
                this.mContext = mContext;
                this.mScreen = mResult.mScreen;
            }
        }

    }

    public static class ScreenValue<T> {

        public boolean isValid;
        @Nullable
        public T mScreen;

        public ScreenValue(boolean isValid, @Nullable T mRequireScreen) {
            this.isValid = isValid;
            this.mScreen = mRequireScreen;
        }
    }

}
