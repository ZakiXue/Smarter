package com.example.fogfly.smarter.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.db.JournalDatebaseHelper;
import com.example.fogfly.smarter.entity.Journal;
import com.example.fogfly.smarter.ui.JournalUpdateActivity;
import com.example.fogfly.smarter.utils.GetDate;

import java.util.List;

import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * @author Zaki Xue
 * @time 2019/2/25 21:30
 * @description
 */
public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    List<Journal> mList;
    private int mEditPosition = -1;


    public JournalAdapter(Context context, List<Journal> mlist) {
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        mList = mlist;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_circle;
        private TextView tv_date;
        private LinearLayout ll_title;
        private LinearLayout item_ll_control;
        private TextView tv_title;
        private TextView tv_content;
        private RelativeLayout rl_edit;
        private LinearLayout item_ll;
        private final FloatingActionsMenu mActionsMenu;
        private final FloatingActionButton delete;
        private final FloatingActionButton edit;

        public ViewHolder(View itemView) {
            super(itemView);
            item_ll = (LinearLayout) itemView.findViewById(R.id.item_ll);
            iv_circle = (ImageView) itemView.findViewById(R.id.journal_iv_circle);
            tv_date = (TextView) itemView.findViewById(R.id.journal_tv_date);
            item_ll_control = (LinearLayout) itemView.findViewById(R.id.journal_item_ll_control);
            ll_title = (LinearLayout) itemView.findViewById(R.id.journal_ll_title);
            tv_title = (TextView) itemView.findViewById(R.id.journal_tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.journal_tv_content);
            rl_edit = (RelativeLayout) itemView.findViewById(R.id.journal_rl_edit);
            mActionsMenu = (FloatingActionsMenu) itemView.findViewById(R.id.journal_edit_labels);
            delete = (FloatingActionButton) itemView.findViewById(R.id.edit_fab_delete);
            edit = (FloatingActionButton) itemView.findViewById(R.id.edit_fab_edit);
        }
    }

    @Override
    public JournalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.journal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final JournalAdapter.ViewHolder holder, final  int position) {

        String dateSystem = GetDate.getDate().toString();

        /**
         * 如果该日记是当天写的，则将日期左边的圆圈设置成橙色的
         */
        if(mList.get(position).getDate().equals(dateSystem)){
            holder.iv_circle.setImageResource(R.drawable.circle_orange);
        }

        holder.tv_date.setText(mList.get(position).getDate());
        holder.tv_title.setText(mList.get(position).getTitle());
        holder.tv_content.setText("       " + mList.get(position).getContent());
        holder.mActionsMenu.setVisibility(View.INVISIBLE);
        if(mEditPosition == position){
            holder.mActionsMenu.setVisibility(View.VISIBLE);
        }else {
            holder.mActionsMenu.setVisibility(View.GONE);
        }

        /**
         * 当点击日记的内容时候，则显示出编辑按钮
         */
        holder.item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mActionsMenu.getVisibility() == View.INVISIBLE) {
                    holder.mActionsMenu.setVisibility(View.VISIBLE);
                }else {
                    holder.mActionsMenu.setVisibility(View.INVISIBLE);
                }
                if(mEditPosition != position){
                    notifyItemChanged(mEditPosition);
                }
                mEditPosition = position;
            }
        });

        /**
         * 点击删除按钮
         */
       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
               alertDialogBuilder.setMessage("是否删除日记内容？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       String tag = mList.get(position).getTag();
                       JournalDatebaseHelper helper = new JournalDatebaseHelper(mContext, "Diary.db", null, 3);
                       SQLiteDatabase dbDelete = helper.getWritableDatabase();
                       dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                       mList.remove(position);
                       notifyDataSetChanged();


                   }
               }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       notifyDataSetChanged();
                   }
               }).show();
               Toast.makeText(mContext,"delete", Toast.LENGTH_SHORT).show();
           }
       });
        /**
         * 点击编辑按钮
         */
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, JournalUpdateActivity.class);
                intent.putExtra("date", mList.get(position).getDate());
                intent.putExtra("title",mList.get(position).getTitle());
                intent.putExtra("content",mList.get(position).getContent());
                intent.putExtra("tag",mList.get(position).getTag());
                mContext.startActivity(intent);
                Toast.makeText(mContext,"edit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
       return mList.size();
      //  return 0;
    }
}


