package com.example.fogfly.smarter.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.db.JournalDatebaseHelper;
import com.example.fogfly.smarter.utils.GetDate;
import com.example.fogfly.smarter.view.LinedEditText;

import cc.trity.floatingactionbutton.FloatingActionButton;

public class JournalUpdateActivity extends AppCompatActivity {
    private TextView mTv_date;
    private EditText mEt_title;
    private LinedEditText mLinedEditText;
    private JournalDatebaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_update);
        initview();
        mHelper = new JournalDatebaseHelper(this, "Diary.db", null, 3);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String mTag = intent.getStringExtra("tag");
        System.out.println("穿过来没又"+date+mTag);
        mEt_title.setText(intent.getStringExtra("title"));
        mLinedEditText.setText(intent.getStringExtra("content"));
        mTv_date.setText("今天" + GetDate.getDate());
        initfloatingbutton();

    }

    /**
     * 初始化悬浮菜单
     */
    private void initfloatingbutton() {
        FloatingActionButton fab_back = (FloatingActionButton) findViewById(R.id.journal_fab_back1);
        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.journal_fab_add1);
        //添加日记按钮
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title1 = mEt_title.getText().toString();
                String edit1 = mLinedEditText.getText().toString();
                valuesUpdate.put("title", title1);
                valuesUpdate.put("content", edit1);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title1});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{edit1});
                finish();
            }
        });
        //取消日记按钮
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dateBack = GetDate.getDate().toString();
                final String tag = String.valueOf(System.currentTimeMillis());
                final String title = mEt_title.getText().toString();
                final String edit = mLinedEditText.getText().toString();
                if (!title.isEmpty() || !edit.isEmpty()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JournalUpdateActivity.this);
                    alertDialogBuilder.setMessage("是否保存日记内容？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                            ContentValues valuesUpdate = new ContentValues();
                            String title1 = mEt_title.getText().toString();
                            String edit1 = mLinedEditText.getText().toString();
                            valuesUpdate.put("title", title1);
                            valuesUpdate.put("content", edit1);
                            dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title1});
                            dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{edit1});
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
                } else {
                    finish();
                }
            }
        });

    }

    /**
     * 初始化view
     */
    private void initview() {
        mTv_date = (TextView) findViewById(R.id.add_diary_tv_date1);
        mEt_title = (EditText) findViewById(R.id.add_diary_et_title1);
        mLinedEditText = (LinedEditText) findViewById(R.id.journal_et_content1);
    }
}
