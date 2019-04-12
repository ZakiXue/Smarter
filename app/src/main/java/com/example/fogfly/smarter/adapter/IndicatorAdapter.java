package com.example.fogfly.smarter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fogfly.smarter.entity.NewsLink;
import com.example.fogfly.smarter.fragment.NewsPagesFragment;

import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/26 16:41
 * @description 新闻导航栏indicator的适配器
 */
public class IndicatorAdapter extends FragmentPagerAdapter {
    private List<NewsLink> mnewsList;
    String[] pageTitles = {"top", "shehui", "guonei", "guoji","yule","tiyu","junshi","keji","caijing","shishang"};
    public IndicatorAdapter(FragmentManager fm, List<NewsLink> mnewsList) {
        super(fm);
        this.mnewsList = mnewsList;
    }

    @Override
    public Fragment getItem(int position) {
        //其实实在这里请求数据
//        return  NewsPagesFragment.newInstance(pageTitles[position]);
       return  NewsPagesFragment.newInstance(mnewsList.get(position).getName());
        //return new NewsPagesFragment();
    }

    @Override
    public int getCount() {
//        return mnewsList.length;
        return mnewsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
     //   return mnewsList[position];
        return mnewsList.get(position).getTitle();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
