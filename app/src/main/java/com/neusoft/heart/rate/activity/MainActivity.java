package com.neusoft.heart.rate.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.neusoft.heart.rate.DataBaseUtil.CommonUtils;
import com.neusoft.heart.rate.DataBaseUtil.DBOpenHelper;
import com.neusoft.heart.rate.R;
//import com.neusoft.heart.rate.Service.MyService;
import com.neusoft.heart.rate.Service.MyService;
import com.neusoft.heart.rate.bean.EchartsDataBean;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.os.Message;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.linechart_bt)
    Button linechartBt;
    @BindView(R.id.barchart_bt)
    Button barchartBt;
    @BindView(R.id.piechart_bt)
    Button piechartBt;
    @BindView(R.id.morechart_bt)
    Button morechartBt;
    @BindView(R.id.addchart_bt)
    Button addchartBt;
    @BindView(R.id.chartshow_wb)
    WebView chartshowWb;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    String TAG;
    private ProgressDialog dialog;

    DBOpenHelper dbOpenHelper=null;
    SQLiteDatabase sqldb;
    private int ExecTotal;
    String jsonstr;
    private Intent intent = null;

  //  MyServiceConn myServiceConn;
  //  MyService.MyBinder binder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   chartshowWb =(WebView) findViewById(R.id.chartshow_wb);
     //  DataTask dt=  new DataTask();
     //  dt.execute("同步数据");
        initView();
        initData();
        initListener();

      // intent = new Intent(this, MyService.class);
     //  stopService(intent);
       // myServiceConn = new MyServiceConn();
        //  startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MainActivity.class.getSimpleName(), "onStart()");
    }


    @Override
    protected void onResume(){
        super.onResume();
        DataTask dt=  new DataTask();
        dt.execute("同步数据");
    }


    private void initView() {
        ButterKnife.bind(this);
    }

    private void initData() {
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("玩儿命加载中...");

       TAG = this.getClass().getName();

        dbOpenHelper=new DBOpenHelper(MainActivity.this,"a001.db",null,2);//1-》2 就为升级
        sqldb=dbOpenHelper.getWritableDatabase();//通过helper的getWritableDatabase(),getReadableDatabase得到SQLiteOpenHelper所创建的数据库 cursor.getColumnIndex("name")
        //sqldb.execSQL("insert into Employee(EmployeeID,Code,Name,DepartmentID) values('00A','001','123','0AE')");
        Cursor cursor = sqldb.rawQuery("select * from Employee order by EmployeeID desc limit 0,9 ", null);

        while(cursor.moveToNext()){
            //遍历出表名
            String EmployeeID = cursor.getString(0);
            String Code = cursor.getString(1);
            String name = cursor.getString(2);//cursor.getColumnIndexOrThrow("DepartmentID")
            int did= cursor.getColumnIndex("DepartmentID"); //根据列名得到id 值 再getString(id) 输出
             String DepartmentID=cursor.getString(did);

            Log.i("System.out","name的值"+name);
            Log.i("System.out", "卡号:"+Code);
            Log.i("System.out", "ID:"+EmployeeID);
            Log.i("System.out", "部门的ID:"+DepartmentID);
        }
        cursor.close();
        //sqldb.close();
      // getData();



    }

  //  public void SynData(){

    //}








    @SuppressLint("SetJavaScriptEnabled")
    private void initListener() {
        linechartBt.setOnClickListener(this);
        barchartBt.setOnClickListener(this);
        piechartBt.setOnClickListener(this);
        morechartBt.setOnClickListener(this);
        addchartBt.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);

        //进行webwiev的一堆设置
        chartshowWb.getSettings().setAllowFileAccess(true);
        chartshowWb.getSettings().setJavaScriptEnabled(true);


// displayWebview.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用



        //chartshowWb.getSettings().setDomStorageEnabled(true);
        chartshowWb.loadUrl("file:///android_asset/echart/myechart.html");
        chartshowWb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //最好在这里调用js代码 以免网页未加载完成
                chartshowWb.loadUrl("javascript:createChart('line'," + EchartsDataBean.getInstance().getEchartsLineJson() + ");");
                findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    String newDataX = "['13:20', '13:30', '13:40', '13:50', '14:00', '14:10', '14:20', '14:30', " +
            "'14:40','14:50','15:00','15:10','15:20','15:30','15:40','15:50','16:00'," +
            "'16:10','16:20','16:30']";

    String newDataY = "[0 ,70,60, 60, 90, 56, 150, 60, 80,0 ,70,60, 60,60,60,140,60,0 ,70,60]";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.linechart_bt:
                chartshowWb.loadUrl("javascript:createChart('line'," + EchartsDataBean.getInstance().getEchartsLineJson() + ");");
                findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
                break;
            case R.id.barchart_bt:
                chartshowWb.loadUrl("javascript:createChart('bar'," + EchartsDataBean.getInstance().getEchartsBarJson() + ");");
                findViewById(R.id.rl_bottom).setVisibility(View.GONE);
                break;
            case R.id.piechart_bt:
                chartshowWb.loadUrl("javascript:createChart('pie'," + EchartsDataBean.getInstance().getEchartsPieJson() + ");");
                findViewById(R.id.rl_bottom).setVisibility(View.GONE);
                break;
            case R.id.morechart_bt://createMapChart
                chartshowWb.loadUrl("javascript:createChart('more'," + EchartsDataBean.getInstance().getEchartsLineJson() + ");");
                findViewById(R.id.rl_bottom).setVisibility(View.GONE);
                break;
            case R.id.iv_left:
                dealwithLeft();
                break;
            case R.id.iv_right:
                dealwithRight();
                break;
            default:
                chartshowWb.loadUrl("javascript:updateDataXY(" + newDataX + "," + newDataY + ");");
                findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * start 和 end 意为拖放的起始点 范围均为  0-100;
     * js中设置的默认初始值 , 和 activity中设置的默认值   两者必须一致, 不然会有错乱
     */
    int start = 20, end = 85;

    private void dealwithLeft() {
        if (start >= 5) {
            start -= 5;
            if (end <= 105 && end >= start + 15) {
                end -= 5;
            }
            if (!ivRight.isEnabled()) {
                ivRight.setEnabled(true);
                ivRight.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.right_blue_bg));
            }
            chartshowWb.loadUrl("javascript:updateZoomState(" + start + "," + end + ");");
        } else {
            if (ivLeft.isEnabled()) {
                ivLeft.setEnabled(false);
                ivLeft.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.left_gray_bg));
            }
            Log.i(TAG, "start == " + start + "  end== " + end);
        }
    }

    private void dealwithRight() {
        if (end <= 100) {
            end += 5;
            if (start < end - 15) {
                start += 5;
            }
            if (!ivLeft.isEnabled()) {
                ivLeft.setEnabled(true);
                ivLeft.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.left_blue_bg));
            }
            chartshowWb.loadUrl("javascript:updateZoomState(" + start + "," + end + ");");
        } else {
            if (ivRight.isEnabled()) {
                ivRight.setEnabled(false);
                ivRight.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.right_gray_bg));
            }
            Log.i(TAG, "start == " + start + "  end== " + end);
        }
    }


/*

    class MyServiceConn implements ServiceConnection {
        // 服务被绑定成功之后执行
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // IBinder service为onBind方法返回的Service实例
            binder = (MyService.MyBinder) service;
            binder.getService().setDataCallback(new MyService.DataCallback() {
                //执行回调函数
                @Override
                public void dataChanged(String str) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("str", str);
                    msg.setData(bundle);
                    //发送通知
                    handler.sendMessage(msg);
                }
            });
        }

        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                //在handler中更新UI
              //  tv_out.setText(msg.getData().getString("str"));
            };
        };

        // 服务奔溃或者被杀掉执行
        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
        }
    }

    */

    public class DataTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {  //三个点，代表可变参数
            //使用网络链接类HttpClient类完成对网络数据的提取，即完成对图片的下载功能
            SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
           // String sessionid=userInfo.getString("userid",null);
           String cookie= userInfo.getString("sessionid",null);
            System.out.println("SharedPreferences的值："+cookie);
           // CommonUtils cu=new CommonUtils();
            CommonUtils.session_id=cookie;
            jsonstr = CommonUtils.httpRequest("http://192.168.1.25:8080/testmybatis/syndata/getpagecount.do?","POST","pagesize=100&tbl=Employee");
            System.out.println("网络返回的json:"+jsonstr);
            // String sr = jsonstr.replaceAll("\","");
            jsonstr= StringEscapeUtils.unescapeJava(jsonstr);  //去掉转义字符\
            System.out.println("网络返回去去掉转义字符的json:"+jsonstr);
            // Log.i("System.out",jsonstr);
            jsonstr = jsonstr.substring(1,jsonstr.length() - 1);  //去掉第一个“号与最一个"号，成为json字符串
            System.out.println("真正的json:"+jsonstr);
            // Log.i("System.out",jsonstr);

            JSONObject jsonObject = JSONObject.fromObject(jsonstr);

            //int weathers =
            //  Log.i("System.out",jsonObject.getString("totalpage"));
            // Log.i("System.out",String.valueOf(jsonObject.get("totalpage")));
            System.out.println("total的值："+jsonObject.getString("totalpage"));
            ExecTotal =Integer.parseInt(jsonObject.getString("totalpage"));

            //实现耗时操作的线程
        //     Runnable    runnable = new Runnable() {

          //     @Override
           //   public void run() {
            //----------模拟耗时的操作，开始---------------
            for (int i = 1; i <= ExecTotal; i++) {
                final int index = i;


                        Log.i(TAG, "thread running!");
                        try {
                           // Thread.sleep(1000);
                          //  Log.e("System.out", "showLog: " + Thread.currentThread().getName() + "写入中");
                            // jsonstr = CommonUtils.httpRequest("http://192.168.1.25:8080/testmybatis/syndata/getpagecount.do?","POST","pagesize=100&tbl=Employee");
                            final String outstr = "currpage=" + String.valueOf(index) + "&keyid=EmployeeID&tbl=Employee&pagesize=100&field=EmployeeID,Code,Name,HelpCode,DepartmentID";
                            String json = CommonUtils.httpRequest("http://192.168.1.25:8080/testmybatis/syndata/syntools.do?", "POST", outstr);
                            json = StringEscapeUtils.unescapeJava(json);
                            json = json.substring(1, json.length() - 1);
                            JSONObject jo = JSONObject.fromObject(json);
                            String jsonstr = jo.getString("ls");
                            List<Object> list = JSON.parseArray(jsonstr);
                            List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
                            for (Object o : list) {

                                Map<String, Object> m = (Map<String, Object>) o;
                                lst.add(m);
                            }

                            for (int j = 0; j < lst.size(); j++) {
                                Map<String, Object> m1 = (Map<String, Object>) lst.get(j);
                                String EmployeeID = String.valueOf(m1.get("EmployeeID"));
                                String Code = String.valueOf(m1.get("Code"));


                              //  System.out.println("员工ID:" + EmployeeID);
                              //  System.out.println("员工编码:" + Code);
                                //   Log.i("System.out","员工ID:"+EmployeeID);
                                //  Log.i("System.out","员工编码:"+Code);
                                //   if (EmployeeID !=null || !"".equals(EmployeeID)) {
                             //   String sql = "if not exists(select EmployeeID from Employee where EmployeeID='"+EmployeeID+"')"+"\t"+
                               //         "insert into Employee(EmployeeID,Code) values('"+EmployeeID+"','"+Code+"')";
                                String sql = "replace into Employee(EmployeeID, Code,Name,HelpCode,DepartmentID) VALUES ('"+EmployeeID+"','"+Code+"','"+String.valueOf(m1.get("Name"))+"','"+String.valueOf(m1.get("HelpCode"))+"','"+String.valueOf(m1.get("DepartmentID"))+"')";
                                sqldb.execSQL(sql);
                                //   }
                            }
                            //Thread.sleep(1000);
                            //Log.e("System.out", "showLog: " + Thread.currentThread().getName() + "写入完成");Interrupted
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                   }
                //   }
                  //  };
                   // Thread t = new Thread(runnable);
                   //  t.start();
                   //----------模拟耗时的操作，结束---------------

           Cursor c= sqldb.rawQuery("select count(1) from Employee",null);
            while (c.moveToNext()){
                System.out.println("总记录数为:" + c.getString(0));
                //c.get
            }

            return "同步数据成功";


        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            //    更新ProgressDialog的进度条
            dialog.setProgress(values[0]);
        }

        //主要是更新UI
    //    @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //    将doInBackground方法返回的byte[]解码成要给Bitmap
          //  Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length) ;
            //    更新我们的ImageView控件
        //    imageView.setImageBitmap(bitmap) ;//更新UI
            //    使ProgressDialog框消失
           // dialog.dismiss() ;
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
        }




    }







}

