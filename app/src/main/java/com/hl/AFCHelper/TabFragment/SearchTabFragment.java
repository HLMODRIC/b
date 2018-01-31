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

import com.hl.AFCHelper.Activity.ContentActivity;
import com.hl.AFCHelper.Activity.ListActivity;
import com.hl.AFCHelper.Activity.SeachDataActivity;
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;

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


    private SQLiteDatabase dbRead;
    MyDBOpenHelper dbHelper;
    String contentStr;
    private Cursor mCursor;

    private final static String MESSAGE_SEARCH_CHS = "chs_code_search";
    private final static String MESSAGE_SEARCH_BNR = "bnr_code_search";

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

        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.putExtra ("message",MESSAGE_SEARCH_CHS);
                intent.setClass(getActivity(), SeachDataActivity.class);
                startActivity (intent);
            }
        });

        searchButton2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.putExtra ("message",MESSAGE_SEARCH_BNR);
                intent.setClass(getActivity(), SeachDataActivity.class);
                startActivity (intent);
            }
        });

        searchButton3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("select * from chs_code where id = 1");

            }
        });
         return view;
    }

    private void getData(String sql) {
        //db数据库
        dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        mCursor = dbRead.rawQuery (sql,null);
        while (mCursor.moveToNext ()) {
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
        }
        mCursor.close ();
        dbHelper.close ();
        Intent intent=new Intent ();
        intent.setClass(getActivity(), ContentActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString ("data",contentStr);
        intent.putExtras(bundle);
        startActivity (intent);
    }
}
