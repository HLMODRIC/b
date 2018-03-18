package com.hl.AFCHelper.Until;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hl.AFCHelper.Bean.db.MyDBOpenHelper;

public class UserService {
    private MyDBOpenHelper dbHelper;
    public UserService(Context context){
        dbHelper=new MyDBOpenHelper (context);
    }

    //登录用
    public boolean login(String username,String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=db.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst ()){
            cursor.close();
            return true;
        }
        return false;
    }
}
