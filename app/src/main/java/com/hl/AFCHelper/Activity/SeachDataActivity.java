package com.hl.AFCHelper.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SearchView;

import com.hl.AFCHelper.SearchFragment.SearchListFragment;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;

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
    private String Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ac_seach_data);
        SearchView searchView = ( SearchView ) findViewById (R.id.search_data_sv);
        final Intent intent = getIntent ();
        getMessage = intent.getStringExtra ("message");
        Log.d ("111114",getMessage);

        searchView.setOnCloseListener(new SearchView.OnCloseListener () {
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

                    case "repair_fragment":
                        Message = "basic";
                        if (newText != null && newText.length () > 0) {
                            if (newText.equals (oldNewsText) == false) {
                                getSearchListData (newText, "select * from " + Message + " where content like ?");
                            }
                        }
                        break;

                    case "search_fragment":
                        Message = "repair";
                        if (newText != null && newText.length () > 0) {
                            if (newText.equals (oldNewsText) == false) {
                                getSearchContentData ( "select title from basic where id = ?", newText);
                            }
                        }
                        break;
                        case "theory_fragment":
                            Message = "theory";
                            if (newText != null && newText.length () > 0) {
                                if (newText.equals (oldNewsText) == false) {
                                    getSearchListData (newText, "select * from " + Message + " where content like ?");
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

    private void getSearchContentData(String sql,String id ) {
        //db数据库
        dbHelper = new MyDBOpenHelper (getBaseContext ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        mCursor = dbRead.rawQuery (sql, new String[] {id});  //查询所有数据
        while (mCursor.moveToNext ()) {
            StringData = mCursor.getString (mCursor.getColumnIndex ("title"));
            Log.d ("111115",StringData);
            Log.d ("111115", String.valueOf (StringData.length ()));
        }
        if (StringData != null && StringData.length () > 0 ) {
            Intent intent = new Intent (SeachDataActivity.this, ContentActivity.class);
            Bundle bundle = new Bundle ();
            bundle.putString ("data", StringData);
            intent.putExtras (bundle);
            startActivity (intent);
        }
    }

    }

