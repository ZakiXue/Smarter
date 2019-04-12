package com.example.fogfly.smarter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.entity.Discover;
import com.example.fogfly.smarter.entity.DiscoverSteps;
import com.example.fogfly.smarter.ui.DiscoverWebActivity;

import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/2/27 23:47
 * @description
 */
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private Context mContext;
    private List<Discover> mList;
    private List<DiscoverSteps> mList1;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView discover_item_img;
        TextView discover_item_title;
        TextView discover_item_text;
        TextView discover_item_tag;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            discover_item_img = (ImageView) itemView.findViewById(R.id.discover_item_img);
            discover_item_title = (TextView) itemView.findViewById(R.id.discover_item_title);
            discover_item_text = (TextView) itemView.findViewById(R.id.discover_item_text);
            discover_item_tag = (TextView) itemView.findViewById(R.id.discover_item_tag);

        }
    }

    public DiscoverAdapter(List<Discover> list, List<DiscoverSteps> list1) {
        this.mList = list;
        this.mList1 = list1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_discover, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Discover discover = mList.get(position);
                Intent intent = new Intent(mContext, DiscoverWebActivity.class);
                intent.putExtra("title",discover.getTitle());
                intent.putExtra("ingredients",discover.getIngredients());
                intent.putExtra("albums",discover.getAlbums());
                intent.putExtra("steps",discover.getSteps().toString());
                mContext.startActivity(intent);
                //                intent.putExtra("mList1",(Serializable) (mList1));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Discover find = mList.get(position);
        holder.discover_item_title.setText(find.getTitle());
        holder.discover_item_text.setText(find.getImtro());
        holder.discover_item_tag.setText(find.getTags());
        Glide.with(mContext).load(find.getAlbums()).into(holder.discover_item_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
