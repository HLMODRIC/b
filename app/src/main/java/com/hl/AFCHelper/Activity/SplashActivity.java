package com.hl.AFCHelper.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hl.AFCHelper.Bean.db.DBManager;
import com.hl.AFCHelper.Until.FileUtils;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.RxPermissionsTool;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by huanglei on 2018/3/9.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟两秒
    private FileUtils mFileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        new Handler ().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //数据库操作
                DBManager.openDatabase (getApplicationContext ());
                Intent intent = new Intent(getApplicationContext (), MainActivity.class);
                getApplication ().startActivity(intent);//显示主窗口
               finish();//关闭主窗口
            }
        }, SPLASH_DISPLAY_LENGHT);


    }
}
