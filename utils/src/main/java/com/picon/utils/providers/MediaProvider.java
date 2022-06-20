package com.picon.utils.providers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.converters.DateConverter;
import com.picon.utils.formats.KeyFormat;
import com.picon.utils.helpers.RunnableHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MediaProvider extends Environment {

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 100;
    private static final String FILE_TYPE_IMG = "IMG";
    private static final String FILE_TYPE_VID = "VID";
    private static final String FILE_EXTENSION_JPG = "jpg";
    private static final String FILE_EXTENSION_JPEG = "jpeg";
    private static final String FILE_EXTENSION_PNG = "png";
    private static Exception mException = new Exception();

    public static void choosePhoto(@NonNull Activity mActivity, int requestCode) {
        if (PermissionProvider.isExternalStoragePermissionGranted(mActivity)) {
            mActivity.startActivityForResult(new Intent()
                    .setAction(Intent.ACTION_GET_CONTENT)
                    .setType("image/*"), requestCode);
        } else {
            PermissionProvider.allowExternalStoragePermission(mActivity, EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    public static void chooseVideo(@NonNull Activity mActivity, int requestCode) {
        if (PermissionProvider.isExternalStoragePermissionGranted(mActivity)) {
            mActivity.startActivityForResult(new Intent()
                    .setAction(Intent.ACTION_GET_CONTENT)
                    .setType("video/*"), requestCode);
        } else {
            PermissionProvider.allowExternalStoragePermission(mActivity, EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    public static void chooseMultiplePhoto(@NonNull Activity mActivity, int requestCode) {
        if (PermissionProvider.isExternalStoragePermissionGranted(mActivity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mActivity.startActivityForResult(new Intent()
                        .setAction(Intent.ACTION_GET_CONTENT)
                        .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        .setType("image/*"), requestCode);
            } else {
                mActivity.startActivityForResult(new Intent()
                        .setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"), requestCode);
            }
        } else {
            PermissionProvider.allowExternalStoragePermission(mActivity, EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    public static void chooseMedia(@NonNull Activity mActivity, int requestCode) {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        mActivity.startActivityForResult(chooserIntent, requestCode);
    }

    public static void createDirectories(@NonNull File file) {
        if (!file.exists() && !file.mkdirs()) {
            file.mkdirs();
        }
    }

    public static void createDirectory(@NonNull File file) {
        if (!file.exists() && !file.mkdirs()) {
            file.mkdir();
        }
    }

    public static File getDisc(@NonNull String directory) {
        return Environment.getExternalStoragePublicDirectory(directory);
    }

    @NonNull
    public static File getDisc(@NonNull String parent_directory, @NonNull String current_directory) {
        return new File(getDisc(parent_directory), current_directory);
    }

    @NonNull
    public static File getDisc(@NonNull String parent_directory, @NonNull String child_directory, @NonNull String current_directory) {
        return new File(getDisc(parent_directory, child_directory), current_directory);
    }

    @NonNull
    private static String getFileName(@NonNull String file_type, @NonNull String file_extension) {
        String name = DateConverter.toDate(System.currentTimeMillis(), KeyFormat.FORMAT_IMAGE_NAMING_DATE);
        return getFileName(file_type, file_extension, name);
    }

    @NonNull
    private static String getFileName(@NonNull String file_type, @NonNull String file_extension, @NonNull String simple_file_name) {
        return file_type + simple_file_name + "." + file_extension;
    }

    public static String getFileExtension(@NonNull String url) {
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    public static String getFileExtension(@NonNull Context mContext, @NonNull Uri uri) {
        ContentResolver contentResolver = mContext.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @NonNull
    public static SavedTask saveImage(@NonNull Context context, @NonNull ImageView mImageView) {
        return saveImage(context, mImageView, Environment.DIRECTORY_PICTURES);
    }

    @NonNull
    public static SavedTask saveImage(@NonNull Context context, @NonNull ImageView mImageView, @NonNull String directory) {
        final BitmapDrawable draw = (BitmapDrawable) mImageView.getDrawable();
        if (draw != null) {
            Bitmap bitmap = draw.getBitmap();
            return saveImage(context, bitmap, directory);
        } else {
            return new SavedTask(new Exception("Photo isn't downloadable."));
        }
    }

    @NonNull
    public static SavedTask saveImage(@NonNull Context context, @NonNull Bitmap bitmap) {
        return saveImage(context, bitmap, getDisc(Environment.DIRECTORY_PICTURES));
    }

    @NonNull
    public static SavedTask saveImage(@NonNull Context context, @NonNull Bitmap bitmap, @NonNull String type) {
        return saveImage(context, bitmap, getDisc(type));
    }

    @NonNull
    public static SavedTask saveImage(@NonNull Context context, @NonNull Bitmap bitmap, @NonNull File disc) {
        return saveImage(context, bitmap, disc, FILE_EXTENSION_JPG);
    }

    @NonNull
    public static SavedTask saveImage(@NonNull Context context, @NonNull Bitmap bitmap, @NonNull File disc, @NonNull String file_extension) {

        String file_name = getFileName(FILE_TYPE_IMG, file_extension);
        File file = new File(disc, file_name);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            scanFile(context, Uri.fromFile(file));
            return new SavedTask(true);
        } catch (Exception e) {
            return new SavedTask(e);
        }
    }

    public static void saveImage(@NonNull Context context, @NonNull Bitmap bitmap, @NonNull OnSavedListener listener) {
        listener.onSavedTask(saveImage(context, bitmap));
    }

    public static void saveImage(@NonNull Context context, @NonNull ImageView view, @NonNull OnSavedListener listener) {
        listener.onSavedTask(saveImage(context, view));
    }

    public static void saveImages(@NonNull Context context, @NonNull ArrayList<ImageView> views) {
        saveImages(context, views, null);
    }

    public static void saveImages(@NonNull Context context, @NonNull ArrayList<ImageView> views, @Nullable OnSavedListener listener) {
        saveImages(context, views, listener, 3000);
    }

    public static void saveImages(@NonNull Context context, @NonNull ArrayList<ImageView> views, @Nullable OnSavedListener listener, int duration) {
        new RunnableHelper(duration, views.size(), true) {
            @Override
            public void onLoad(int index) {
                SavedTask task = saveImage(context, views.get(index));
                if (listener != null) {
                    listener.onSavedTask(task.setIndex(index).setCompleted(isCompleted()));
                }
            }
        };
    }

    public static void scanFile(@NonNull Context context, @NonNull Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);
    }

    public interface OnSavedListener {
        void onSavedTask(@NonNull SavedTask task);
    }

    public static class SavedTask {

        @NonNull
        private final Exception exception;
        private final boolean successful;
        private boolean completed;
        private int index;

        public SavedTask(boolean successful) {
            this.exception = new Exception();
            this.successful = successful;
        }

        public SavedTask(@NonNull Exception exception) {
            this.exception = exception;
            this.successful = false;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public boolean isCompleted() {
            return completed;
        }

        public SavedTask setCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public int getIndex() {
            return index;
        }

        public SavedTask setIndex(int index) {
            this.index = index;
            return this;
        }

        @NonNull
        public Exception getException() {
            return exception;
        }

        public String getMessage() {
            return exception.getMessage();
        }

    }
}
