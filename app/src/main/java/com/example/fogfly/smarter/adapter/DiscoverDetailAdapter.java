package com.example.fogfly.smarter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.entity.DiscoverSteps;

import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/2/28 2:520
 * @description
 */
public class DiscoverDetailAdapter extends RecyclerView.Adapter<DiscoverDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<DiscoverSteps> mList1;

    static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView discover_item1_img;
        TextView discover_item1_text;

        public ViewHolder(View itemView) {
            super(itemView);
            discover_item1_img = (ImageView) itemView.findViewById(R.id.discover_item1_img);
            discover_item1_text = (TextView) itemView.findViewById(R.id.discover_item1_text);
        }
    }

    public DiscoverDetailAdapter(List<DiscoverSteps> list1) {
        this.mList1 = list1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_discover1, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiscoverSteps find = mList1.get(position);
        holder.discover_item1_text.setText(find.getStep());
        Glide.with(mContext).load(find.getImg()).into(holder.discover_item1_img);
    }

    @Override
    public int getItemCount() {
        return mList1.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
