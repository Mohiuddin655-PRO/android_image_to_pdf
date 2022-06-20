package com.picon.utils.helpers;

import android.text.Editable;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.picon.utils.builders.LogBuilder;
import com.picon.utils.callbacks.OnTextChangedListener;
import com.picon.utils.constains.ErrorCode;
import com.picon.utils.response.Response;
import com.picon.utils.validators.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class IntelligentAdapterHelper<T, VH extends RecyclerView.ViewHolder, Options extends ViewHolderHelper.Options<T>> extends AdapterHelper<T, VH, Options> {

    private static final String TAG_ADAPTER_API_RESPONSE = "api_response";

    private final MutableLiveData<ArrayList<Response<ArrayList<T>>>> mResponsesLiveData = new MutableLiveData<>();
    private final ArrayList<Response<ArrayList<T>>> mResponses = new ArrayList<>();

    private final ArrayList<String> mKeys = new ArrayList<>();
    private final ArrayList<T> mTemporaryList = new ArrayList<>();

    private final ObservableBoolean mLoadingState = new ObservableBoolean(false);
    private final ObservableBoolean mCompleteState = new ObservableBoolean(false);

    private OnNestedPagingCallback mSmartPagingCallback;

    private boolean isQueryLoading;

    private int mThreshold;
    private int mDuration;

    public IntelligentAdapterHelper() {
    }

    public IntelligentAdapterHelper(@NonNull Options options) {
        super(options);
    }

    @NonNull
    public ArrayList<String> getKeys() {
        return mKeys;
    }

    public void clear() {
        super.clear();
        mKeys.clear();
        mCompleteState.set(false);
        mLoadingState.set(false);
    }

    public abstract void setItem(@NonNull T t);

    public abstract void setItem(@NonNull T t, int position);

    public abstract void setItems(@NonNull ArrayList<T> ts);

    public final void setItem(@NonNull T item, @NonNull String key) {
        super.setItem(item);
        mKeys.add(key);
    }

    public final void setItem(@NonNull T item, @NonNull String key, int position) {
        super.setItem(item, position);
        if (position < mKeys.size()) {
            mKeys.add(position, key);
        } else {
            mKeys.add(0, key);
        }
    }

    public final void setItem(@NonNull T item, boolean checked) {
        if (checked) setItem(item);
    }

    public final void setItems(@NonNull List<T> items, @NonNull OnItemCheckedListener<T> listener) {
        setItems(items, listener, false);
    }

    public final void setItems(@NonNull List<T> items, @NonNull OnItemCheckedListener<T> listener, boolean forReserve) {
        if (items.size() > 0) {
            new RunnableHelper(mDuration, items.size(), true) {
                @Override
                public void onLoad(int index) {
                    T item = forReserve ? items.get(getInvertedIndex()) : items.get(index);
                    setItem(item, listener.isChecked(item));
                    setLoadingState(!isCompleted());
                }

                @Override
                public void onCompleted() {
                    if (mSmartPagingCallback != null) {
                        mSmartPagingCallback.startPaging(getItemCount() < mThreshold);
                    }
                }
            };
        }
    }

    public final void setItems(@NonNull List<T> items, @NonNull OnItemKeyCallbackListener<T> callback) {
        setItems(items, callback, false);
    }

    public final void setItems(@NonNull List<T> items, @NonNull OnItemKeyCallbackListener<T> callback, boolean forReserve) {
        if (items.size() > 0) {
            new RunnableHelper(mDuration, items.size(), true) {
                @Override
                public void onLoad(int index) {
                    T item = forReserve ? items.get(getInvertedIndex()) : items.get(index);
                    setItem(item, callback.isChecked(item) && !mKeys.contains(callback.key(item)));
                    setLoadingState(!isCompleted());
                }

                @Override
                public void onCompleted() {
                    if (mSmartPagingCallback != null) {
                        mSmartPagingCallback.startPaging(getItemCount() < mThreshold);
                    }
                }
            };
        }
    }

    @Override
    public final void setCollection(@NonNull Collection<T> collection) {
        clear();
        setItems((List<T>) collection);
    }

    public void setCompleteState(boolean state) {
        this.mCompleteState.set(state);
    }

    public void setCompleteState(long duration) {
        new RunnableHelper(duration) {
            @Override
            public void onLoad(int index) {
                mCompleteState.set(true);
            }
        };
    }

    public void setLoadingState(long duration) {
        new RunnableHelper(duration) {
            @Override
            public void onLoad(int index) {
                mLoadingState.set(true);
            }
        };
    }

    public void setLoadingState(boolean state) {
        this.mLoadingState.set(state);
    }

    public void setPaging(@NonNull RecyclerView view, @NonNull OnPagingListener mListener) {
        setPaging(new OnRecyclerPagingCallback(view, mListener, mThreshold));
    }

    public void setPaging(@NonNull OnRecyclerPagingCallback callback) {
    }

    public void setPaging(@NonNull NestedScrollView view, @NonNull OnPagingListener mListener) {
        setPaging(new OnNestedPagingCallback(view, mListener));
    }

    public void setPaging(@NonNull OnNestedPagingCallback callback) {
        mSmartPagingCallback = callback;
        mSmartPagingCallback.setCompleteState(mCompleteState);
        mSmartPagingCallback.setLoadingState(mLoadingState);
    }

    public void setApiResponse(@NonNull Response<ArrayList<T>> response) {
        setLoadingState(!response.isLoaded());
        setCompleteState(response.getErrorCode() == ErrorCode.RESULT_NOT_FOUND && getItemCount() > 0);
        if (response.getResult() != null && !response.getResult().isEmpty()) {
            mResponses.add(0, response);
            mResponsesLiveData.setValue(mResponses);
            setItems(response.getResult());
        }

        LogBuilder.getInstance(TAG_ADAPTER_API_RESPONSE)
                .put("Status  ", response.isLoading() ? "Loading..." : getItemCount() > 0 && response.getErrorCode() == ErrorCode.RESULT_NOT_FOUND ? "Completed" : "Loaded")
                .put("Result  ", response.getResult())
                .put("Size  \t ", response.getResult() != null ? response.getResult().size() : 0)
                .put("Snapshot", response.getSnapshot())
                .put("Feedback", response.getFeedback())
                .attachStart("Error \t ").attach("Code", response.getErrorCode()).attachEnd("Message", response.getMessage(response.getErrorCode()))
                .build();
    }

    public void setLoadingDuration(int duration) {
        this.mDuration = Math.max(duration, 0);
    }

    public void setThreshold(int threshold) {
        this.mThreshold = Math.max(threshold, 50);
    }

    public void attachSearchView(@NonNull EditText view, @NonNull OnQueryListener<T> listener) {
        view.addTextChangedListener(new OnTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                search(String.valueOf(editable).trim(), listener);
            }
        });
    }

    public void search(@NonNull String query, @NonNull OnQueryListener<T> listener) {

        if (!isQueryLoading) {

            if (mTemporaryList.size() <= 0) mTemporaryList.addAll(getCurrentList());

            if (Validator.isValidList(mTemporaryList)) {
                if (Validator.isValidString(query)) {
                    clear();
                    isQueryLoading = true;
                    listener.state(true);
                    new RunnableHelper(mDuration, mTemporaryList.size(), true) {
                        @Override
                        public void onLoad(int index) {
                            T item = mTemporaryList.get(index);
                            if (item != null) {
                                String data = String.valueOf(listener.query(item)).toLowerCase();
                                setItem(item, data.contains(query));
                            }
                        }

                        @Override
                        public void onCompleted() {
                            isQueryLoading = false;
                            listener.state(false);
                        }
                    };
                } else {
                    listener.state(false);
                    setItems(new ArrayList<>(mTemporaryList));
                    mTemporaryList.clear();
                }
            }
        }
    }

    public enum PagingDirection {
        PAGING_HORIZONTAL, PAGING_VERTICAL, PAGING_TOP, PAGING_BOTTOM, PAGING_LEFT, PAGING_RIGHT
    }

    public interface OnQueryListener<T> {

        String query(@NonNull T t);

        default void state(boolean query) {
        }
    }

    public interface OnItemKeyCallbackListener<T> extends OnItemCheckedListener<T> {

        @NonNull
        String key(@NonNull T t);

        @Override
        default boolean isChecked(@NonNull T t) {
            return true;
        }
    }

    public interface OnItemCheckedListener<T> {
        boolean isChecked(@NonNull T t);
    }

    public interface OnPagingListener {

        void onPagingDown(int page);

        default void onPagingUp(int page) {
        }

        default void onPagingLeft(int page) {
        }

        default void onPagingRight(int page) {
        }

    }

    public static class OnNestedPagingCallback implements NestedScrollView.OnScrollChangeListener {

        private static final String TAG = OnNestedPagingCallback.class.getSimpleName();

        private final OnPagingListener mListener;
        private ObservableBoolean mLoadingState = new ObservableBoolean(false);
        private ObservableBoolean mCompleteState = new ObservableBoolean(false);

        private PagingDirection direction = PagingDirection.PAGING_BOTTOM;

        private int mPage = 1;

        public OnNestedPagingCallback(@NonNull NestedScrollView view, @NonNull OnPagingListener mListener) {
            this.mListener = mListener;
            view.setOnScrollChangeListener(this);
        }

        public OnNestedPagingCallback(@NonNull NestedScrollView view, @NonNull OnPagingListener listener, @NonNull ObservableBoolean loadingState, @NonNull ObservableBoolean completeState) {
            this.mListener = listener;
            this.mLoadingState = loadingState;
            this.mCompleteState = completeState;
            view.setOnScrollChangeListener(this);
        }

        public void setCompleteState(boolean state) {
            this.mCompleteState.set(state);
        }

        public void setCompleteState(long duration) {
            new RunnableHelper(duration) {
                @Override
                public void onLoad(int index) {
                    mCompleteState.set(true);
                }
            };
        }

        public void setCompleteState(@NonNull ObservableBoolean state) {
            this.mCompleteState = state;
        }

        public void setLoadingState(long duration) {
            new RunnableHelper(duration) {
                @Override
                public void onLoad(int index) {
                    mLoadingState.set(true);
                }
            };
        }

        public void setLoadingState(boolean state) {
            this.mLoadingState.set(state);
        }

        public void setLoadingState(@NonNull ObservableBoolean state) {
            this.mLoadingState = state;
        }

        public PagingDirection getDirection() {
            return direction;
        }

        public void setDirection(@NonNull PagingDirection direction) {
            this.direction = direction;
        }

        @Override
        public void onScrollChange(@NonNull NestedScrollView view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            long total_height = view.getChildAt(0).getMeasuredHeight();
            long previous_height = view.getMeasuredHeight();

            if (scrollY > oldScrollY && direction == PagingDirection.PAGING_BOTTOM) {
                startPaging(scrollY == (total_height - previous_height));
            }

        }

        public void startPaging(boolean start) {

            if (start) {

                boolean isLoading = mLoadingState.get();
                boolean isComplete = mCompleteState.get();

                if (!isLoading && !isComplete) {
                    mPage++;
                    mListener.onPagingDown(mPage);
                }

                LogBuilder.getInstance(TAG)
                        .put("Loading", isLoading)
                        .put("Complete", isComplete)
                        .put("Page", mPage)
                        .put("Status", !isLoading && !isComplete)
                        .put("Paging Direction", direction)
                        .build();
            }
        }

    }

    public static class OnRecyclerPagingCallback extends RecyclerView.OnScrollListener {

        private static final String TAG = OnRecyclerPagingCallback.class.getSimpleName();

        private final RecyclerView.LayoutManager mLayoutManager;
        private final OnPagingListener mListener;
        private final long mThreshold;
        private boolean isLoading = true;
        private long mPreviousTotal = 0;
        private int mPage = 1;

        public OnRecyclerPagingCallback(@NonNull RecyclerView view, @NonNull OnPagingListener listener, int threshold) {
            this.mLayoutManager = view.getLayoutManager();
            this.mListener = listener;
            this.mThreshold = threshold;
            view.addOnScrollListener(this);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mLayoutManager != null) {
                if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    onStaggeredGridLayoutManagerScrolled((StaggeredGridLayoutManager) mLayoutManager);
                } else {
                    onLinearLayoutManagerScrolled((LinearLayoutManager) mLayoutManager);
                }
            }
        }

        private void onLinearLayoutManagerScrolled(@NonNull LinearLayoutManager manager) {

            long totalItemCount = manager.getItemCount();
            long visibleItemCount = manager.getChildCount();
            long firstVisibleItem = manager.findFirstVisibleItemPosition();

            long a = totalItemCount - visibleItemCount;
            long b = firstVisibleItem + mThreshold;

            if (isLoading && totalItemCount > mPreviousTotal) {
                isLoading = false;
                mPreviousTotal = totalItemCount;
            }

            if (!isLoading && a <= b) {
                isLoading = true;
                mPage++;
                mListener.onPagingDown(mPage);
            }

            LogBuilder.getInstance(TAG)
                    .put("Loading", isLoading)
                    .put("Page", mPage)
                    .put("Status", !isLoading && a <= b)
                    .put("Type", "paging_down")
                    .build();
        }

        private void onStaggeredGridLayoutManagerScrolled(@NonNull StaggeredGridLayoutManager manager) {
        }
    }

}
