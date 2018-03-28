package com.hl.AFCHelper.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hl.AFCHelper.Bean.db.DBManager;
import com.hl.AFCHelper.Until.FileUtils;

/**
 * Created by huanglei on 2018/3/9.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟两秒


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //数据库操作
        DBManager.openDatabase (getApplicationContext ());


        new Handler ().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(getApplicationContext (), MainActivity.class);
                startActivity(intent);//显示主窗口
               finish();//关闭主窗口
                System.gc ();
            }
        }, SPLASH_DISPLAY_LENGHT);


    }
}
