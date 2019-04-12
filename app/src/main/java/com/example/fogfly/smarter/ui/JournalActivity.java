package com.example.fogfly.smarter.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.db.JournalDatebaseHelper;
import com.example.fogfly.smarter.utils.GetDate;
import com.example.fogfly.smarter.view.LinedEditText;

import cc.trity.floatingactionbutton.FloatingActionButton;

public class JournalActivity extends AppCompatActivity {

    private TextView mTv_date;
    private EditText mEt_title;
    private LinedEditText mLinedEditText;
    private JournalDatebaseHelper mHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        mHelper = new JournalDatebaseHelper(this, "Diary.db", null, 3);
        initview();
        initfloatingbutton();
        mTv_date.setText("今天" + GetDate.getDate());
    }

    /**
     * 初始化悬浮菜单
     */
    private void initfloatingbutton() {
        FloatingActionButton fab_back = (FloatingActionButton) findViewById(R.id.journal_fab_back);
        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.journal_fab_add);
        //添加日记按钮
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JournalActivity.this, "fab_add点击", Toast.LENGTH_SHORT).show();
                String dateBack = GetDate.getDate().toString();
                String tag = String.valueOf(System.currentTimeMillis());
                String title = mEt_title.getText().toString();
                String edit = mLinedEditText.getText().toString();
                if (!title.isEmpty() || !edit.isEmpty()) {
                    SQLiteDatabase database = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", dateBack);
                    values.put("title", title);
                    values.put("content", edit);
                    values.put("tag", tag);
                    database.insert("Diary", null, values);
                    values.clear();
                    finish();
                }
            }
        });
        //取消日记按钮
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String dateBack = GetDate.getDate().toString();
                final  String tag = String.valueOf(System.currentTimeMillis());
                final    String title = mEt_title.getText().toString();
                final   String edit = mLinedEditText.getText().toString();
                if (!title.isEmpty() || !edit.isEmpty()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JournalActivity.this);
                    alertDialogBuilder.setMessage("是否保存日记内容？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SQLiteDatabase database = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("date", dateBack);
                            values.put("title", title);
                            values.put("content", edit);
                            values.put("tag", tag);
                            database.insert("Diary", null, values);
                            values.clear();
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
                }else{
                    finish();
                }
            }
        });

    }

    /**
     * 初始化view
     */
    private void initview() {
        mTv_date = (TextView) findViewById(R.id.add_diary_tv_date);
        mEt_title = (EditText) findViewById(R.id.add_diary_et_title);
        mLinedEditText = (LinedEditText) findViewById(R.id.journal_et_content);
    }
}
