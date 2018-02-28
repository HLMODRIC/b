package com.hl.AFCHelper.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.hl.AFCHelper.ListFragment.ListFragment;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.SearchFragment.SearchListFragment;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

/**
 * Created by huanglei on 2018/1/24.
 */

public class SeachDataActivity extends AppCompatActivity {
    private ArrayList<Data> datas;
    private String StringData;
    private SQLiteDatabase dbRead;
    MyDBOpenHelper dbHelper;
    String titleStr;
    String contentStr;
    int mid;
    private Cursor mCursor;
    private String oldNewsText = null;
    private String getMessage;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ac_seach_data);

        backButton = ( ImageButton ) findViewById (R.id.search_back);
        backButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });
        SearchView searchView = ( SearchView ) findViewById (R.id.search_data_sv);

        final Intent intent = getIntent ();
        getMessage = intent.getStringExtra ("message");
        datas = ( ArrayList<Data> ) intent.getSerializableExtra ("data");

        searchView.setOnCloseListener (new SearchView.OnCloseListener () {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        // 设置搜索文本监听
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                switch (intent.getStringExtra ("message")) {
                    case "theory_fragment":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from theory where content like ?");
                            }
                        }
                        break;

                    case "chs_code_search":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from chs_code where content like ?");
                            }
                        }
                        break;
                    case "bnr_code_search":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from bnr_code where content like ?");
                            }
                        }
                        break;

                    case "mbc_code_search":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from mbc_code where content like ?");
                            }
                        }
                        break;
                    case "card_code_search":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from card_code where content like ?");
                            }
                        }
                        break;
                    case "ip_code_search":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from ip_code where content like ?");
                            }
                        }
                        break;
                    case "screw_code_search":
                        if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, "select * from screw_code where content like ?");
                            }
                        }
                        break;

                    default:
                        break;
                }
                return false;

            }
        });

        FragmentManager fragmentManager = getFragmentManager ();         //获取FragmentManger对象
        Fragment fragment = fragmentManager.findFragmentById (R.id.search_list);
        if (fragment == null) {
            Bundle bundle = new Bundle ();
            bundle.putSerializable ("data", datas);
            fragment = new SearchListFragment (fragmentManager);
            fragment.setArguments (bundle);
            fragmentManager.beginTransaction ()
                    .replace (R.id.search_list, fragment)
                    .commit ();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }

    private void getSearchListData(String newText,String sql ) {
        //db数据库
        dbHelper = new MyDBOpenHelper (getBaseContext ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        String[] selectionArgs = {"%"+newText+"%"};
        mCursor = dbRead.rawQuery (sql, selectionArgs);  //查询所有数据
        datas = new ArrayList<Data> ();
        while (mCursor.moveToNext ()) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }

            // FragmentManager fragmentManager = getFragmentManager (); //获取FragmentManger对象
            Fragment fragment = new SearchListFragment (getFragmentManager ());
            Bundle bundle = new Bundle ();
            bundle.putSerializable ("data", datas);
            fragment.setArguments (bundle);
            getFragmentManager ().beginTransaction ()
                    .replace (R.id.search_list, fragment)
                    .commit ();
    }
}

