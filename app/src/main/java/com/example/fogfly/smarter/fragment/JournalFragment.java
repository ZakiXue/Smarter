package com.example.fogfly.smarter.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.adapter.JournalAdapter;
import com.example.fogfly.smarter.db.JournalDatebaseHelper;
import com.example.fogfly.smarter.entity.Journal;
import com.example.fogfly.smarter.ui.JournalActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Zaki Xue
 * @time 2019/1/24 16:35
 * @description
 */
public class JournalFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private List<Journal> mList = new ArrayList<>();

    /**
     * 标识今天是否已经写了日记
     */
    private boolean isWrite = false;
    private static TextView mTvTest;
    private JournalDatebaseHelper mHelper;
    private LinearLayout mItem_first;
    private LinearLayout mRelativeLayout;
    private JournalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //        TextView textView = new TextView(getActivity());
        //        textView.setText("日记");
        //        textView.setTextSize(100);
        //        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = inflater.inflate(R.layout.fragment_journal, null);
        initFloatingButton(view);
        initview(view);
        getDiaryBeanList();
        if (!mList.isEmpty()) {
            mRelativeLayout.removeView(mItem_first);
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.journal_rv_show_diary);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new JournalAdapter(getContext(), mList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }


    private List<Journal> getDiaryBeanList() {
        List<Journal> diaryList = new ArrayList<>();
        mHelper = new JournalDatebaseHelper(getContext(), "Diary.db", null, 3);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query("Diary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                mRelativeLayout.removeView(mItem_first);
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                mList.add(new Journal(date, title, content, tag));
            } while (cursor.moveToNext());
        }
        cursor.close();
        for (int i = mList.size() - 1; i >= 0; i--) {
            diaryList.add(mList.get(i));
        }
        mList = diaryList;
        return mList;
    }

    @Override
    public void onResume() {
        if (!mList.isEmpty()) {
            mRelativeLayout.removeView(mItem_first);
        }
        mList.clear();
        getDiaryBeanList();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new JournalAdapter(getContext(), mList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // Toast.makeText(getContext(),"IAM A ONRESUME", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    /**
     * 初始化view
     *
     * @param view
     */
    private void initview(View view) {
        mRelativeLayout = (LinearLayout) view.findViewById(R.id.journal_ll_journal);
        mItem_first = (LinearLayout) view.findViewById(R.id.item_first);
    }

    /**
     * 初始化悬浮按钮
     *
     * @param view
     */
    private void initFloatingButton(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.journal_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JournalActivity.class);
                startActivity(intent);
            }
        });
    }
}
