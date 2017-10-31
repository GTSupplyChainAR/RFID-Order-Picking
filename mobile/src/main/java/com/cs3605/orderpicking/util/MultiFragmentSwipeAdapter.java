package com.cs3605.orderpicking.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MultiFragmentSwipeAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> mFragmentList;

    public MultiFragmentSwipeAdapter(FragmentManager fm, List<T> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public T getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}