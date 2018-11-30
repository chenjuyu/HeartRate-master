package com.neusoft.heart.rate.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.neusoft.heart.rate.DataBaseUtil.CommonDatabase;
import com.neusoft.heart.rate.DataBaseUtil.DBOpenHelper;
import com.neusoft.heart.rate.R;
import com.neusoft.heart.rate.bean.Employee;
import com.neusoft.heart.rate.bean.MyAdapter;
import com.neusoft.heart.rate.bean.MyOnScrollListener;
import com.neusoft.heart.rate.bean.Student;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ListViewPage extends AppCompatActivity implements MyOnScrollListener.OnloadDataListener {

    //ListView展示的数据项
   // private List<Student> data;
    private List<Employee> data;
    //ListView控件
    private ListView mList;

    //自定义适配器
    MyAdapter adapter;

    //底部加载更多布局
    View footer;

    private DBOpenHelper dbOpenHelper=null;
    private SQLiteDatabase sqldb=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_page);

        dbOpenHelper=new DBOpenHelper(ListViewPage.this,"a001.db",null,2);//1-》2 就为升级  加载数据库连接
    //    CommonDatabase c=new CommonDatabase();
        sqldb=dbOpenHelper.getWritableDatabase();//通过helper的getWritableDatabase(),getReadableDatabase得到SQLiteOpenHelper所创建的数据库 cursor.getColumnIndex("name")

        //首先加载默认数据，这里设置为10条
        getData();
        //显示到ListView上
        showListView(data);
        //自定义的滚动监听事件

        MyOnScrollListener onScrollListener = new MyOnScrollListener(footer,this);

        //设置接口回调
        onScrollListener.setOnLoadDataListener(this);
        //设置ListView的滚动监听事件
        mList.setOnScrollListener(onScrollListener);

    }

    /**
     * 初始化ListView数据，默认设置为10条
     */
    private void getData() {
        data = new ArrayList<>();
        Employee employee=null;
        Cursor cursor=null;
        cursor= sqldb.rawQuery("select EmployeeID,Code,Name from Employee  order by EmployeeID  limit 9 offset 0 ",null);//offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
        while (cursor.moveToNext()){
            employee=new Employee();
            employee.setEmployeeID(cursor.getString(0));
            employee.setCode(cursor.getString(1));
            employee.setName(cursor.getString(2));
            data.add(employee);
        }
     //   Student stu = null;
      /*  for (int i = 0; i < 10; i++) {
            stu = new Student();
            stu.setName("姓名" + i);
            stu.setSex(i % 2 == 0 ? "男" : "女");
            data.add(stu);
        } */
    }

    /**
     * 将数据加载到ListView上
     *
     * @param data List<Student> data
     */
    private void showListView(List<Employee> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) findViewById(R.id.mList);
            //将底部加载一个加载更多的布局
            footer = LayoutInflater.from(this).inflate(R.layout.foot_boot, null);
            //初始状态为隐藏
            footer.setVisibility(View.GONE);
            //加入到ListView的底部
            mList.addFooterView(footer);

            //创建adpter数据
            adapter = new MyAdapter(data);
            //设置adapter
            mList.setAdapter(adapter);
        } else {
            //不为空，则刷新数据
            this.data.addAll(data);
            //提醒ListView重新更新数据
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadData(List<Employee> data) {
        //加载数据完成后，展示数据到ListView  List<Student> data
        showListView(data);
    }

}
