package com.picon.utils.callbacks;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SearchView;

public interface OnTextChangedListener extends TextWatcher, SearchView.OnQueryTextListener {

    @Override
    default boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    default boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    default void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }

    @Override
    default void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    default void afterTextChanged(Editable editable) {
    }

}
