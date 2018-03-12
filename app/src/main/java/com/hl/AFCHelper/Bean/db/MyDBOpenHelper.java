package com.hl.AFCHelper.Bean.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huanglei on 2018/1/16.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context){
        super(context,"app.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "CREATE TABLE IF NOT EXISTS repair(id integer primary key autoincrement,title varchar,content varchar);";     //建数据表
       // db.execSQL(sql);    //执行SQL语句
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
    }
}
