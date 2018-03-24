package com.hl.AFCHelper;

import android.app.Application;
import android.content.Context;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
import com.vondear.rxtools.RxTool;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
    }

}
