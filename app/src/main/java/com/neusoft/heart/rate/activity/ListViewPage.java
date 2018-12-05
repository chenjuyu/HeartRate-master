package com.neusoft.heart.rate.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.neusoft.heart.rate.DataBaseUtil.CommonDatabase;
import com.neusoft.heart.rate.DataBaseUtil.DBOpenHelper;
import com.neusoft.heart.rate.R;
import com.neusoft.heart.rate.bean.Employee;
import com.neusoft.heart.rate.bean.MyAdapter;
import com.neusoft.heart.rate.bean.MyOnScrollListener;
import com.neusoft.heart.rate.bean.Student;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.view.View.OnKeyListener;
public class ListViewPage extends AppCompatActivity implements MyOnScrollListener.OnloadDataListener {

    //ListView展示的数据项
   // private List<Student> data;
    private List<Employee> data;
    //ListView控件
    private ListView mList;

    //搜索控件
    private EditText employeeid;

    //自定义适配器
    MyAdapter adapter;

    //底部加载更多布局
    View footer;

    private DBOpenHelper dbOpenHelper=null;
    private SQLiteDatabase sqldb=null;

    private  MyOnScrollListener onScrollListener;

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

        onScrollListener = new MyOnScrollListener(footer,this);


        //onScrollListener.onScroll();

        //设置接口回调
        onScrollListener.setOnLoadDataListener(this);
        //设置ListView的滚动监听事件
        mList.setOnScrollListener(onScrollListener);

        init();

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
    private  void init() {

        employeeid = (EditText) findViewById(R.id.employeeid);
        //查到ListView控件
        mList = (ListView) findViewById(R.id.mList);

        employeeid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");

            }

            //limit 9 offset 0
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 输入的内容变化的监听
                footer.setVisibility(View.GONE);
                Log.e("输入内容变化输出：", employeeid.getText().toString());
                if (!"".equals(employeeid.getText().toString())) {
                    Log.e("执行到555：", employeeid.getText().toString());
                    data = new ArrayList<>();

                    Employee employee = null;
                    Cursor cursor = null;
                    cursor = sqldb.rawQuery("select EmployeeID,Code,Name from Employee where Code like '%" + employeeid.getText().toString() + "%' or name like '%" + employeeid.getText().toString() + "%' limit 10 offset 0    ", null);//offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
                    while (cursor.moveToNext()) {
                        employee = new Employee();
                        employee.setEmployeeID(cursor.getString(0));
                        employee.setCode(cursor.getString(1));
                        employee.setName(cursor.getString(2));
                        data.add(employee);
                    }

                    // showListView(data);

                    adapter = new MyAdapter(data);
                    mList.setAdapter(adapter);


                    onScrollListener = new MyOnScrollListener(footer, ListViewPage.this);
                    //传查询参数
                    onScrollListener.conditon = employeeid.getText().toString();

                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(ListViewPage.this);
                    //设置ListView的滚动监听事件
                    //  onScrollListener.loadMoreData(employeeid.getText().toString());
                    mList.setOnScrollListener(onScrollListener);


                }else{
                    onScrollListener = new MyOnScrollListener(footer, ListViewPage.this);
                    //传查询参数
                    onScrollListener.conditon =null;

                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(ListViewPage.this);
                    //设置ListView的滚动监听事件
                    //  onScrollListener.loadMoreData(employeeid.getText().toString());
                    mList.setOnScrollListener(onScrollListener);


                }
                Log.e("输入过程中执行该方法", "文字变化");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 输入后的监听

                Log.e("输入结束执行该方法", "输入结束");
            }


        });
//______________________________________

        employeeid.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_DEL){

                    Log.i("System.out","执行退格键");
                    data = new ArrayList<>();

                    Employee employee = null;
                    Cursor cursor = null;
                    cursor = sqldb.rawQuery("select EmployeeID,Code,Name from Employee where Code like '%" + employeeid.getText().toString() + "%' or name like '%" + employeeid.getText().toString() + "%' limit 10 offset 0    ", null);//offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
                    while (cursor.moveToNext()) {
                        employee = new Employee();
                        employee.setEmployeeID(cursor.getString(0));
                        employee.setCode(cursor.getString(1));
                        employee.setName(cursor.getString(2));
                        data.add(employee);
                    }

                    // showListView(data);

                    adapter = new MyAdapter(data);
                    mList.setAdapter(adapter);

                    return true;
                }else {
                    return false;
                }
            }
        });











//________________________________________________
        mList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                //我们需要的内容，跳转页面或显示详细信息

                //获得选中项的HashMap对象
                // Map<String,String> map=(HashMap<String,String>)mList.getItemAtPosition(position);
                // List<Employee> map= JSON.parseArray(String.valueOf(mList.getItemAtPosition(position)),Employee.class);
                // List<Employee> map=(List<Employee>)mList.getItemAtPosition(position);
                System.out.println(mList.getItemAtPosition(position));
                Employee map = (Employee) mList.getItemAtPosition(position);
                String title = map.getCode();
                String content = map.getName();
                android.widget.Toast.makeText(getApplicationContext(),
                        "你选择了第" + position + "个Item，itemTitle的值是：" + title + "itemContent的值是:" + content+"--ID的值为:"+map.getEmployeeID(),
                        android.widget.Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("EmployeeID", title);
                bundle.putString("name", content);
                android.content.Intent intent = new android.content.Intent();
                intent.putExtras(bundle);
                //intent.putExtra("result", result);
                //intent.putExtras("bundle",(bundle));
                // intent.setClass(SpinnerActivity.this, Main2Activity.class);
                //android.util.Log.i("message", message[arg2]);
                //startActivity(intent);
                //   intent.putExtra("data_return", "Hello");

                //    setResult(RESULT_OK, intent);

                //    finish();


            }
        });

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
