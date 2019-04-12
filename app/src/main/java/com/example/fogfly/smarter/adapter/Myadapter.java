package com.example.fogfly.smarter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author Zaki Xue
 * @time 2019/1/24 16:42
 * @description 主界面适配器
 */
public class Myadapter extends FragmentPagerAdapter{
    ArrayList<Fragment> mFragments;
    public Myadapter(FragmentManager fm , ArrayList<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
