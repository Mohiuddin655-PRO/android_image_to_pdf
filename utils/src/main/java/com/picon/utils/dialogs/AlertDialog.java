package com.picon.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.picon.utils.databinding.DialogAlertMessageBinding;
import com.picon.utils.validators.Validator;

public class AlertDialog extends MessageDialog {

    public AlertDialog(@NonNull Context mContext, @StyleRes int mTheme) {
        super(mContext, mTheme);
    }

    public void alert(String message, @NonNull OnDataChangeListener<String> listener) {
        alert(message, "OK", listener);
    }

    public void alert(String message, String button, @NonNull OnDataChangeListener<String> listener) {
        alert(message, null, button, null, listener);
    }

    public void alert(String message, String positiveButton, String negativeButton, @NonNull OnDataChangeListener<String> listener) {
        alert(message, null, positiveButton, negativeButton, listener);
    }

    public void alert(String header, String body, String positiveButton, String negativeButton, @NonNull OnDataChangeListener<String> listener) {

        final Dialog dialog = new Dialog(mContext);
        final DialogAlertMessageBinding binding = DialogAlertMessageBinding.inflate(LayoutInflater.from(mContext));
        final Window window = dialog.getWindow();

        binding.tvMessageHeader.setText(header);
        binding.positiveButton.setText(positiveButton);

        if (Validator.isValidString(body)) {
            binding.tvMessageBody.setText(body);
        } else {
            binding.tvMessageBody.setVisibility(View.GONE);
        }

        if (Validator.isValidString(negativeButton)) {
            binding.negativeButton.setText(negativeButton);
            dialog.setCancelable(false);
        } else {
            binding.negativeButton.setVisibility(View.GONE);
        }

        binding.positiveButton.setOnClickListener(v -> {
            listener.onChange(positiveButton);
            dialog.dismiss();
        });

        binding.negativeButton.setOnClickListener(v -> {
            listener.onChange(negativeButton);
            dialog.dismiss();
        });

        if (window != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        dialog.setContentView(binding.getRoot());
        dialog.getContext().setTheme(mTheme);
        dialog.show();

    }

}
