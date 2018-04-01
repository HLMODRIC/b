package com.hl.AFCHelper.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hl.AFCHelper.R;
import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by huanglei on 2018/3/19.
 */

public class VideoContentActivity extends BaseActivity {
    private MarkdownView mMarkdownView;
    private String content;
    private String title;
    private String imageUrl;

    @Override
    protected int setLayoutId() {
        return R.layout.video_ac_content;
    }

    @Override
    protected void initData() {
        super.initData ();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            content = bundle.getString("data");
            title = bundle.getString ("title");
            imageUrl = bundle.getString ("imageUrl");
            String videoUrl = bundle.getString ("videoUrl");
            bundle.clear ();
        }
    }

    @Override
    protected void initView() {
        super.initView ();
        mMarkdownView = findViewById (R.id.markdown_view_video);
        InternalStyleSheet css = new Github ();
        css.addRule ("body", "padding: 0px");
        css.addRule (".scrollup", "width: 40px", "height: 40px", "background-color: #2196F3", "bottom: 25px", "right: 25px");
        css.addRule ("h1", "font-weight: 700");
        css.addRule ("h2", "font-weight: 700");
        css.addRule ("h3", "font-weight: 700");
        css.addRule ("h4", "font-weight: 700");
        css.addRule ("h5", "font-weight: 700");
        css.addRule ("h6", "font-weight: 700");
        mMarkdownView.addStyleSheet (css);
        mMarkdownView.loadMarkdown (content);
        JZVideoPlayerStandard jzVideoPlayerStandard = findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(Environment.getExternalStorageDirectory ()+"/AFCHelper/1.mp4", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title);
        Log.d ("11111",Environment.getExternalStorageDirectory ()+"/DCIM/1.mp4");
        //Glide 加载图片简单用法
        RequestOptions options = new RequestOptions ()
                .diskCacheStrategy (DiskCacheStrategy.ALL)
                .error(R.drawable.load_error);

        Glide.with(getApplicationContext ())
                .applyDefaultRequestOptions (options)
                .load(imageUrl)
                .into(jzVideoPlayerStandard.thumbImageView);
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
