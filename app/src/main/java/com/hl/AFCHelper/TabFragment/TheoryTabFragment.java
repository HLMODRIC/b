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
import android.widget.LinearLayout;

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
public class TheoryTabFragment extends Fragment {

    private LinearLayout theory_item1;
    private LinearLayout theory_item2;
    private LinearLayout theory_item3;
    private LinearLayout theory_item4;
    private LinearLayout theory_item5;
    private LinearLayout theory_item6;
    private LinearLayout theory_item7;
    private LinearLayout theory_item8;
    private LinearLayout repair_item1;
    private LinearLayout repair_item2;
    private LinearLayout repair_item3;
    private LinearLayout repair_item4;
    private LinearLayout repair_item5;
    private LinearLayout repair_item6;
    private String tableName = null;
    private ImageButton searchButton;

    //1.22
    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private SQLiteDatabase dbRead;
    MyDBOpenHelper dbHelper;
    String titleStr;
    String contentStr;
    int mid;
    private Cursor mCursor;
    private Cursor mCursor2;
    private final static String MESSAGE_THEORY = "theory_fragment";

    public TheoryTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_theory,container,false);
       //理论知识按键
        theory_item1 = (LinearLayout) view.findViewById (R.id.theory_ll_1);
        theory_item2 = (LinearLayout) view.findViewById (R.id.theory_ll_2);
        theory_item3 = (LinearLayout) view.findViewById (R.id.theory_ll_3);
        theory_item4 = (LinearLayout) view.findViewById (R.id.theory_ll_4);
        theory_item5 = (LinearLayout) view.findViewById (R.id.theory_ll_5);
        theory_item6 = (LinearLayout) view.findViewById (R.id.theory_ll_6);
        theory_item7 = (LinearLayout) view.findViewById (R.id.theory_ll_7);
        theory_item8 = (LinearLayout) view.findViewById (R.id.theory_ll_8);
        //维修知识按键
        repair_item1 = (LinearLayout) view.findViewById (R.id.repair_ll_1);
        repair_item2 = (LinearLayout) view.findViewById (R.id.repair_ll_2);
        repair_item3 = (LinearLayout) view.findViewById (R.id.repair_ll_3);
        repair_item4 = (LinearLayout) view.findViewById (R.id.repair_ll_4);
        repair_item5 = (LinearLayout) view.findViewById (R.id.repair_ll_5);
        repair_item6 = (LinearLayout) view.findViewById (R.id.repair_ll_6);
        //查询按键
        searchButton = (ImageButton) view.findViewById (R.id.search_theory_ib);
         return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //理论知识按键监听
        theory_item1.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_1);
                getData ("select * from theory where id < 100");
            }
        });
        theory_item2.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_2);
                getData ("select * from theory where id between 100 and 199");
            }
        });
        theory_item3.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_3);
                getData ("select * from theory where id between 200 and 299");
            }
        });
        theory_item4.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_4);
                getData ("select * from theory where id between 300 and 399");
            }
        });
        theory_item5.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_5);
                getData ("select * from theory where id between 400 and 499");
            }
        });
        theory_item6.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_6);
                getData ("select * from theory where id between 500 and 549");
            }
        });
        theory_item7.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_7);
                getData ("select * from theory where id between 550 and 600");
            }
        });
        theory_item8.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.theory_button_text_8);
                getData ("select * from theory where id between 600 and 650");
            }
        });
        //维护知识按键监听
        repair_item1.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.repair_button_text_1);
                getData ("select * from repair where id < 100");
            }
        });
        repair_item2.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.repair_button_text_2);
                getData ("select * from repair where id between 100 and 199");
            }
        });
        repair_item3.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.repair_button_text_3);
                getData ("select * from repair where id between 200 and 299");
            }
        });
        repair_item4.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.repair_button_text_4);
                getData ("select * from repair where id between 300 and 399");
            }
        });
        repair_item5.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.repair_button_text_5);
                getData ("select * from repair where id between 400 and 499");
            }
        });
        repair_item6.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.repair_button_text_6);
                getData ("select * from repair where id between 500 and 599");
            }
        });
        //查询按键监听
        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //db数据库
                dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
                //查询数据库
                dbRead = dbHelper.getReadableDatabase ();
                mCursor = dbRead.rawQuery ("select * from theory",null);
                mCursor2 = dbRead.rawQuery ("select * from repair",null);
                datas = new ArrayList<Data> ();
                while (mCursor.moveToNext ()) {
                    contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
                    titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
                    mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
                    Data data = new Data (mid, titleStr, contentStr);
                    datas.add (data);
                }
                while (mCursor2.moveToNext ()) {
                    contentStr = mCursor2.getString (mCursor2.getColumnIndex ("content"));
                    titleStr = mCursor2.getString (mCursor2.getColumnIndex ("title"));
                    mid = mCursor2.getInt (mCursor2.getColumnIndex ("id"));
                    Data data = new Data (mid, titleStr, contentStr);
                    datas.add (data);
                }
                mCursor.close ();
                mCursor2.close ();
                dbHelper.close ();
                Intent intent=new Intent ();
                intent.setClass(getActivity(), SeachDataActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable ("data",datas);
                intent.putExtras(bundle);
                intent.putExtra ("message",MESSAGE_THEORY);
                startActivity (intent);
            }
        });

    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    private void getData(String sql) {
        //db数据库
        dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        mCursor = dbRead.rawQuery (sql,null);
        datas = new ArrayList<Data> ();
        while (mCursor.moveToNext ()) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        Intent intent=new Intent ();
        intent.setClass(getActivity(), ListActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString ("table_name",tableName);
        bundle.putSerializable ("data",datas);
        intent.putExtras(bundle);
        startActivity (intent);
    }
}
