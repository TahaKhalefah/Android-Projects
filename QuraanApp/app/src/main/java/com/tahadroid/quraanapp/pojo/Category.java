package com.tahadroid.quraanapp.pojo;

public class Category {
  private   String mName;
    private int mId;

    public Category(String mName, int mId) {
        this.mName = mName;
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
