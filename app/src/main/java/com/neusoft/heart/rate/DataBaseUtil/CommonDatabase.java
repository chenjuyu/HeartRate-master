package com.neusoft.heart.rate.DataBaseUtil;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class CommonDatabase {

    private DBOpenHelper dbHelper;
    private SQLiteDatabase sqldb;
    public  SQLiteDatabase getSqliteObject(Context context,String db_name){
        dbHelper = new DBOpenHelper(context,db_name,null,2);
        sqldb = dbHelper.getWritableDatabase();
        return sqldb;
    }

}
