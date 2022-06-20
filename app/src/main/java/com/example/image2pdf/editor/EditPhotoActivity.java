package com.example.image2pdf.editor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.image2pdf.databinding.ActivityEditPhotoBinding;
import com.example.image2pdf.utils.Utils;

import java.io.IOException;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class EditPhotoActivity extends AppCompatActivity {

    private static final String TAG = EditPhotoActivity.class.getSimpleName();

    private ActivityEditPhotoBinding mBinding;
    private PhotoEditor mPhotoEditor;

    private Uri mParcelableUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEditPhotoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
        listener();
    }

    private void init() {

        mParcelableUri = getIntent() != null ? getIntent().getParcelableExtra("data") : null;

        mPhotoEditor = new PhotoEditor.Builder(this, mBinding.pevEdit)
                .setClipSourceImage(true)
                .build();

        mPhotoEditor.setFilterEffect(PhotoFilter.BRIGHTNESS);

        try {
            mPhotoEditor.addImage(Utils.getBitmap(getContentResolver(), mParcelableUri));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    private void listener() {
        if (Utils.isCheckedPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mPhotoEditor.saveAsFile(String.valueOf(mParcelableUri), new PhotoEditor.OnSaveListener() {
                @Override
                public void onSuccess(@NonNull String path) {
                    setData(Uri.parse(path));
                }

                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e(TAG, "onFailure: " + exception.getMessage(), exception);
                }
            });
        } else {
            Utils.permissionRequest(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void setData(@NonNull Uri data) {
        Intent intent = new Intent();
        intent.setData(data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}