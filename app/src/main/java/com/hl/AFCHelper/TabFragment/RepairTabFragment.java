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
import com.hl.AFCHelper.R;
import com.hl.AFCHelper.db.Data;
import com.hl.AFCHelper.db.MyDBOpenHelper;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class RepairTabFragment extends Fragment  {

    private Button listButton;
    private Button listButton2;
    private Button listButton3;
    private Button listButton4;
    private Button listButton5;
    private Button listButton6;
    private ImageButton searchButton;
    private final static String MESSAGE_REPAIR = "repair_fragment";

    //1.22
    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private SQLiteDatabase dbRead;
    MyDBOpenHelper dbHelper;
    String titleStr;
    String contentStr;
    int mid;
    private Cursor mCursor;

    public RepairTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_repair,container,false);
        listButton = (Button) view.findViewById(R.id.repair_bt_1);
        listButton2 = (Button) view.findViewById(R.id.repair_bt_2);
        listButton3 = (Button) view.findViewById(R.id.repair_bt_3);
        listButton4 = (Button) view.findViewById(R.id.repair_bt_4);
        listButton5 = (Button) view.findViewById(R.id.repair_bt_5);
        listButton6 = (Button) view.findViewById(R.id.repair_bt_6);
        searchButton = (ImageButton) view.findViewById (R.id.search_repair_ib);


        //1.22


        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("repair");
            }
        });
        listButton2.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("repair");
            }
        });
        listButton3.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("repair");
            }
        });
        listButton4.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("repair");
            }
        });
        listButton5.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("repair");
            }
        });
        listButton6.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getData ("repair");
            }
        });
        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.putExtra ("message",MESSAGE_REPAIR);
                intent.setClass(getActivity(), SeachDataActivity.class);
                startActivity (intent);
            }
        });
    }

    private void getData(String mData ) {
        //db数据库
        dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        dbRead = dbHelper.getReadableDatabase ();
        mCursor = dbRead.query (mData, null, null, null, null, null, null);  //查询所有数据
                datas = new ArrayList<Data> ();
        while (mCursor.moveToNext ()) {
            //for (int i = 0; i < num; i++) {
            mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }
        dbHelper.close ();
        Intent intent=new Intent ();
        intent.setClass(getActivity(), ListActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable ("data",datas);
        intent.putExtras(bundle);
        startActivity (intent);

    }
}