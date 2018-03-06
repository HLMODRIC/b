package com.hl.AFCHelper.ImageViewPager;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hl.AFCHelper.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 图文布局，该布局是用LinearLayout布局的
 * Created by 林川 on 2016/12/6.
 */

public class TextAndGraphicsView extends LinearLayout {

    public static final String TEXT_TAG =  "[#TEXT#]";//文字普通内容标识
    public static final String IMAGE_LOCAL_TAG = "[#IMAGE#]";//本地图片标识(assets目录下)

    private Context context;
    private String[] mData;
    private final int PADDING_SIZE = 10;//边距
    //private final int IMAGE_MAX_HEIGHT = 1000;//图片最大的高度限制
    private ImagePagerActivity.ImageSize imageSize;
    private List<String> ListPhotos;
    private final String localPathUrl = "file:///android_asset/";


    public TextAndGraphicsView(Context context, String[] mData) {
        super(context);
        this.setOrientation(VERTICAL);
        this.context = context;
        this.mData = mData;
        if (mData == null || mData.length == 0) {
            mData = new String[1];
        }

        ListPhotos = new ArrayList<>();
        imageSize = new ImagePagerActivity.ImageSize(50, 50);
        initView();
    }

    private void initView() {

        //根据数据动态创建TextView和ImageView并添加点击事件和属性
        for (int i = 0; i < mData.length; i++) {
             //文字普通内容标识
            if (mData[i].contains(TEXT_TAG)) {
                mData[i] = mData[i].replace("<br />","<br>");
                TextView tv = new TextView(context);
                tv.setText(Html.fromHtml (mData[i].replace(TEXT_TAG, "")));
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setIncludeFontPadding(false);
                tv.setPadding(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE);
                tv.setTextSize(15);
                tv.setTextIsSelectable(true);
                tv.setHighlightColor(getResources ().getColor (R.color.app_yellow));
                tv.setLineSpacing(5, 1.2f);// 参数：1、行间距 2、倍数
                tv.setTextColor(Color.BLACK);
                addView(tv);
            }//图片标识
             else if (mData[i].contains(IMAGE_LOCAL_TAG)) {
                final ImageView iv = new ImageView(context);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setAdjustViewBounds (true);
                iv.setPadding(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE);
                final String localPath = mData[i].replace(IMAGE_LOCAL_TAG, "");
                ListPhotos.add(localPathUrl + localPath);

                RequestOptions options = new RequestOptions ()
                        .diskCacheStrategy (DiskCacheStrategy.ALL)
                        .error(R.mipmap.load_error);

                Glide.with(context)
                        .load(localPathUrl + localPath)
                        .apply (options)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(iv);
                iv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePagerActivity.startImagePagerActivity(context, ListPhotos, getUrlPosition(localPathUrl + localPath), imageSize);
                    }
                });
                addView(iv);
            } else {
                //扩展包含其他类型
            }
        }
    }

    //返回url在ListPhotos中的下标位置
    private int getUrlPosition(String url) {
        for (int i = 0; i < ListPhotos.size(); i++) {
            if (url.equals(ListPhotos.get(i))) {
                return i;
            }
        }
        return -1;
    }

    //dp转为像素
    private int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
