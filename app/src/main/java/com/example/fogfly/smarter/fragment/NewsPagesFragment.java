package com.example.fogfly.smarter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.adapter.NewsPagesAdapter;
import com.example.fogfly.smarter.entity.NewsPages;
import com.example.fogfly.smarter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/27 15:11
 * @description 新闻详情页面的fragment 搭配NewsPages适配器使用
 */
public class NewsPagesFragment extends Fragment {
    private List<NewsPages> mList = new ArrayList<>();
    private NewsPagesAdapter adapter;
    private String mContent = "???";
    private String image;
    private String image1;
    private String image2;

    private SwipeRefreshLayout swipeRefresh;


    public static NewsPagesFragment newInstance(String content) {
        NewsPagesFragment newsPagesFragment = new NewsPagesFragment();
        StringBuilder builder = new StringBuilder();
        builder.append(content);
        newsPagesFragment.mContent = builder.toString();
        return newsPagesFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_newspages, container, false);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });
        //这里是子线程请求数据，所以要做UI的同步
        //必须是请求完毕数据，解析JSON成功之后，才去更新UI
        initGet(view);

        //        for (int i = 0; i < 30; i++) {
        //            NewsPages news = new NewsPages();
        //            news.setTitle("title"+i+mContent);
        //            news.setImage(R.drawable.lllloooggggoo);
        //            news.setData("data"+i);
        //            mList.add(news);
        //        }

        //如果在这里直接更新UI，可能子线程还没有请求数据完毕，所以有时候UI上能看到数据，有时候是空的
        //initView(view);
        return view;
    }

    /**
     * 解析接口并获取数据
     * @param view
     */
    private void initGet(final View view) {
        String url = StaticClass.newspages_key + mContent+StaticClass.newspages_key1;
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
                            initView(view);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }
        });
    }

    /**
     * 解析json
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
            NewsPages newsPages = new NewsPages();
            String title = object.optString("title");
            String data = object.optString("author_name");
            String url = object.optString("url");
            image = object.optString("thumbnail_pic_s");
            newsPages.setImage(image);
            image1 = object.optString("thumbnail_pic_s02");
            newsPages.setImage1(image1);
            image2 = object.optString("thumbnail_pic_s03");
            newsPages.setImage2(image2);
            newsPages.setTitle(title);
            newsPages.setData(data);
            newsPages.setUrl(url);
            mList.add(newsPages);
        }
    }
    //    private void parseJson1(String t) throws JSONException {
    //        //1、声明JSONObject 对象
    //        JSONObject jsonObject = new JSONObject(t);
    //        //2、获取JSONObject 数据
    //        JSONObject result = jsonObject.getJSONObject("result");
    //        JSONArray jsonArray = result.getJSONArray("data");
    //        for (int i = 0; i < jsonArray.length(); i++) {
    //            JSONObject object = (JSONObject) jsonArray.get(i);
    //            NewsPages newsPages = new NewsPages();
    //            String url = object.optString("url");
    //            newsPages.setUrl(url);
    //            mList.add(newsPages);
    //        }
    //    }

    /**
     * 初始化 设置适配器新闻详情页
     * @param view
     */
    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsPagesAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    /**
     * 刷新新闻界面
     */
    private void refreshNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //                try {
                //                    Thread.sleep(2000);
                //                } catch (InterruptedException e) {
                //                    e.printStackTrace();
                //                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        initGet(getView());
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


}
