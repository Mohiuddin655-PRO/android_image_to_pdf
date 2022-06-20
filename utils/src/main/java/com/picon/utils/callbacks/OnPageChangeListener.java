package com.picon.utils.callbacks;

import androidx.viewpager.widget.ViewPager;

public interface OnPageChangeListener extends ViewPager.OnPageChangeListener {

    @Override
    default void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    void onPageSelected(int position);

    @Override
    default void onPageScrollStateChanged(int state) {
    }
}
