package com.hl.AFCHelper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hl.AFCHelper.ImageViewPager.TextAndGraphicsView;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by huanglei on 2018/1/19.
 */

public class ContentActivity extends AppCompatActivity {

    private ScrollView sv_main;
    private String[] mData;//图文数据的列表
    private TextAndGraphicsView mTextAndGraphicsView;
    private ImageButton backButton;

    public static final String SPLIT_TAG = "\\[\\#SPLIT\\#\\]";//分割处标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ac_content);
        TextView textView = (TextView ) findViewById (R.id.txt_topbar_content);
        backButton = (ImageButton) findViewById (R.id.content_back);
        backButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        String content=bundle.getString("data");
        String title = bundle.getString ("title");
        textView.setText (title);
        parseData(content);//解析数据
        initView();//初始化ui
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }


    private void parseData(String name) {
        //根据分割线来把文字和图片地址存入数组中
        mData = name.split (SPLIT_TAG);
    }

    private void initView() {
        sv_main = (ScrollView) findViewById(R.id.content_sv);
        mTextAndGraphicsView = new TextAndGraphicsView(this,mData);
        sv_main.addView(mTextAndGraphicsView);
    }
}
