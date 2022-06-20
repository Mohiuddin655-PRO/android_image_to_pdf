package com.picon.utils.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class NetworkService extends ConnectivityService {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static final int TYPE_VPN = ConnectivityManager.TYPE_VPN;

    public NetworkService(@NonNull Context mContext) {
        super(mContext);
    }

    public static NetworkService getInstance(@NonNull Context context) {
        return new NetworkService(context);
    }

    public boolean isBluetoothAvailable() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_BLUETOOTH);
        return networkInfo != null && networkInfo.isAvailable();
    }

    public boolean isCellularAvailable() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_MOBILE);
        return networkInfo != null && networkInfo.isAvailable();
    }

    public boolean isEthernetAvailable() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_ETHERNET);
        return networkInfo != null && networkInfo.isAvailable();
    }

    public boolean isInternetAvailable() {
        return isCellularAvailable() || isWifiAvailable();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isVPNAvailable() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_VPN);
        return networkInfo != null && networkInfo.isAvailable();
    }

    public boolean isWifiAvailable() {
        final NetworkInfo networkInfo = getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    public boolean isBluetoothConnected() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_BLUETOOTH);
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isCellularConnected() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isEthernetConnected() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_ETHERNET);
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isInternetConnected() {
        return isCellularConnected() || isWifiConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isVPNConnected() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_VPN);
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isWifiConnected() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isBluetoothConnectedOrConnecting() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_BLUETOOTH);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isCellularConnectedOrConnecting() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isEthernetConnectedOrConnecting() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_ETHERNET);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isInternetConnectedOrConnecting() {
        return isCellularConnectedOrConnecting() || isWifiConnectedOrConnecting();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isVPNConnectedOrConnecting() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_VPN);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isWifiConnectedOrConnecting() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isBluetoothFailOver() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_BLUETOOTH);
        return networkInfo != null && networkInfo.isFailover();
    }

    public boolean isCellularFailOver() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_MOBILE);
        return networkInfo != null && networkInfo.isFailover();
    }

    public boolean isEthernetFailOver() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_ETHERNET);
        return networkInfo != null && networkInfo.isFailover();
    }

    public boolean isInternetFailOver() {
        return isCellularFailOver() || isWifiFailOver();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isVPNFailOver() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_VPN);
        return networkInfo != null && networkInfo.isFailover();
    }

    public boolean isWifiFailOver() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_WIFI);
        return networkInfo != null && networkInfo.isFailover();
    }

    public boolean isBluetoothRoaming() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_BLUETOOTH);
        return networkInfo != null && networkInfo.isRoaming();
    }

    public boolean isCellularRoaming() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_MOBILE);
        return networkInfo != null && networkInfo.isRoaming();
    }

    public boolean isEthernetRoaming() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_ETHERNET);
        return networkInfo != null && networkInfo.isRoaming();
    }

    public boolean isInternetRoaming() {
        return isCellularRoaming() || isWifiRoaming();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isVPNRoaming() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_VPN);
        return networkInfo != null && networkInfo.isRoaming();
    }

    public boolean isWifiRoaming() {
        final NetworkInfo networkInfo = getNetworkInfo(TYPE_WIFI);
        return networkInfo != null && networkInfo.isRoaming();
    }

}
