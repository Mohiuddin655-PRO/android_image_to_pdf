package com.picon.utils.callbacks;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.picon.utils.constains.ErrorCode;
import com.picon.utils.models.KeyValue;
import com.picon.utils.providers.TaskProvider;
import com.picon.utils.response.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseDatabaseCallback extends FirebaseStorageCallback {

    private static final String TAG = FirebaseDatabaseCallback.class.getSimpleName();

    private static final int CODE_DELETE_BY_KEY = 10010;
    private static final int CODE_LOAD_BY_KEY = 10020;
    private static final int CODE_UPLOAD_BY_KEY = 10030;
    private static final int CODE_UPDATE_BY_KEY = 10030;
    private static final int CODE_LOAD_BY_LIST = 10030;

    private final ArrayList<Object> mTemporaryList = new ArrayList<>();

    private FirebaseDatabaseCallback(@NonNull Activity mActivity) {
        super(mActivity);
    }

    @NonNull
    public static FirebaseDatabaseCallback getInstance(@NonNull Activity activity) {
        return new FirebaseDatabaseCallback(activity);
    }

    public void deleteRequestByKey(@NonNull DatabaseReference reference) {
        deleteRequestByKey(reference, response -> Log.e(TAG, "deleteByKey: " + response.getMessage(), response.getException()));
    }

    public void deleteRequestByKey(@NonNull DatabaseReference reference, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_DELETE_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .removeValue()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void deleteRequestByKey(@NonNull DocumentReference reference) {
        deleteRequestByKey(reference, response -> Log.e(TAG, "deleteByKey: " + response.getMessage(), response.getException()));
    }

    public void deleteRequestByKey(@NonNull DocumentReference reference, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_DELETE_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .delete()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void deleteRequestByKeys(@NonNull DatabaseReference reference, @NonNull ArrayList<String> keys) {
        deleteRequestByKeys(reference, keys, response -> Log.e(TAG, "deleteRequestByKeys: " + response.getMessage(), response.getException()));
    }

    public void deleteRequestByKeys(@NonNull CollectionReference reference, @NonNull ArrayList<String> keys) {
        deleteRequestByKeys(reference, keys, response -> Log.e(TAG, "deleteRequestByKeys: " + response.getMessage(), response.getException()));
    }

    public void deleteRequestByKeys(@NonNull DatabaseReference reference, @NonNull ArrayList<String> keys, @NonNull OnCallbackResponseListener<Void> listener) {

        this.mTemporaryList.clear();

        final Response<Void> response = new Response<>(CODE_DELETE_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (String key : keys) {

                reference
                        .child(key)
                        .removeValue()
                        .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                        .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                        .addOnSuccessListener(unused -> {
                            this.mTemporaryList.add(true);
                            if (TaskProvider.isComplete(keys.size(), mTemporaryList.size())) {
                                listener.onResponse(response.setResult(null));
                            }
                        });

            }
        }
    }

    public void deleteRequestByKeys(@NonNull CollectionReference reference, @NonNull ArrayList<String> keys, @NonNull OnCallbackResponseListener<Void> listener) {

        this.mTemporaryList.clear();

        final Response<Void> response = new Response<>(CODE_DELETE_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (String key : keys) {

                reference
                        .document(key)
                        .delete()
                        .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                        .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                        .addOnSuccessListener(unused -> {
                            this.mTemporaryList.add(true);
                            if (TaskProvider.isComplete(keys.size(), mTemporaryList.size())) {
                                listener.onResponse(response.setResult(null));
                            }
                        });

            }
        }
    }

    public <T> void loadingRequestForDocument(@NonNull DatabaseReference reference, @NonNull Class<T> dataClass, @NonNull OnCallbackResponseListener<T> listener) {

        final Response<T> response = new Response<>(CODE_LOAD_BY_KEY);
        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {
            listener.onResponse(response);
            reference.get()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, value -> {
                        if (value != null && value.exists()) {
                            try {
                                T data = value.getValue(dataClass);
                                response.setSnapshot(value).setResult(data);
                            } catch (Exception e) {
                                response.setErrorCode(ErrorCode.FAILURE).setException(e);
                            }
                        } else {
                            response.setErrorCode(ErrorCode.RESULT_NOT_FOUND);
                        }
                        listener.onResponse(response);
                    });
        }
    }

    public <T> void loadingRequestForDocument(@NonNull DocumentReference reference, @NonNull Class<T> dataClass, @NonNull OnCallbackResponseListener<T> listener) {

        final Response<T> response = new Response<>(CODE_LOAD_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {
            listener.onResponse(response);
            reference.get()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, value -> {
                        if (value != null && value.exists()) {
                            try {
                                T data = value.toObject(dataClass);
                                response.setSnapshot(value).setResult(data);
                            } catch (Exception e) {
                                response.setErrorCode(ErrorCode.FAILURE).setException(e);
                            }
                        } else {
                            response.setErrorCode(ErrorCode.RESULT_NOT_FOUND);
                        }
                        listener.onResponse(response);
                    });
        }
    }

    public <T> void loadingRequestForCollection(@NonNull DatabaseReference reference, @NonNull Class<T> dataClass, @NonNull OnCallbackResponseListener<ArrayList<T>> listener) {
        loadingRequestForCollection((com.google.firebase.database.Query) reference, dataClass, listener);
    }

    public <T> void loadingRequestForCollection(@NonNull CollectionReference reference, @NonNull Class<T> dataClass, @NonNull OnCallbackResponseListener<ArrayList<T>> listener) {
        loadingRequestForCollection((com.google.firebase.firestore.Query) reference, dataClass, listener);
    }

    public <T> void loadingRequestForCollection(@NonNull com.google.firebase.database.Query query, @NonNull Class<T> dataClass, @NonNull OnCallbackResponseListener<ArrayList<T>> listener) {

        final Response<ArrayList<T>> response = new Response<>(CODE_LOAD_BY_LIST);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {
            listener.onResponse(response);
            query.get()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, values -> {
                        if (values != null && values.exists()) {
                            ArrayList<T> list = new ArrayList<>();
                            for (DataSnapshot value : values.getChildren()) {
                                if (value != null && value.exists()) {
                                    try {
                                        T data = value.getValue(dataClass);
                                        list.add(data);
                                    } catch (Exception e) {
                                        final String exception = response.getMessage() + "\n" + e.getMessage();
                                        response.setException(exception);
                                    }
                                }
                            }
                            response.setSnapshot(values).setResult(list);
                        } else {
                            response.setErrorCode(ErrorCode.RESULT_NOT_FOUND);
                        }
                        listener.onResponse(response);
                    });
        }
    }

    public <T> void loadingRequestForCollection(@NonNull com.google.firebase.firestore.Query query, @NonNull Class<T> dataClass, @NonNull OnCallbackResponseListener<ArrayList<T>> listener) {

        final Response<ArrayList<T>> response = new Response<>(CODE_LOAD_BY_LIST);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {
            listener.onResponse(response);
            query.get()
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, values -> {
                        if (values != null && !values.isEmpty()) {
                            ArrayList<T> list = new ArrayList<>();
                            for (DocumentSnapshot value : values) {
                                if (value != null && value.exists()) {
                                    try {
                                        T data = value.toObject(dataClass);
                                        list.add(data);
                                    } catch (Exception e) {
                                        final String exception = response.getMessage() + "\n" + e.getMessage();
                                        response.setException(exception);
                                    }
                                }
                            }
                            response.setSnapshot(values).setResult(list);
                        } else {
                            response.setErrorCode(ErrorCode.RESULT_NOT_FOUND);
                        }
                        listener.onResponse(response);
                    });
        }
    }

    public void updateRequestByKey(@NonNull DatabaseReference reference, @NonNull String key, @Nullable Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        updateRequestByKey(reference, map, response -> Log.e(TAG, "updateByKey: " + response.getMessage(), response.getException()));
    }

    public void updateRequestByKey(@NonNull DatabaseReference reference, @NonNull HashMap<String, Object> data) {
        updateRequestByKey(reference, data, response -> Log.e(TAG, "updateByKey: " + response.getMessage(), response.getException()));
    }

    public void updateRequestByKey(@NonNull DocumentReference reference, @NonNull String key, @Nullable Object value) {
        updateRequestByKey(reference, key, value, response -> Log.e(TAG, "updateByKey: " + response.getMessage(), response.getException()));
    }

    public void updateRequestByKey(@NonNull DocumentReference reference, @NonNull String key, @Nullable Object value, @NonNull OnCallbackResponseListener<Void> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        updateRequestByKey(reference, map, listener);
    }

    public void updateRequestByKey(@NonNull DocumentReference reference, @NonNull HashMap<String, Object> data) {
        updateRequestByKey(reference, data, response -> Log.e(TAG, "updateByKey: " + response.getMessage(), response.getException()));
    }

    public void updateRequestByKey(@NonNull DatabaseReference reference, @NonNull HashMap<String, Object> data, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPDATE_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .updateChildren(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void updateRequestByKey(@NonNull DocumentReference reference, @NonNull HashMap<String, Object> data, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPDATE_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .update(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void uploadRequestByKey(@NonNull DatabaseReference reference, @NonNull Object data) {
        uploadRequestByKey(reference, data, response -> Log.e(TAG, "uploadByKey: " + response.getMessage(), response.getException()));
    }

    public void uploadRequestByKey(@NonNull DocumentReference reference, @NonNull Object data) {
        uploadRequestByKey(reference, data, response -> Log.e(TAG, "uploadByKey: " + response.getMessage(), response.getException()));
    }

    public void uploadRequestByKey(@NonNull DatabaseReference reference, @NonNull HashMap<String, Object> data) {
        uploadRequestByKey(reference, data, response -> Log.e(TAG, "uploadByKey: " + response.getMessage(), response.getException()));
    }

    public void uploadRequestByKey(@NonNull DocumentReference reference, @NonNull HashMap<String, Object> data) {
        uploadRequestByKey(reference, data, response -> Log.e(TAG, "uploadByKey: " + response.getMessage(), response.getException()));
    }

    public void uploadRequestByKey(@NonNull DatabaseReference reference, @NonNull Object data, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .setValue(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void uploadRequestByKey(@NonNull DocumentReference reference, @NonNull Object data, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE);
            listener.onResponse(response);
        } else {

            reference
                    .set(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void uploadRequestByKey(@NonNull DatabaseReference reference, @NonNull HashMap<String, Object> data, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .setValue(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void uploadRequestByKey(@NonNull DocumentReference reference, @NonNull HashMap<String, Object> data, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE);
            listener.onResponse(response);
        } else {

            reference
                    .set(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(null)));
        }
    }

    public void uploadRequestWithKeyValue(@NonNull DatabaseReference reference, @NonNull ArrayList<KeyValue> keyValues) {
        uploadRequestWithKeyValue(reference, keyValues, response -> Log.e(TAG, "uploadRequestWithKeyValue: " + response.getMessage(), response.getException()));
    }

    public void uploadRequestWithKeyValue(@NonNull CollectionReference reference, @NonNull ArrayList<KeyValue> keyValues) {
        uploadRequestWithKeyValue(reference, keyValues, response -> Log.e(TAG, "uploadRequestWithKeyValue: " + response.getMessage(), response.getException()));
    }

    public void uploadRequestWithKeyValue(@NonNull DatabaseReference reference, @NonNull ArrayList<String> keys, @NonNull ArrayList<Object> values) {
        if (keys.size() > 0 && keys.size() == values.size()) {
            ArrayList<KeyValue> keyValues = new ArrayList<>();
            for (int index = 0; index < keys.size(); index++) {
                keyValues.add(new KeyValue(keys.get(0), values.get(0)));
            }
            uploadRequestWithKeyValue(reference, keyValues, response -> Log.e(TAG, "uploadRequestWithKeyValue: " + response.getMessage(), response.getException()));
        }
    }

    public void uploadRequestWithKeyValue(@NonNull CollectionReference reference, @NonNull ArrayList<String> keys, @NonNull ArrayList<Object> values) {
        if (keys.size() > 0 && keys.size() == values.size()) {
            ArrayList<KeyValue> keyValues = new ArrayList<>();
            for (int index = 0; index < keys.size(); index++) {
                keyValues.add(new KeyValue(keys.get(0), values.get(0)));
            }
            uploadRequestWithKeyValue(reference, keyValues, response -> Log.e(TAG, "uploadRequestWithKeyValue: " + response.getMessage(), response.getException()));
        }
    }

    public void uploadRequestWithKeyValue(@NonNull DatabaseReference reference, @NonNull ArrayList<KeyValue> keyValues, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (KeyValue keyValue : keyValues) {

                String key = keyValue.getKey();
                Object value = keyValue.getValue();

                reference.child(key)
                        .setValue(value)
                        .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                        .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                        .addOnSuccessListener(unused -> {
                            this.mTemporaryList.add(true);
                            if (TaskProvider.isComplete(keyValues.size(), mTemporaryList.size())) {
                                listener.onResponse(response.setResult(null));
                            }
                        });
            }
        }
    }

    public void uploadRequestWithKeyValue(@NonNull CollectionReference reference, @NonNull ArrayList<KeyValue> keyValues, @NonNull OnCallbackResponseListener<Void> listener) {

        final Response<Void> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            for (KeyValue keyValue : keyValues) {

                String key = keyValue.getKey();
                Object value = keyValue.getValue();

                if (value != null) {

                    reference.document(key)
                            .set(value)
                            .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                            .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                            .addOnSuccessListener(unused -> {
                                this.mTemporaryList.add(true);
                                if (TaskProvider.isComplete(keyValues.size(), mTemporaryList.size())) {
                                    listener.onResponse(response.setResult(null));
                                }
                            });
                }
            }
        }
    }

    public <T> void uploadRequestWithFeedback(@NonNull DatabaseReference reference, @NonNull T data, @NonNull OnCallbackResponseListener<T> listener) {

        final Response<T> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            listener.onResponse(response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE));
        } else {

            reference
                    .setValue(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(data)));
        }
    }

    public <T> void uploadRequestWithFeedback(@NonNull DocumentReference reference, @NonNull T data, @NonNull OnCallbackResponseListener<T> listener) {

        final Response<T> response = new Response<>(CODE_UPLOAD_BY_KEY);

        if (isInternetUnavailable()) {
            response.setErrorCode(ErrorCode.NETWORK_UNAVAILABLE);
            listener.onResponse(response);
        } else {

            reference
                    .set(data)
                    .addOnCanceledListener(mActivity, () -> listener.onResponse(response.setErrorCode(ErrorCode.CANCELED)))
                    .addOnFailureListener(mActivity, e -> listener.onResponse(response.setErrorCode(ErrorCode.FAILURE).setException(e)))
                    .addOnSuccessListener(mActivity, unused -> listener.onResponse(response.setResult(data)));
        }
    }

}
