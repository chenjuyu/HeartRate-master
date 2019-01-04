package com.neusoft.heart.rate.activity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.neusoft.heart.rate.DataBaseUtil.CommonUtils;
import com.neusoft.heart.rate.R;
import com.neusoft.heart.rate.bean.News;
import com.neusoft.heart.rate.bean.NewsAdapter;
import com.neusoft.heart.rate.bean.WGoods;
import com.neusoft.heart.rate.bean.WGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView extends AppCompatActivity {
    private android.support.v7.widget.RecyclerView  recycler_view;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recycler_view);//R.layout.activity_recycler_view
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);












     /*   for (int i=0;i<=10;i++)//添加数据
        {
            News news =new News("https://weixin.fxsoft88.com/WeeSee_images/goodsimg/2018-03-19-15-113363.jpg","这是一条新闻"+String.valueOf(i),"伏羲软件","2018-12-27","www.fuxi.com");

            dataItems.add(news);


        } */



        GetGoods a=   new GetGoods("http://192.168.1.25:8080/testmybatis/syndata/syntools.do?");//.execute("查询货品数据");
        a.execute();
        a.setOnDataFinishedListener(new OnDataFinishedListener() {
            @Override
            public void onDataSuccessfully(String data) {

                List<WGoods> dataItems=JSON.parseArray(data,WGoods.class);
                recycler_view =(android.support.v7.widget.RecyclerView)findViewById(R.id.recycler_view);
                //  recycler_view.getLayoutManager();
                recycler_view.setLayoutManager(new LinearLayoutManager(RecyclerView.this));
                //   NewsAdapter  adapter =new NewsAdapter();

                System.out.println("输出list:"+dataItems);
                WGoodsAdapter adapter=new WGoodsAdapter();


                adapter.setDataItems(dataItems);

                recycler_view.setAdapter(adapter);

                adapter.setOnItemClickListener(new WGoodsAdapter.OnItemClickListener() {
                    //这个是点击事件
                    @Override
                    public void onClick(List<WGoods> dataItems,int position) {
                     //   List<WGoods> dataItems=(List<WGoods>) recycler_view.getAdapter().;


                        //    WGoods g=(WGoods)dataItems.get(position);
                        System.out.println(((WGoods)dataItems.get(position)).getCode());
                                //recycler_view.getAdapter().getItemId(position);
                    }
                    //这个是长按事件
                    @Override
                    public void onLongClick(List<WGoods> dataItems,int position) {

                    }
                });





            }

            @Override
            public void onDataFailed() {
                Toast.makeText(RecyclerView.this, "加载失败！", Toast.LENGTH_SHORT).show();
            }
        });



       // recycler_view.setAdapter(adapter);

   /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });  */

    }

public void init(String data){




}

    public class GetGoods extends AsyncTask<String, Void, String> {
        //上面的方法中，第一个参数：网络图片的路径，第二个参数的包装类：进度的刻度，第三个参数：任务执行的返回结果

        String mUrl;
        OnDataFinishedListener onDataFinishedListener;

        public GetGoods(String url){
            this.mUrl = url;
        }

        public void setOnDataFinishedListener(OnDataFinishedListener onDataFinishedListener) {
            this.onDataFinishedListener = onDataFinishedListener;
        }





        @Override
        //在界面上显示进度条
        protected void onPreExecute() {
            // dialog.show() ;
        };
        protected String doInBackground(String... params) {  //三个点，代表可变参数
            // HttpClient httpClient = new DefaultHttpClient() ;
            //            HttpGet httpget = new HttpGet(params[0]) ;
            String datastr =null;
            try {

              datastr= CommonUtils.httpRequest(mUrl,"POST","currpage=1&pagesize=10&field=GoodsID,Code,GoodName,Imgpath&tbl=W_Goods&keyid=GoodsID");

               // net.sf.json.JSONObject json=    net.sf.json.JSONObject.fromObject(datastr);


             //  datastr =json.get("ls").toString();
             //   List<WGoods> l =  JSON.parseArray(json.get("ls").toString(),WGoods.class);


             //   return l;

            }  catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
          return   datastr;
        }
        //主要是更新UI
        @Override
        protected void onPostExecute( String result) {
            super.onPostExecute(result);
            //imageView.setImageBitmap(result) ;//更新UI
            //dialog.dismiss() ;

            System.out.println("拿到值："+result);
            //net.sf.json.JSONObject  json= net.sf.json.JSONObject.fromObject(result)  ;


            if(result!=null){
                onDataFinishedListener.onDataSuccessfully(result);
            }else{
                onDataFinishedListener.onDataFailed();
            }





        }
    }
    //  回调接口：
    public interface OnDataFinishedListener {

        public void onDataSuccessfully(String  data);

        public void onDataFailed();

    }









}

