package com.picon.utils.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.constains.ErrorCode;
import com.picon.utils.messages.BaseMessage;

public class Response<T> {

    private final int requestCode;
    private int errorCode;
    private T result;
    private String feedback;
    private Object snapshot;
    private Exception exception;
    private double progress;
    private boolean successful;
    private boolean cancel;
    private boolean complete;
    private boolean internetError;
    private boolean valid;
    private boolean loaded;
    private boolean paused;
    private boolean nullableObject;
    private boolean stopped;
    private boolean failed;
    private boolean timeout;

    public Response() {
        this.requestCode = 0;
    }

    public Response(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    @ErrorCode
    public int getErrorCode() {
        return errorCode;
    }

    public Response<T> setErrorCode(@ErrorCode int errorCode) {
        setException(errorCode);
        return this;
    }

    public T getResult() {
        return result;
    }

    public Response<T> setResult(T result) {
        this.result = result;
        this.successful = true;
        this.complete = true;
        this.loaded = true;
        return this;
    }

    public <Result> Result getResult(@NonNull Class<Result> cls) throws IllegalAccessException, InstantiationException {
        if (cls.isInstance(result)) {
            return cls.cast(result);
        } else {
            return cls.newInstance();
        }
    }

    public String getFeedback() {
        return feedback;
    }

    public Response<T> setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public Object getSnapshot() {
        return snapshot;
    }

    public Response<T> setSnapshot(Object snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    @Nullable
    public <Snapshot> Snapshot getSnapshot(@NonNull Class<Snapshot> cls) {
        if (cls.isInstance(snapshot)) {
            return cls.cast(snapshot);
        } else {
            return null;
        }
    }

    public Exception getException() {
        return exception != null ? exception : new Exception();
    }

    public Response<T> setException(@ErrorCode int code) {
        this.errorCode = code;
        this.exception = new Exception(getMessage(code));
        this.feedback = null;
        this.complete = true;
        this.loaded = true;
        return this;
    }

    public Response<T> setException(@NonNull String message) {
        this.exception = new Exception(message);
        this.feedback = null;
        this.complete = true;
        this.loaded = true;
        return this;
    }

    public Response<T> setException(@Nullable Exception exception) {
        if (exception != null) {
            this.exception = exception;
            this.feedback = null;
            this.complete = true;
            this.loaded = true;
        }
        return this;
    }

    public String getMessage() {
        return exception != null ? exception.getMessage() : "";
    }

    public String getMessage(@ErrorCode int code) {
        switch (code) {
            case ErrorCode.CANCELED:
                this.cancel = true;
                return BaseMessage.EXCEPTION_PROCESS_CANCELED;
            case ErrorCode.FAILURE:
                this.failed = true;
                return BaseMessage.EXCEPTION_PROCESS_FAILED;
            case ErrorCode.NETWORK_UNAVAILABLE:
                this.internetError = true;
                this.valid = false;
                return BaseMessage.EXCEPTION_INTERNET_DISCONNECTED;
            case ErrorCode.NULLABLE_OBJECT:
                this.nullableObject = true;
                return BaseMessage.EXCEPTION_RESULT_NOT_VALID;
            case ErrorCode.PAUSED:
                this.paused = true;
                return BaseMessage.EXCEPTION_PROCESS_PAUSED;
            case ErrorCode.RESULT_NOT_FOUND:
                return BaseMessage.EXCEPTION_RESULT_NOT_FOUND;
            case ErrorCode.STOPPED:
                this.stopped = true;
                return BaseMessage.EXCEPTION_PROCESS_STOPPED;
            case ErrorCode.TIME_OUT:
                this.timeout = true;
                return BaseMessage.EXCEPTION_TRY_AGAIN;
            default:
                return "null";
        }
    }

    public boolean isSuccessful() {
        return successful;
    }

    public Response<T> setSuccessful(boolean successful) {
        this.successful = successful;
        this.complete = true;
        return this;
    }

    public double getProgress() {
        return progress;
    }

    public Response<T> setProgress(double progress) {
        this.progress = progress;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public Response<T> setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }

    public boolean isCancel() {
        return cancel;
    }

    public Response<T> setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public Response<T> setValid(boolean valid) {
        this.valid = valid;
        this.loaded = true;
        return this;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Response<T> setLoaded(boolean loaded) {
        this.loaded = loaded;
        return this;
    }

    public boolean isLoading() {
        return !loaded;
    }

    public boolean isInternetConnected() {
        return !internetError;
    }

    public boolean isValidException() {
        return exception != null && exception.getMessage() != null;
    }

    public boolean isInternetError() {
        return internetError;
    }

    public Response<T> setInternetError(@NonNull String message) {
        this.setException(message);
        this.internetError = true;
        this.valid = false;
        return this;
    }

    public Response<T> setInternetError(boolean internetError) {
        this.internetError = internetError;
        return this;
    }

    public boolean isPaused() {
        return paused;
    }

    public Response<T> setPaused(boolean paused) {
        this.paused = paused;
        return this;
    }

    public boolean isNullableObject() {
        return nullableObject;
    }

    public Response<T> setNullableObject(boolean nullableObject) {
        this.nullableObject = nullableObject;
        return this;
    }

    public boolean isStopped() {
        return stopped;
    }

    public Response<T> setStopped(boolean stopped) {
        this.stopped = stopped;
        return this;
    }

    public boolean isFailed() {
        return failed;
    }

    public Response<T> setFailed(boolean failed) {
        this.failed = failed;
        return this;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public Response<T> setTimeout(boolean timeout) {
        this.timeout = timeout;
        return this;
    }
}
