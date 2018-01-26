package com.hl.AFCHelper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hl.AFCHelper.ImageViewPager.TextAndGraphicsView;
import com.hl.AFCHelper.R;

/**
 * Created by huanglei on 2018/1/19.
 */

public class ContentActivity extends AppCompatActivity {

    private ScrollView sv_main;
    private String[] mData;//图文数据的列表
    private TextAndGraphicsView mTextAndGraphicsView;

    public static final String SPLIT_TAG = "\\[\\#SPLIT\\#\\]";//分割处标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ac_content);
        TextView textView = (TextView ) findViewById (R.id.txt_topbar_content);
        textView.setText ("内容");

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        String name=bundle.getString("data");

        parseData(name);//解析数据
        initView();//初始化ui
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
