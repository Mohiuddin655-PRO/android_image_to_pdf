package com.picon.utils.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> titleList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void addFragment(@NonNull Fragment fragment) {
        addFragment(fragment, "");
    }

    public void addFragment(@NonNull Fragment fragment, @NonNull String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    public void addFragment(@NonNull Fragment fragment, @NonNull String title, int index) {
        if (fragmentList.size() > index) {
            fragmentList.add(index, fragment);
            titleList.add(index, title);
        } else {
            addFragment(fragment, title);
        }
    }
}
