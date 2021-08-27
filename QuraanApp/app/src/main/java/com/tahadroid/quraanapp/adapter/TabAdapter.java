package com.tahadroid.quraanapp.adapter;

import com.tahadroid.quraanapp.pojo.MyTab;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    List<MyTab> myTabList = new ArrayList<>();

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return myTabList.get(position).getBaseFragment();
    }

    public void addTab(MyTab tab) {
        myTabList.add(tab);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return myTabList.get(position).getCategory().getmName();
    }

    @Override
    public int getCount() {
        return myTabList.size();
    }
}
