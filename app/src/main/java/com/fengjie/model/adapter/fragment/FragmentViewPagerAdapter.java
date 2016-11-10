package com.fengjie.model.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments;

    /**
     * 构造方法初始化
     * @param fm 由Activity赋予管理权限
     * @param fragments 赋入Fragment对象集合
     */
    public FragmentViewPagerAdapter ( FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        mFragments=fragments;
    }

    /**
     * 由位置对应获得Fragment的实例
     * @param position
     * @return
     */
    @Override
    public Fragment getItem( int position) {
        return mFragments.get(position);
    }

    /**
     * 返回Fragment的数量
     * @return
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    private boolean noScroll = false;



    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }




}
