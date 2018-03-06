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

import com.hl.AFCHelper.Activity.ContentActivity;
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
public class SearchTabFragment extends Fragment {

    private Button searchButton;
    private Button searchButton2;
    private Button searchButton3;
    private Button searchButton4;
    private Button searchButton5;
    private Button searchButton6;


    private SQLiteDatabase dbRead;
    private ArrayList<Data> datas;
    MyDBOpenHelper dbHelper;
    String contentStr;
    String titleStr;
    int mid;
    private Cursor mCursor;

    private final static String MESSAGE_SEARCH_CHS = "chs_code_search";
    private final static String MESSAGE_SEARCH_BNR = "bnr_code_search";
    private final static String MESSAGE_SEARCH_MBC = "mbc_code_search";
    private final static String MESSAGE_SEARCH_CARD = "card_code_search";
    private final static String MESSAGE_SEARCH_IP = "ip_search";
    private final static String MESSAGE_SEARCH_SCREW = "screw_search";

    public SearchTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_search,container,false);
        searchButton = (Button) view.findViewById(R.id.search_bt_1);
        searchButton2 = (Button) view.findViewById(R.id.search_bt_2);
        searchButton3 = (Button) view.findViewById(R.id.search_bt_3);
        searchButton4 = (Button) view.findViewById(R.id.search_bt_4);
        searchButton5 = (Button) view.findViewById(R.id.search_bt_5);
        searchButton6 = (Button) view.findViewById(R.id.search_bt_6);


        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from chs_code",MESSAGE_SEARCH_CHS);
            }
        });

        searchButton2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from bnr_code",MESSAGE_SEARCH_BNR);
            }
        });

        searchButton3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from mbc_code",MESSAGE_SEARCH_MBC);

            }
        });
        searchButton4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from card_code",MESSAGE_SEARCH_CARD);

            }
        });
        searchButton5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from ip_code",MESSAGE_SEARCH_IP);

            }
        });
        searchButton6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from screw_code",MESSAGE_SEARCH_SCREW);

            }
        });
        return view;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    private void getData(String sql,String message) {
        //db数据库
        dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        mCursor = dbRead.rawQuery (sql,null);
        datas = new ArrayList<Data> ();
        while (mCursor.moveToNext ()) {
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        Intent intent=new Intent ();
        intent.setClass(getActivity(), SeachDataActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable ("data",datas);
        intent.putExtras(bundle);
        intent.putExtra ("message",message);
        startActivity (intent);
    }
}
