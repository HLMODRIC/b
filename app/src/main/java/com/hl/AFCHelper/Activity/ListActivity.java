package com.hl.AFCHelper.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.hl.AFCHelper.ListFragment.ListFragment;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

/**
 * Created by huanglei on 2018/1/19.
 */

public class ListActivity extends AppCompatActivity {
    private ArrayList<Data> mData;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ac_list);
        Intent intent = getIntent ();
        mData = ( ArrayList<Data> ) intent.getSerializableExtra ("data");
        tableName = intent.getStringExtra ("table_name");

        FragmentManager fragmentManager = getFragmentManager ();         //获取FragmentManger对象
        Fragment fragment = fragmentManager.findFragmentById (R.id.fl_content);

        if (fragment == null) {
            fragment = new ListFragment (fragmentManager);
            fragmentManager.beginTransaction ()
                    .add (R.id.fl_content, fragment)
                    .commit ();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }
}
