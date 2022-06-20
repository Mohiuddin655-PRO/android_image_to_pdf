package com.picon.utils.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.net.InetSocketAddress;

public class ConnectivityService extends SystemService {

    public static final int TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    public static final int TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
    public static final int TYPE_BLUETOOTH = ConnectivityManager.TYPE_BLUETOOTH;
    public static final int TYPE_ETHERNET = ConnectivityManager.TYPE_ETHERNET;

    public ConnectivityService(@NonNull Context mContext) {
        super(mContext);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Network getActiveNetwork() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getActiveNetwork();
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Network[] getAllNetworks() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getAllNetworks();
        } else {
            return null;
        }
    }

    public NetworkInfo getActiveNetworkInfo() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getActiveNetworkInfo();
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Network getBoundNetworkForProcess() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getBoundNetworkForProcess();
        } else {
            return null;
        }
    }

    public boolean getBackgroundDataSetting() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getBackgroundDataSetting();
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public int getConnectionOwnerUid(int protocol, @NonNull InetSocketAddress local, @NonNull InetSocketAddress remote) {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getConnectionOwnerUid(protocol, local, remote);
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ProxyInfo getDefaultProxy() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getDefaultProxy();
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinkProperties getLinkProperties(@NonNull Network network) {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getLinkProperties(network);
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getMultiPathPreference(@NonNull Network network) {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getMultipathPreference(network);
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NetworkInfo getNetworkInfo(@NonNull Network network) {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getNetworkInfo(network);
        } else {
            return null;
        }
    }

    public NetworkInfo getNetworkInfo(int mNetworkType) {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getNetworkInfo(mNetworkType);
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NetworkCapabilities getNetworkCapabilities(@NonNull Network network) {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getNetworkCapabilities(network);
        } else {
            return null;
        }
    }

    public int getNetworkPreference() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getNetworkPreference();
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public byte[] getNetworkWatchlistConfigHash() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getNetworkWatchlistConfigHash();
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getRestrictBackgroundStatus() {
        if (mConnectivityManager != null) {
            return mConnectivityManager.getRestrictBackgroundStatus();
        } else {
            return 0;
        }
    }

}
