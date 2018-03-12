package com.hl.AFCHelper.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.hl.AFCHelper.Bean.DataTest;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.squareup.leakcanary.RefWatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;

@SuppressLint("JavascriptInterface")
public class ContentActivity extends BaseActivity implements View.OnTouchListener {
    private MarkdownView mMarkdownView;
    private List<String> imgUrlList = null;
    private ImagePagerActivity.ImageSize imageSize;
    private float x,y;
    private String content;
    private String title;



    //后退键监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Fragment结束
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);//
        refWatcher.watch(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ac_content;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar ();
        mImmersionBar.titleBar (R.id.toolbar_content).init ();

    }

    //监测WebView图片点击事件
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float density = getResources().getDisplayMetrics().density; //屏幕密度
        float touchX = event.getX() / density;  //必须除以屏幕密度
        float touchY = event.getY() / density;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x = touchX;
            y = touchY;
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            float dx = Math.abs(touchX-x);
            float dy = Math.abs(touchY-y);
            if(dx<10.0/density&&dy<10.0/density){
                clickImage(touchX,touchY);
            }
        }
        return false;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            content = bundle.getString("data");
             title = bundle.getString ("title");
            bundle.clear ();
        }
    }

    /**
     * 初始化布局
     */
    @Override
    protected void initView() {
        Toolbar toolbar = findViewById (R.id.toolbar_content);
        TextView textView = findViewById (R.id.toolbar_content_title);
        mMarkdownView = findViewById(R.id.markdown_view);
        toolbar.setTitle ("");
        textView.setText (title);
        setSupportActionBar(toolbar);
        imageSize = new ImagePagerActivity.ImageSize(50, 50);
        imgUrlList = extractMessageByRegular(content);
    }

    /**
     * 初始化WebView的配置
     */
    @Override
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "ClickableViewAccessibility"})
    protected void initWebView() {
        InternalStyleSheet css = new Github();
        css.addRule("body",  "padding: 0px");
        css.addRule(".scrollup", "width: 40px", "height: 40px","background-color: #2196F3","bottom: 25px", "right: 25px");
        css.addRule ("h1","font-weight: 700");
        css.addRule ("h2","font-weight: 700");
        css.addRule ("h3","font-weight: 700");
        css.addRule ("h4","font-weight: 700");
        css.addRule ("h5","font-weight: 700");
        css.addRule ("h6","font-weight: 700");
        mMarkdownView.addStyleSheet(css);
        DataTest dataTest = new DataTest ();

        mMarkdownView.loadMarkdown(content);

        mMarkdownView.setWebViewClient(new WebViewClient (){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings settings = mMarkdownView.getSettings();
        settings.setJavaScriptEnabled(true); //支持JS
        mMarkdownView.addJavascriptInterface(new JsInterface(getApplicationContext ()), "imageClick"); //JS交互
        mMarkdownView.setOnTouchListener(this);
    }

    //通过触控的位置来获取图片URL
    private void clickImage(float touchX, float touchY) {
        String js = "javascript:(function(){" +
                "var  obj=document.elementFromPoint("+touchX+","+touchY+");"
                +"if(obj.src!=null){"+ " window.imageClick.click(obj.src);}" +
                "})()";
        mMarkdownView.loadUrl(js);
    }

    //使用正则表达式提取中括号中的内容
    public static List<String> extractMessageByRegular(String msg){

        List<String> list= new ArrayList<> ();
        Pattern p = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(4, m.group().length()-1));
        }
        return list;
    }

    //返回url在ListPhotos中的下标位置
    private int getUrlPosition(String url) {
        for (int i = 0; i < imgUrlList.size(); i++) {
            if (url.equals(imgUrlList.get(i))) {
                return i;
            }
        }
        return -1;
    }

    //JS
    class JsInterface{
        Context context;
        JsInterface(Context context){
            this.context = context;
        }

        //查看图片url
        @JavascriptInterface
        public void click(String url){
            ImagePagerActivity.startImagePagerActivity(getApplicationContext (), imgUrlList, getUrlPosition(url), imageSize);
        }
    }
}
