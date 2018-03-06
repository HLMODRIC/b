package com.hl.AFCHelper.TabFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.hl.AFCHelper.Activity.ListActivity;
import com.hl.AFCHelper.Activity.SeachDataActivity;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class SettingTabFragment extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_setting,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
