package com.example.fogfly.smarter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.adapter.DiscoverDetailAdapter;
import com.example.fogfly.smarter.entity.DiscoverSteps;

import java.util.ArrayList;
import java.util.List;

public class DiscoverWebActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout find_web_tool;
    private ImageView find_web_imageview;
    private RecyclerView mRecyclerView;
    private DiscoverDetailAdapter adapter;
    List<DiscoverSteps> mList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_web);
        initview();
    }

    /**
     * 初始化VIEW
     */
    private void initview() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String albums = intent.getStringExtra("albums");
        String burden = intent.getStringExtra("burden");
        //通过key来获取你传输的list集合数据，并强转为List<Object>格式，Object就是前面红色字体部分说的，要实现Serializable接口。
        // List<DiscoverSteps> mList1 = (List<DiscoverSteps>) getIntent().getSerializableExtra("mList1");
        String steps_str = intent.getStringExtra("steps");
        com.alibaba.fastjson.JSONArray steps =  JSON.parseArray(steps_str);
        for (int j = 0; j < steps.size(); j++) {
            com.alibaba.fastjson.JSONObject object2 = null;

                object2 = (com.alibaba.fastjson.JSONObject) steps.get(j);
                String img = object2.getString("img");
                String step = object2.getString("step");
                DiscoverSteps discover1 = new DiscoverSteps();
                discover1.setImg(img);
                discover1.setStep(step);
                mList1.add(discover1);


        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        find_web_tool = (CollapsingToolbarLayout) findViewById(R.id.find_web_tool);
        find_web_imageview = (ImageView) findViewById(R.id.find_web_imageview);
        mRecyclerView = (RecyclerView) findViewById(R.id.find_web_sho);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        find_web_tool.setTitle(title);
        Glide.with(this).load(albums).into(find_web_imageview);
        GridLayoutManager layoutManager = new GridLayoutManager(DiscoverWebActivity.this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new DiscoverDetailAdapter(mList1);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
