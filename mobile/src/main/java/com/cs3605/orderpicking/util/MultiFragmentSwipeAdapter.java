package com.cs3605.orderpicking.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MultiFragmentSwipeAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> fragmentList;

    public MultiFragmentSwipeAdapter(FragmentManager fm, List<T> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public void setFragmentList(List<T> fragmentList) {
        this.fragmentList = fragmentList;
    }

    @Override
    public T getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}