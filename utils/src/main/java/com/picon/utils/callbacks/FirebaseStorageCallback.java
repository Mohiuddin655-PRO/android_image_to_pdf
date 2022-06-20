package com.picon.utils.callbacks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.picon.utils.constains.ErrorCode;
import com.picon.utils.converters.Converter;
import com.picon.utils.providers.KeyProvider;
import com.picon.utils.providers.TaskProvider;
import com.picon.utils.response.Response;
import com.picon.utils.services.NetworkService;
import com.picon.utils.validators.Validator;

import java.util.ArrayList;

public class FirebaseStorageCallback {

    private static final String TAG = FirebaseStorageCallback.class.getSimpleName();

    private static final int CODE_DELETE = 10010;
    private static final int CODE_DOWNLOAD = 10020;
    private static final int CODE_UPLOAD = 10030;
    public final Activity mActivity;
    public final NetworkService mNetworkService;
    private final ArrayList<String> mFinalUrls = new ArrayList<>();
    private final ArrayList<Uri> mFinalUris = new ArrayList<>();
    private final StorageReference mStorageReference;

    public FirebaseStorageCallback(@NonNull Activity activity) {
        mActivity = activity;
        mNetworkService = NetworkService.getInstance(activity);
        mStorageReference = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    public static FirebaseStorageCallback getInstance(@NonNull Activity activity) {
        return new FirebaseStorageCallback(activity);
    }

    @NonNull
    private StorageReference getFilePath(@NonNull String path) {
        return mStorageReference.child(path).child(KeyProvider.generateKey());
    }

    public void deleteRequestByUrl(@NonNull String url) {
        deleteRequestByUrl(url, response -> Log.e(TAG, "deleteByUrl: " + response.getMessage(), response.getException()));
    }

    public void deleteRequestByUrl(@NonNull String url, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_DELETE);

        if (!mNetworkService.isInternetConnected()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            final StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

            reference.delete()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void deleteRequestByUrls(@NonNull ArrayList<String> urls) {
        deleteRequestByUrls(urls, response -> Log.e(TAG, "deleteByUrls: " + response.getMessage(), response.getException()));
    }

    public void deleteRequestByUrls(@NonNull ArrayList<String> urls, @NonNull OnCallbackResponseListener<Void> listener) {

        this.mFinalUrls.clear();

        final Response<Void> response = new Response<>(CODE_DELETE);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (String url : urls) {

                final StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

                reference
                        .delete()
                        .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                        .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                        .addOnSuccessListener(unused -> {
                            this.mFinalUrls.add("null");
                            if (TaskProvider.isComplete(urls.size(), mFinalUrls.size())) {
                                listener.onResponse(response.setResult(null));
                            }
                        });
            }
        }
    }

    public void downloadRequestForBitmap(@NonNull String url, long byteQuality, @NonNull OnCallbackResponseListener<Bitmap> listener) {

        final Response<Bitmap> response = new Response<>(CODE_DOWNLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            final StorageReference fileReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

            fileReference.getBytes(byteQuality)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, bytes -> {
                        Bitmap bitmap = Converter.toBitmap(bytes);
                        listener.onResponse(response.setResult(bitmap));
                    });
        }
    }

    public void downloadRequestForUris(@NonNull ArrayList<String> urls, long byteQuality, @NonNull OnCallbackResponseListener<ArrayList<Uri>> listener) {

        final Response<ArrayList<Uri>> response = new Response<>(CODE_DOWNLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (String url : urls) {

                final StorageReference fileReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

                fileReference.getBytes(byteQuality)
                        .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                        .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                        .addOnSuccessListener(mActivity, bytes -> {
                            Bitmap bitmap = Converter.toBitmap(bytes);
                            Uri uri = Converter.toUri(mActivity.getApplicationContext(), bitmap);
                            this.mFinalUris.add(0, uri);
                            if (TaskProvider.isComplete(urls.size(), mFinalUris.size())) {
                                listener.onResponse(response.setResult(mFinalUris));
                            }
                        });
            }
        }
    }

    public void downloadRequestForUri(@NonNull String url, long byteQuality, @NonNull OnCallbackResponseListener<Uri> listener) {

        final Response<Uri> response = new Response<>(CODE_DOWNLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            final StorageReference fileReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

            fileReference.getBytes(byteQuality)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, bytes -> {
                        Bitmap bitmap = Converter.toBitmap(bytes);
                        Uri uri = Converter.toUri(mActivity.getApplicationContext(), bitmap);
                        listener.onResponse(response.setResult(uri));
                    });
        }
    }

    public void uploadRequestForUrl(@NonNull String path, Uri uri, @NonNull OnCallbackResponseListener<String> listener) {

        final Response<String> response = new Response<>(CODE_UPLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else if (!Validator.isValidString(path)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else if (!Validator.isValidObject(uri)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else {

            final StorageReference fileReference = getFilePath(path);
            final UploadTask uploadTask = fileReference.putFile(uri);

            uploadTask
                    .addOnProgressListener(mActivity, snapshot -> listener.onResponse(response.setProgress(TaskProvider.getProgress(snapshot))))
                    .addOnPausedListener(mActivity, snapshot -> listener.onResponse(response.setErrorCode(ErrorCode.PAUSED)))
                    .continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            if (task.getException() != null) throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    })
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(downloadUri -> listener.onResponse(response.setResult(Converter.toString(downloadUri))));
        }
    }

    public void uploadRequestForUrls(@NonNull String path, @NonNull ArrayList<Uri> uris, @NonNull OnCallbackResponseListener<ArrayList<String>> listener) {

        final Response<ArrayList<String>> response = new Response<>(CODE_UPLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else if (!Validator.isValidString(path)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else if (!Validator.isValidList(uris)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else if (!mNetworkService.isInternetConnected()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (Uri uri : uris) {

                if (!Validator.isValidObject(uri)) {
                    listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
                } else {

                    final StorageReference fileReference = getFilePath(path);
                    final UploadTask uploadTask = fileReference.putFile(uri);

                    uploadTask
                            .addOnPausedListener(mActivity, snapshot -> listener.onResponse(response.setErrorCode(ErrorCode.PAUSED)))
                            .addOnProgressListener(mActivity, snapshot -> listener.onResponse(response.setProgress(TaskProvider.getProgress(snapshot))))
                            .continueWithTask(task -> {
                                if (!task.isSuccessful()) {
                                    if (task.getException() != null) throw task.getException();
                                }
                                return fileReference.getDownloadUrl();
                            })
                            .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                            .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                            .addOnSuccessListener(downloadUri -> {
                                this.mFinalUrls.add(0, Converter.toString(downloadUri));
                                if (TaskProvider.isComplete(uris.size(), mFinalUrls.size())) {
                                    listener.onResponse(response.setResult(mFinalUrls));
                                }
                            });
                }
            }
        }
    }

    public void uploadRequestWithoutProgressForUrl(@NonNull String path, Uri uri, @NonNull OnCallbackResponseListener<String> listener) {

        final Response<String> response = new Response<>(CODE_UPLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else if (!Validator.isValidString(path)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else if (!Validator.isValidObject(uri)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else {

            final StorageReference fileReference = getFilePath(path);
            final UploadTask uploadTask = fileReference.putFile(uri);

            uploadTask
                    .continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            if (task.getException() != null) throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    })
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(downloadUri -> listener.onResponse(response.setResult(Converter.toString(downloadUri))));
        }
    }

    public void uploadRequestWithoutProgressForUrls(@NonNull String path, @NonNull ArrayList<Uri> uris, @NonNull OnCallbackResponseListener<ArrayList<String>> listener) {

        final Response<ArrayList<String>> response = new Response<>(CODE_UPLOAD);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else if (!Validator.isValidString(path)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else if (!Validator.isValidList(uris)) {
            listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
        } else if (!mNetworkService.isInternetConnected()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (Uri uri : uris) {

                if (!Validator.isValidObject(uri)) {
                    listener.onResponse(response.setErrorCode(ErrorCode.NULLABLE_OBJECT));
                } else {

                    final StorageReference fileReference = getFilePath(path);
                    final UploadTask uploadTask = fileReference.putFile(uri);

                    uploadTask.continueWithTask(task -> {
                                if (!task.isSuccessful()) {
                                    if (task.getException() != null) throw task.getException();
                                }
                                return fileReference.getDownloadUrl();
                            })
                            .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                            .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                            .addOnSuccessListener(downloadUri -> {
                                this.mFinalUrls.add(0, Converter.toString(downloadUri));
                                if (TaskProvider.isComplete(uris.size(), mFinalUrls.size())) {
                                    listener.onResponse(response.setResult(mFinalUrls));
                                }
                            });
                }
            }
        }
    }

    public boolean isInternetUnavailable() {
        return !mNetworkService.isInternetConnected();
    }

    public interface OnCallbackResponseListener<T> {
        void onResponse(@NonNull Response<T> response);
    }

}
