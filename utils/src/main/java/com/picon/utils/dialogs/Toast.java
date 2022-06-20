package com.picon.utils.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.picon.utils.helpers.DialogHelper;
import com.picon.utils.validators.Validator;

public class Toast extends DialogHelper {

    public Toast(@NonNull Context mContext) {
        super(mContext);
    }

    @NonNull
    public static Toast getInstance(@NonNull Context context) {
        return new Toast(context);
    }

    public void toast(@StringRes int resId) {
        this.toast(mContext.getString(resId), false);
    }

    public void toast(@StringRes int resId, boolean isLong) {
        this.toast(mContext.getString(resId), isLong);
    }

    public void toast(String message) {
        this.toast(message, false);
    }

    public void toast(String message, boolean isLong) {
        if (Validator.isValidString(message)) {
            if (isLong) {
                android.widget.Toast.makeText(mContext, message, android.widget.Toast.LENGTH_LONG).show();
            } else {
                android.widget.Toast.makeText(mContext, message, android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    }

}
