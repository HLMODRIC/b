package com.hl.AFCHelper.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hl.AFCHelper.Activity.ListActivity;
import com.hl.AFCHelper.Adapter.BaseRecyclerAdapter;
import com.hl.AFCHelper.Adapter.TheoryListDataAdapter;
import com.hl.AFCHelper.MyApplication;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.Bean.Data;
import com.hl.AFCHelper.Bean.db.MyDBOpenHelper;
import com.squareup.leakcanary.RefWatcher;
//import com.squareup.leakcanary.RefWatcher;
import java.util.ArrayList;
import java.util.List;

public class TheoryListFragment extends Fragment {

    private ArrayList<Data> mData;
    private ArrayList<Data> mList;
    private int mid;
    private String titleStr;
    private String contentStr;
    private View view;
    private String imageUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.recycler_theory_list, container, false);
        //初始化数据，布局
        initData ();
        initView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mData.clear ();
        mData = null;
        System.gc ();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
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
    private void initView(){
        RecyclerView crimeRecyclerView = view.findViewById (R.id.theory_recycler_view);
        crimeRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        TheoryListDataAdapter adapter = new TheoryListDataAdapter (getContext (),R.layout.theory_recycler_list_item, mData);
        adapter.setItemClickListener (new BaseRecyclerAdapter.onItemClickListener () {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent=new Intent (getActivity (),ListActivity.class);
                Bundle bundle=new Bundle();
                getTabData (mData.get (position).getNew_content ());
                bundle.putSerializable ("data",mList);
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
        Cursor mCursor = dbRead.rawQuery ("select * from tab_name where id < 100",null);
        mData = new ArrayList<> ();
        while (mCursor.moveToNext ()) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            imageUrl = mCursor.getString (mCursor.getColumnIndex ("imageID"));
            Data data = new Data (mid, titleStr, contentStr, imageUrl);
            Log.d ("1111",imageUrl);
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
        mList = new ArrayList<> ();
        while (mCursor.moveToNext ()) {
             mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
             titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
             contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            mList.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        dbRead.close ();
    }
}


