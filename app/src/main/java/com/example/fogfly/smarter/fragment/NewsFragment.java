package com.example.fogfly.smarter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.adapter.IndicatorAdapter;
import com.example.fogfly.smarter.entity.NewsLink;
import com.example.fogfly.smarter.utils.StaticClass;
import com.example.fogfly.smarter.view.SimpleViewpagerIndicator;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/24 16:34
 * @description
 */
public class NewsFragment extends Fragment {

    private SimpleViewpagerIndicator indicator;
    //String[] pageTitles = {"头条", "社会", "国内", "国际","娱乐","体育","军事","科技","财经","时尚"};
    private List<NewsLink> mList = new ArrayList<>();
    private ViewPager viewpager;
    private ImageButton news_next;
    //下拉刷新


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, null);
        //  //请求服务器获取数据
        String url = StaticClass.news_category;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                System.out.println("json:" + t);
                try {
                    parseJson(t);    //解析json数据
                    //要跑到主线程中去更新UI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            initindicator(view);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }
        });
        initview(view);
        // initindicator(view);
        return view;
    }

    /**
     * 新闻导航栏
     *
     * @param view
     */
    private void initindicator(View view) {

        indicator = (SimpleViewpagerIndicator) view.findViewById(R.id.indicator);
        viewpager = (ViewPager) view.findViewById(R.id.news_viewpager);
        //这里设置适配器，在适配器里请求数据
            viewpager.setAdapter(new IndicatorAdapter(getChildFragmentManager(), mList));
            //  viewpager.getAdapter().notifyDataSetChanged();
            //        indicator.setExpand(true)//设置tab宽度为包裹内容还是平分父控件剩余空间，默认值：false,包裹内容
            //                .setIndicatorWrapText(false)//设置indicator是与文字等宽还是与整个tab等宽，默认值：true,与文字等宽
            //                .setIndicatorColor(Color.parseColor("#ff3300"))//indicator颜色
            //                .setIndicatorHeight(2)//indicator高度
            //                .setShowUnderline(true, Color.parseColor("#dddddd"), 2)//设置是否展示underline，默认不展示
            //                .setShowDivider(true, Color.parseColor("#dddddd"), 10, 1)//设置是否展示分隔线，默认不展示
            //                .setTabTextSize(16)//文字大小
            //                .setTabTextColor(Color.parseColor("#ff999999"))//文字颜色
            //                .setTabTypeface(null)//字体
            //                .setTabTypefaceStyle(Typeface.NORMAL)//字体样式：粗体、斜体等
            //                .setTabBackgroundResId(0)//设置tab的背景
            //                .setTabPadding(0)//设置tab的左右padding
            //                .setSelectedTabTextSize(20)//被选中的文字大小
            //                .setSelectedTabTextColor(Color.parseColor("#ff3300"))//被选中的文字颜色
            //                .setSelectedTabTypeface(null)
            //                .setSelectedTabTypefaceStyle(Typeface.BOLD)
            //                .setScrollOffset(120);//滚动偏移量

            indicator.setSelectedTabTextColor(Color.parseColor("#ff3300"));
            indicator.setViewPager(viewpager);
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
                @Override
                public void onPageSelected(int position) {
                }
                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

    }

    /**
     * 解析新闻导航栏json
     *
     * @param t
     */
    private void parseJson(String t) throws JSONException {
        //1、声明JSONObject 对象
        JSONObject jsonObject = new JSONObject(t);
        //2、获取JSONObject 数据
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray = result.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            NewsLink newsLink = new NewsLink();
            String title = object.getString("title");
            String name = object.getString("name");
            newsLink.setTitle(title);
            newsLink.setName(name);
            mList.add(newsLink);
            System.out.println(mList.get(i).getTitle());
            //            System.out.println("tittle"+newsLink.getTitle());
        }
    }

    /**
     * 初始化界面
     *
     * @param view
     */
    private void initview(View view) {
        news_next = (ImageButton) view.findViewById(R.id.news_next);
        news_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewpager.getCurrentItem();
                currentItem++;
                viewpager.setCurrentItem(currentItem);
            }
        });


    }


}



//        new RxVolley.Builder().url(url)
//                .callback(new HttpCallback() {
//                    @Override
//                    public void onSuccess(String t) {
//                        System.out.println("json:" + t);
//                        try {
//                            parseJson(t);    //解析json数据
//                            //要跑到主线程中去更新UI
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //更新UI
//                                    initindicator(view);
//                                }
//                            });
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        super.onSuccess(t);
//                    }
//                })
//         //请求类型，如果不加默认为GET请求方式，可选项：POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
//            .httpMethod(RxVolley.Method.GET)
//                //设置缓存有效时间，单位分钟，默认是GET请求5分钟, POST请求不缓存
//                .cacheTime(6)
//                //请求超时时间，如果不设置则使用重连策略的超时时间，默认3000ms
//                .timeout(3000)
//                //为了更真实的模拟网络，如果读取缓存，延迟一段时间再返回缓存内容
//                .delayTime(1000)
//                //内容参数传递形式，如果不加默认为FORM表单提交，可选项JSON内容
//                .contentType(RxVolley.ContentType.FORM)
//                //是否启用缓存，默认是GET请求缓存5分钟，POST请求不缓存
//                .shouldCache(true)
//                //重连策略，不传则使用默认重连策略，Volley默认的重连策略是timeout=3000，重试1次
//                .retryPolicy(new DefaultRetryPolicy())
//                //是否使用服务器控制的缓存有效期即cookie有效期(为true时mCacheTime无效)
//                .useServerControl(false)
//                //编码格式，默认为UTF-8
//                .encoding("UTF-8")
//                //执行请求操作
//                .doTask();