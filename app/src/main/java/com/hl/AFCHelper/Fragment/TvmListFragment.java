package com.hl.AFCHelper.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.AFCHelper.Activity.VideoListActivity;
import com.hl.AFCHelper.Adapter.BaseRecyclerAdapter;
import com.hl.AFCHelper.Adapter.TheoryListDataAdapter;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.Bean.db.MyDBOpenHelper;
//import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

public class TvmListFragment extends Fragment {

    private ArrayList<Data> mData;
    private ArrayList<Data> list;
    private int mid;
    private String titleStr;
    private String contentStr;
    private String imageUrl;
    private String videoUrl;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.recycler_theory_list, container, false);
        initData ();
        initView ();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        //refWatcher.watch(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        getData ();
    }
    /**
     * 初始化布局
     */
    private void initView() {
        RecyclerView crimeRecyclerView = view.findViewById (R.id.theory_recycler_view);
        crimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        TheoryListDataAdapter adapter = new TheoryListDataAdapter (getContext (),R.layout.theory_recycler_list_item, mData);
        adapter.setItemClickListener (new BaseRecyclerAdapter.onItemClickListener () {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent=new Intent (getActivity (),VideoListActivity.class);
                Bundle bundle=new Bundle();
                getTabData (mData.get (position).getNew_content ());
                bundle.putParcelableArrayList ("videoData",list);
                bundle.putString ("table_name",mData.get (position).getNew_title ());
                intent.putExtras(bundle);
                bundle.clear ();
                startActivity(intent);
            }
        });
        crimeRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        Cursor mCursor = dbRead.rawQuery ("select * from tab_name where id between 200 and 299",null);
        mData = new ArrayList<> ();
        while (mCursor.moveToNext ()) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            mData.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        dbRead.close ();
    }
    private void getTabData(String sql) {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        Cursor mCursor = dbRead.rawQuery (sql,null);
        list = new ArrayList<> ();
        while (mCursor.moveToNext ()) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            imageUrl = mCursor.getString (mCursor.getColumnIndex ("imageUrl"));
            videoUrl = mCursor.getString (mCursor.getColumnIndex ("videoUrl"));
            Data data = new Data (mid,titleStr, contentStr,imageUrl,videoUrl);
            list.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        dbRead.close ();
    }
}


