package com.example.fogfly.smarter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fogfly.smarter.R;

public class NewsWebActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingtoolbar;
    private ImageView mImageView;
    private WebView webview;
    private Toolbar toolbar;
    private TextView textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        initview();
    }

    /**
     * 初始化界面
     */
    private void initview() {
        Intent intent = getIntent();
        final String url = intent.getStringExtra("NewsUrl");
        String name = intent.getStringExtra("NewsName");
        String img = intent.getStringExtra("NewsImg");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.newsweb_collapsing_toolbar);
        mImageView = (ImageView) findViewById(R.id.newsweb_imageview);
        webview = (WebView) findViewById(R.id.newsweb_web);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingtoolbar.setTitle(name);
        Glide.with(this).load(img).into(mImageView);
        webview.post(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl(url);
                webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //        switch (item.getItemId()) {
        //            case R.id.homeAsUp:
        //                finish();
        //                return true;
        //        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
