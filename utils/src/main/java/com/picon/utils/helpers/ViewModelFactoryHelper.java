package com.picon.utils.helpers;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactoryHelper implements ViewModelProvider.Factory {

    public Activity mActivity;
    public Bundle mBundle;

    public ViewModelFactoryHelper() {
        this(new Activity());
    }

    public ViewModelFactoryHelper(@NonNull Activity activity) {
        this(activity, new Bundle());
    }

    public ViewModelFactoryHelper(@NonNull Activity activity, @NonNull Bundle bundle) {
        this.mActivity = activity;
        this.mBundle = bundle;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return (T) modelClass.getConstructor(Activity.class, Bundle.class).newInstance(mActivity, mBundle);
        } catch (Exception e) {
            try {
                return (T) modelClass.getConstructor(Activity.class).newInstance(mActivity);
            } catch (Exception ex) {
                try {
                    return (T) modelClass.newInstance();
                } catch (IllegalAccessException exc) {
                    throw new IllegalArgumentException("Access denied.");
                } catch (InstantiationException exc) {
                    throw new IllegalArgumentException("Unknown ViewModel Constructor or Class");
                }
            }
        }
    }

}
