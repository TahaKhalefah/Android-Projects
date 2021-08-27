package com.tahadroid.quraanapp.pojo;

import com.tahadroid.quraanapp.ui.fragment.BaseFragment;


public class MyTab {
    private BaseFragment baseFragment;
    private Category category;

    public MyTab(BaseFragment baseFragment, Category category) {
        this.baseFragment = baseFragment;
        this.category = category;
    }

    public BaseFragment getBaseFragment() {
        return baseFragment;
    }

    public void setBaseFragment(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
