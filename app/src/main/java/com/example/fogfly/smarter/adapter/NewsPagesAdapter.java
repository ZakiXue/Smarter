package com.example.fogfly.smarter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.entity.NewsPages;
import com.example.fogfly.smarter.ui.NewsWebActivity;

import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/27 15:54
 * @description 新闻详情页面适配器
 */
public class NewsPagesAdapter extends RecyclerView.Adapter<NewsPagesAdapter.ViewHolder> {

    private Context mContext;
    private List<NewsPages> mnewsList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView newsImage;
        ImageView newsImage1;
        ImageView newsImage2;
        TextView newsName;
        TextView newsData;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            newsImage = (ImageView) itemView.findViewById(R.id.news_item_image);
            newsImage1 = (ImageView) itemView.findViewById(R.id.news_item_image1);
            newsImage2 = (ImageView) itemView.findViewById(R.id.news_item_image2);
            newsName = (TextView) itemView.findViewById(R.id.news_item_text);
            newsData = (TextView) itemView.findViewById(R.id.news_item_data);
        }
    }

    public NewsPagesAdapter(List<NewsPages> mnewsList) {
        this.mnewsList = mnewsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_news, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                NewsPages newsPages = mnewsList.get(position);
                Intent intent = new Intent(mContext, NewsWebActivity.class);
                intent.putExtra("NewsUrl",newsPages.getUrl());
                intent.putExtra("NewsName",newsPages.getData());
                intent.putExtra("NewsImg",newsPages.getImage());
                mContext.startActivity(intent);
                // Intent intent = new Intent(mContext,);
                //                int position = holder.getAdapterPosition();
                //                NewsPages news = mnewsList.get(position);
                //                Intent intent = new Intent(mContext, NewsPagesFragment.class);
                // intent.putExtra(NewsPagesFragment.NEWS_NAME,news.getTitle());
                //Toast.makeText(mContext, "clickclick", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsPages news = mnewsList.get(position);
        holder.newsName.setText(news.getTitle());
        holder.newsData.setText(news.getData());
        Glide.with(mContext).load(news.getImage()).into(holder.newsImage);
        if (!TextUtils.isEmpty(news.getImage1())){
            Glide.with(mContext).load(news.getImage1()).into(holder.newsImage1);
        }
        if (!TextUtils.isEmpty(news.getImage2())){
            Glide.with(mContext).load(news.getImage2()).into(holder.newsImage2);
        }
    }

    @Override
    public int getItemCount() {
        return mnewsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
