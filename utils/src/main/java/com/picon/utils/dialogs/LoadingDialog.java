package com.picon.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.picon.utils.databinding.DialogLoadingBinding;

public class LoadingDialog extends OptionDialog {

    private final Toast mToast;
    private Dialog mDialog;

    public LoadingDialog(@NonNull Context mContext, @StyleRes int mTheme) {
        super(mContext, mTheme);
        this.mToast = Toast.getInstance(mContext);
        this.mDialog = new Dialog(mContext, mTheme);
    }

    @NonNull
    public static LoadingDialog getInstance(@NonNull Context mContext, @StyleRes int mTheme) {
        return new LoadingDialog(mContext, mTheme);
    }

    public void load(boolean show) {
        load(show, 0);
    }

    public void load(boolean show, int duration) {
        load(show, true, duration);
    }

    public void load(boolean show, boolean cancelable) {
        load(show, cancelable, 0);
    }

    public void load(boolean show, boolean cancelable, int duration) {
        if (show) {
            mDialog = new Dialog(mContext);
            final DialogLoadingBinding binding = DialogLoadingBinding.inflate(LayoutInflater.from(mContext));
            final Window window = mDialog.getWindow();
            if (window != null) {
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            mDialog.setContentView(binding.getRoot());
            mDialog.getContext().setTheme(mTheme);
            mDialog.setCancelable(false);
            if (duration > 0) {
                new Handler().postDelayed(() -> mDialog.dismiss(), duration);
            } else {
                new Handler().postDelayed(() -> {
                    mDialog.setCancelable(cancelable);
                    mToast.toast("Something went wrong!");
                }, 10000);
            }
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }

}
