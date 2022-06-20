package com.picon.utils.providers;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.picon.utils.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class Binder {

    @BindingAdapter({"app:loadImage"})
    public static void loadImage(ImageView imageView, Object image) {
        if (image != null) {
            Glide.with(imageView).load(image).placeholder(R.color.black_T5).into(imageView);
        }
    }

    @BindingAdapter({"app:loadAvatar"})
    public static void loadAvatar(ImageView imageView, Object image) {
        if (image != null) {
            Glide.with(imageView).load(image).placeholder(R.color.black_T5).into(imageView);
        }
    }

    @BindingAdapter({"app:loadBlurImage"})
    public static void loadBlurImage(ImageView imageView, Object image) {
        if (image != null) {
            Glide.with(imageView.getContext()).load(image).transition(DrawableTransitionOptions.withCrossFade()).transform(new BlurTransformation()).into(imageView);
        }
    }
}
