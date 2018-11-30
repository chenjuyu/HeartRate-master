package com.neusoft.heart.rate.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import com.neusoft.heart.rate.DataBaseUtil.DBOpenHelper;
import com.neusoft.heart.rate.activity.MainActivity;

public class MyOnScrollListener implements AbsListView.OnScrollListener {

    //ListView总共显示多少条
    private int totalItemCount;

    //ListView最后的item项
    private int lastItem;

    //用于判断当前是否在加载
    private boolean isLoading;

    //底部加载更多布局
    private View footer;

    //接口回调的实例
    private OnloadDataListener listener;

    //数据
    //private List<Student> data;
    private List<Employee> data;

   private DBOpenHelper dbOpenHelper=null;
   private SQLiteDatabase sqldb=null;


    public MyOnScrollListener(View footer,Context context) {

        this.footer = footer;
      //  context=this;
        dbOpenHelper=new DBOpenHelper(context,"a001.db",null,2);//1-》2 就为升级  加载数据库连接
        sqldb=dbOpenHelper.getWritableDatabase();//通过helper的getWritableDatabase(),getReadableDatabase得到SQLiteOpenHelper所创建的数据库 cursor.getColumnIndex("name")


    }
    //设置接口回调的实例
    public void setOnLoadDataListener(OnloadDataListener listener) {
        this.listener = listener;
    }

    /**
     * 滑动状态变化
     *
     * @param view
     * @param scrollState 1 SCROLL_STATE_TOUCH_SCROLL是拖动   2 SCROLL_STATE_FLING是惯性滑动  0SCROLL_STATE_IDLE是停止 , 只有当在不同状态间切换的时候才会执行
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //如果数据没有加载，并且滑动状态是停止的，而且到达了最后一个item项
        if (!isLoading && lastItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            //显示加载更多
            footer.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            //模拟一个延迟两秒的刷新功能
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        //开始加载更多数据
                        loadMoreData(null);
                        //回调设置ListView的数据
                        listener.onLoadData(data);
                        //加载完成后操作什么
                        loadComplete();
                    }
                }
            }, 2000);
        }
    }

    /**
     * 当加载数据完成后，设置加载标志为false表示没有加载数据了
     * 并且设置底部加载更多为隐藏
     */
    private void loadComplete() {
        isLoading = false;
        footer.setVisibility(View.GONE);

    }

    /**
     * 开始加载更多新数据，这里每次只更新三条数据
     */
    private void loadMoreData(String conditon) {
        isLoading = true;
       // Student stu = null;
       Employee employee=null;
        Cursor cursor=null;
        int count=0;
        data = new ArrayList<>();
       if (conditon !=null) {
            cursor = sqldb.rawQuery("select count(*) from Employee where Code like '%?%' or Name like '%?%' ", new String[]{conditon});
       }else {
            cursor = sqldb.rawQuery("select count(*) from Employee ",null);
       }
       while (cursor.moveToNext()){
          count=Integer.parseInt(cursor.getString(0));
       }
        int pageCount=count/10;

        cursor= sqldb.rawQuery("select EmployeeID,Code,Name from Employee  order by EmployeeID  limit 10 offset 10 ",null);//offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
        while (cursor.moveToNext()){
            employee=new Employee();
            employee.setEmployeeID(cursor.getString(0));
            employee.setCode(cursor.getString(1));
            employee.setName(cursor.getString(2));
            data.add(employee);
        }



    /*    for (int i = 0; i < 3; i++) {
            stu = new Student();
            stu.setName("新名字" + i);
            stu.setSex("新性别" + i);
            data.add(stu);
        } */
    }


    /**
     * 监听可见界面的情况
     *
     * @param view             ListView
     * @param firstVisibleItem 第一个可见的 item 的索引
     * @param visibleItemCount 可以显示的 item的条数
     * @param totalItemCount   总共有多少个 item
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当可见界面的第一个item  +  当前界面多有可见的界面个数就可以得到最后一个item项了
        lastItem = firstVisibleItem + visibleItemCount;
        //总listView的item个数
        this.totalItemCount = totalItemCount;
    }
    //回调接口
    public interface OnloadDataListener {
        void onLoadData(List<Employee> data);//List<Student> data
    }
}
