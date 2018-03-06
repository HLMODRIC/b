package com.hl.AFCHelper.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.hl.AFCHelper.Fragment.ListFragment;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Until.SearchDataHelper;
import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;
import com.squareup.leakcanary.RefWatcher;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ArrayList<Data> mData;
    private String tableName;
    private String oldNewsText = null;
    private SearchDataHelper mSearchDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ac_list);
        //数据加载并初始化View
        initData ();
        initView ();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent ();
        tableName = intent.getStringExtra ("table_name");
        mData = intent.getParcelableArrayListExtra ("data");

    }

    /**
     * 初始化布局
     */
    private void initView() {
        //Toolbar
        Toolbar toolbar = findViewById (R.id.toolbar_list);
        TextView toolbar_title = findViewById (R.id.toolbar_list_title);
        toolbar.setTitle ("");
        //生成选项菜单
        toolbar.inflateMenu(R.menu.list_menu);
        //设置溢出菜单的icon，显示、隐藏溢出菜单弹出的窗口
        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);
        toolbar_title.setText (tableName);
        //Fragment
        FragmentManager fragmentManager = getSupportFragmentManager ();         //获取FragmentManger对象
        Fragment fragment = fragmentManager.findFragmentById (R.id.fl_content);

        if (fragment == null) {
            fragment = new ListFragment ();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        MenuItem item = menu.findItem(R.id.list_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //默认刚进去就打开搜索栏
        searchView.setIconified(true);
        //设置输入文本的EditText
        SearchView.SearchAutoComplete et = searchView.findViewById(R.id.search_src_text);
        //设置搜索栏的默认提示
        et.setHint("请输入 "+tableName+" 相关内容");
        //设置提示文本的颜色
        et.setHintTextColor(Color.WHITE);
        et.setTextSize (TypedValue.COMPLEX_UNIT_SP, 14);
        //设置输入文本的颜色
        et.setTextColor(Color.WHITE);
        //设置提交按钮是否可见
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchDataHelper = new SearchDataHelper ();
                if (newText != null && newText.length () > 0) {
                            if (!newText.equals (oldNewsText)) {
                                getSearchListData (newText, mSearchDataHelper.SearchData(tableName));
                            }
                        }else {
                    Fragment fragment = new ListFragment ();
                    getSupportFragmentManager ().beginTransaction ()
                            .replace (R.id.fl_content, fragment)
                            .commit ();

                }
                return false;


            }
        });
        return true;
    }
    private void getSearchListData(String newText,String sql ) {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getApplicationContext ());
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        String[] selectionArgs = {"%"+newText+"%"};
        Cursor cursor = dbRead.rawQuery (sql, selectionArgs);
        mData = new ArrayList<> ();
        while (cursor.moveToNext ()) {
            int mid = cursor.getInt (cursor.getColumnIndex ("id"));
            String titleStr = cursor.getString (cursor.getColumnIndex ("title"));
            String contentStr = cursor.getString (cursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            Log.d ("33333", mid + titleStr);
            mData.add (data);
        }
        cursor.close ();
        dbHelper.close ();
        dbRead.close ();

        Fragment fragment = new ListFragment ();
        Bundle bundle = new Bundle ();
        bundle.putSerializable ("data", mData);
        fragment.setArguments (bundle);
        getSupportFragmentManager ().beginTransaction ()
                .replace (R.id.fl_content, fragment)
                .commit ();
    }
}
