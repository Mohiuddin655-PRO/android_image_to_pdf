package com.picon.utils.providers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class PermissionProvider {

    public static boolean isPermissionGranted(@NonNull Context mContext, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void allowPermissions(@NonNull Activity mActivity, @NonNull String[] permissions, int request_code) {
        ActivityCompat.requestPermissions(mActivity, permissions, request_code);
    }

    public static boolean isContactsPermissionGranted(@NonNull Context mContext) {
        return isPermissionGranted(mContext, Manifest.permission.READ_CONTACTS);
    }

    public static boolean isExternalStoragePermissionGranted(@NonNull Context mContext) {
        boolean read_permission = isPermissionGranted(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean write_permission = isPermissionGranted(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return read_permission && write_permission;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isPhoneNumbersPermissionGranted(@NonNull Context mContext) {
        return isPermissionGranted(mContext, Manifest.permission.READ_PHONE_NUMBERS);
    }

    public static boolean isSMSPermissionGranted(@NonNull Context mContext) {
        return isPermissionGranted(mContext, Manifest.permission.READ_SMS);
    }

    public static void allowExternalStoragePermission(@NonNull Activity mActivity, int request_code) {
        allowPermissions(mActivity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                request_code
        );
    }

    public static void allowContactsPermission(@NonNull Activity mActivity, int request_code) {
        allowPermissions(mActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                request_code
        );
    }

    public static void allowSMSPermission(@NonNull Activity mActivity, int request_code) {
        allowPermissions(mActivity,
                new String[]{Manifest.permission.READ_SMS},
                request_code
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void allowPhoneNumbersPermission(@NonNull Activity mActivity, int request_code) {
        allowPermissions(mActivity,
                new String[]{Manifest.permission.READ_PHONE_NUMBERS},
                request_code
        );
    }

}
