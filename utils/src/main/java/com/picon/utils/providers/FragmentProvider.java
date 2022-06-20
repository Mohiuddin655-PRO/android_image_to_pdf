package com.picon.utils.providers;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentProvider {

    private final @NonNull
    FragmentManager mFragmentManager;
    private final @IdRes
    int mContainerViewId;

    public FragmentProvider(@NonNull FragmentManager mFragmentManager, @IdRes int containerViewId) {
        this.mFragmentManager = mFragmentManager;
        this.mContainerViewId = containerViewId;
    }

    public void load(@Nullable Fragment mFragment) {
        load(mFragment, null, true);
    }

    public void load(@Nullable Fragment mFragment, boolean isBackHome) {
        load(mFragment, null, isBackHome);
    }

    public void load(@Nullable Fragment mFragment, @Nullable Bundle bundle) {
        load(mFragment, bundle, true);
    }

    public void load(@Nullable Fragment mFragment, @Nullable Bundle bundle, boolean isBackHome) {

        if (mFragment != null) {

            if (bundle != null) {
                mFragment.setArguments(bundle);
            }

            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            if (!isBackHome) {
                transaction.addToBackStack(null).replace(mContainerViewId, mFragment).commit();
            } else {
                transaction.replace(mContainerViewId, mFragment).commit();
            }

        }
    }

}
