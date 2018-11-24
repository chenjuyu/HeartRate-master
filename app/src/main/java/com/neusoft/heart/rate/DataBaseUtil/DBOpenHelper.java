package com.neusoft.heart.rate.DataBaseUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBOpenHelper extends SQLiteOpenHelper {
   // private static final String name = "a001.db";//数据库名称
  //  private static final int version = 1;//数据库版本

    public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DBOpenHelper", "DBOpenHelperDBOpenHelperDBOpenHelperDBOpenHelper");
        db.execSQL("CREATE TABLE IF NOT EXISTS Employee (EmployeeID varchar(60) primary key , Code varchar(20) UNIQUE , Name varchar(50),HelpCode varchar(60), DepartmentID varchar(60), EmployeeTypeID varchar(60))");

        db.execSQL("CREATE TABLE IF NOT EXISTS EmployeeType (EmployeeTypeID varchar(20) primary key ,EmployeeType varchar(50) UNIQUE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS Department (DepartmentID varchar(20) primary key , Code varchar(60) UNIQUE , Department varchar(60),DepartmentTypeID varchar(60))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Customer (CustomerID varchar(20) primary key , Customer varchar(60), CustomerTypeID varchar(60),Code varchar(60) UNIQUE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       Log.e("DBOpenHelper", "onUpgradeonUpgradeonUpgradeonUpgrade");
        db.execSQL("DROP TABLE IF EXISTS Employee");
        db.execSQL("DROP TABLE IF EXISTS EmployeeType");
        db.execSQL("DROP TABLE IF EXISTS Department");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        onCreate(db);


    }
  //降级
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        try {
            //第一、先把t_message 未来的表，改名
            String alt_table="alter table t_message rename to t_message_bak";
            db.execSQL(alt_table);
            //第二、建立2.0的表结构
            String crt_table = "create table t_message (id int primary key,userName varchar(50),lastMessage varchar(50),datetime  varchar(50))";
            db.execSQL(crt_table);
            //第三、把备份的数据，copy到 新建的2.0的表
            String cpy_table="insert into t_message select id,userName,lastMessage,datetime from t_message_bak";
            db.execSQL(cpy_table);
            //第四、把备份表drop掉
            String drp_table = "drop table if exists t_message_bak";
            db.execSQL(drp_table);

        } catch (Exception e) {

            //失败
            Log.i("hi", "降级失败，重新建立");
            String sql_drop_old_table = "drop table if exists t_message";
            String sql_message = "create table t_message (id int primary key,userName varchar(50),lastMessage varchar(50),datetime  varchar(50))";
            String sql_init_1 = "insert into t_message values (2,'TT2','一起去旅游','10月1号')";
            String sql_init_2 = "insert into t_message values (2,'TT2','一起去旅游','10月1号')";
            String sql_init_3 = "insert into t_message values (2,'TT2','一起去旅游','10月1号')";
            db.execSQL(sql_drop_old_table);
            db.execSQL(sql_message);
            db.execSQL(sql_init_1);
            db.execSQL(sql_init_2);
            db.execSQL(sql_init_3);
        }
    }

}
