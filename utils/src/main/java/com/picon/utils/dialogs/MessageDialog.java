package com.picon.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.picon.utils.databinding.DialogMessageBinding;
import com.picon.utils.validators.Validator;

public class MessageDialog extends Toast {

    public int mTheme;

    public MessageDialog(@NonNull Context mContext, @StyleRes int mTheme) {
        super(mContext);
        this.mTheme = mTheme;
    }

    public void message(String message) {
        if (Validator.isValidString(message)) {
            Dialog dialog = new Dialog(mContext);
            final DialogMessageBinding binding = DialogMessageBinding.inflate(LayoutInflater.from(mContext));
            final Window window = dialog.getWindow();

            binding.tvMessage.setText(message);

            binding.actionOk.setOnClickListener(v -> dialog.dismiss());

            if (window != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.setContentView(binding.getRoot());
            dialog.getContext().setTheme(mTheme);
            dialog.show();
        }
    }
}
