package com.example.image2pdf.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static Uri getImageUri(@NonNull Context inContext, @NonNull Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = fromBitmap(inContext, inImage);
        return Uri.parse(path);
    }

    public static String fromBitmap(@NonNull Context inContext, @NonNull Bitmap inImage) {
        String title = String.format("Title_%s", System.currentTimeMillis());
        String description = String.format("Description_%s", System.currentTimeMillis());
        return MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, title, description);
    }

    public static void cropImage(@NonNull Activity inActivity, int requestCode, @NonNull Uri src) {
        if (isCheckedPermission(inActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            final UCrop.Options options = new UCrop.Options();
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            UCrop uCrop = UCrop.of(src, Uri.fromFile(new File(inActivity.getCacheDir(), "img_" + System.currentTimeMillis() + ".jpg")));
            options.setToolbarTitle("Edit photo");
            options.setFreeStyleCropEnabled(true);
            uCrop.withOptions(options);
            uCrop.start(inActivity, requestCode);
        } else {
            permissionRequest(inActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    public static void chooseMultiplePhoto(@NonNull Activity inActivity, int requestCode) {
        if (isCheckedPermission(inActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) && isCheckedPermission(inActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            inActivity.startActivityForResult(new Intent()
                    .setAction(Intent.ACTION_GET_CONTENT)
                    .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    .setType("image/*"), requestCode);
        } else {
            permissionRequest(inActivity, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    public static void choosePhoto(@NonNull Activity inActivity, int requestCode) {
        if (isCheckedPermission(inActivity, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            inActivity.startActivityForResult(intent, requestCode);
        } else {
            permissionRequest(inActivity, Manifest.permission.CAMERA);
        }
    }

    public static void permissionRequest(@NonNull Activity activity, @NonNull String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 100);
    }

    public static boolean isCheckedPermission(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static Bitmap getBitmap(@NonNull ContentResolver resolver, @NonNull Uri url) throws IOException {
        InputStream input = resolver.openInputStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }
}
