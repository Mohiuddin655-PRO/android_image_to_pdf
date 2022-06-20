package com.picon.utils.loaders;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnimationLoader {

    public static void itemAnimation(View view, @AnimRes int animation) {
        view.setAnimation(AnimationUtils.loadAnimation(view.getContext(), animation));
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void layoutAnimation(@NonNull RecyclerView recyclerView, @AnimRes int animation) {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), animation);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        if (recyclerView.getAdapter() != null) recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
