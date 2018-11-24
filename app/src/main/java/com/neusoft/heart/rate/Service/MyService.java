package com.neusoft.heart.rate.Service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.neusoft.heart.rate.DataBaseUtil.CommonUtils;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
public class MyService extends Service  {


    private String data = "默认消息";
    private boolean serviceRunning = false;

    int ExecTotal;
    String jsonstr=null;
    // 必须实现的方法，用于返回Binder对象

    public static final String TAG = "MainActivity";
    private Handler mHandler;
    private boolean mRunning = false;
    private  Runnable mBackgroundRunnable;

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("--onBind()--");
        return new MyBinder();
    }

    public class MyBinder extends Binder {
      public   MyService getService() {
            return MyService.this;
        }

        public void setData(String data) {
            MyService.this.data = data;
        }
    }

    // 创建Service时调用该方法，只调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("--onCreate()--");
        serviceRunning = true;

   /*     new Thread() {
            @Override
            public void run() {
                int n = 0;
                while (serviceRunning) {
                    n++;
                    String str = n + data;
                    System.out.println(str);
                    if (dataCallback != null) {
                        dataCallback.dataChanged(str);
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();  */
    }

    // 每次启动Servcie时都会调用该方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("--onStartCommand()--");
        data = intent.getStringExtra("data");
     //   getData();
        return super.onStartCommand(intent, flags, startId);
    }

    // 解绑Servcie调用该方法
    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("--onUnbind()--");
        return super.onUnbind(intent);
    }

    // 退出或者销毁时调用该方法
    @Override
    public void onDestroy() {
        serviceRunning = false;
        System.out.println("--onDestroy()--");
        super.onDestroy();
    }

    DataCallback dataCallback = null;

    public DataCallback getDataCallback() {
        return dataCallback;
    }

    public void setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    // 通过回调机制，将Service内部的变化传递到外部
    public interface DataCallback {
        void dataChanged(String str);
    }


    private synchronized void getData() {
        mRunning=true;
        HandlerThread thread = new HandlerThread("MyHandlerThread");
        thread.start();//创建一个HandlerThread并启动它
          mHandler = new Handler(thread.getLooper());//使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，很有可能阻塞UI线程
        mHandler.post(mBackgroundRunnable);//将线程post到Handler中

        //实现耗时操作的线程
        mBackgroundRunnable = new Runnable() {

            @Override
            public void run() {
                //----------模拟耗时的操作，开始---------------
                while(mRunning){
                    Log.i(TAG, "thread running!");
                    try {
                        jsonstr = CommonUtils.httpRequest("http://192.168.1.25:8080/testmybatis/syndata/getpagecount.do?","POST","pagesize=100&tbl=Employee");
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //----------模拟耗时的操作，结束---------------
            }
        };

        //销毁线程
     //   mHandler.removeCallbacks(mBackgroundRunnable);
     //   thread.stop();
  //      mHandler =null;

    //    thread = new HandlerThread("MyHandlerThread");
    //    thread.start();
   //     mHandler = new Handler(thread.getLooper());





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










        for (int i = 1; i <= ExecTotal; i++) {
            final int index=i;



                //实现耗时操作的线程
                Runnable   runnable = new Runnable() {

                    @Override
                    public void run() {
                        //----------模拟耗时的操作，开始---------------
                        while(mRunning){
                            Log.i(TAG, "thread running!");
                            try {
                                Log.e("System.out", "showLog: " + Thread.currentThread().getName() + "写入中");
                               // jsonstr = CommonUtils.httpRequest("http://192.168.1.25:8080/testmybatis/syndata/getpagecount.do?","POST","pagesize=100&tbl=Employee");
                                final   String outstr="currpage="+String.valueOf(index)+"&keyid=EmployeeID&tbl=Employee&pagesize=100";
                                String json=  CommonUtils.httpRequest("http://192.168.1.25:8080/testmybatis/syndata/syntools.do?","POST",outstr);
                                json=  StringEscapeUtils.unescapeJava(json);
                                json=json.substring(1,json.length()-1);
                                JSONObject jo = JSONObject.fromObject(json);
                                String jsonstr=jo.getString("ls");
                                List<Object> list = JSON.parseArray(jsonstr);
                                List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
                                for (Object o:list){

                                    Map<String,Object> m=(Map<String,Object>) o;
                                    lst.add(m);
                                }

                                for(int j=0;j<lst.size();j++){
                                    Map<String,Object> m1=(Map<String,Object>)lst.get(j);
                                    String EmployeeID=  String.valueOf(m1.get("EmployeeID"));
                                    String Code=String.valueOf(m1.get("Code"));
                                    System.out.println("员工ID:"+EmployeeID);
                                    System.out.println("员工编码:"+Code);
                                    //   Log.i("System.out","员工ID:"+EmployeeID);
                                    //  Log.i("System.out","员工编码:"+Code);
                                }
                                Thread.sleep(1000);
                                Log.e("System.out", "showLog: " + Thread.currentThread().getName() + "写入完成");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //----------模拟耗时的操作，结束---------------
                    }
                };




        }
    }






}
