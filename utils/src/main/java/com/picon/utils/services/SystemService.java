package com.picon.utils.services;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

public class SystemService {

    public static final String CONNECTIVITY_SERVICE = Context.CONNECTIVITY_SERVICE;

    public final Context mContext;
    public final ConnectivityManager mConnectivityManager;

    public SystemService(@NonNull Context mContext) {
        this.mContext = mContext;
        this.mConnectivityManager = getConnectivityManager(mContext);
    }

    public ConnectivityManager getConnectivityManager(@NonNull Context mContext) {
        if (mConnectivityManager != null) {
            return mConnectivityManager;
        } else {
            return (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        }
    }

}
