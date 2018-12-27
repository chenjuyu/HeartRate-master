package com.neusoft.heart.rate.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neusoft.heart.rate.R;
import com.neusoft.heart.rate.bean.News;
import com.neusoft.heart.rate.bean.NewsAdapter;

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


        List<News> dataItems = new ArrayList<>();

        for (int i=0;i<=10;i++)//添加数据
        {
            News news =new News("https://weixin.fxsoft88.com/WeeSee_images/goodsimg/2018-03-19-15-113363.jpg","这是一条新闻"+String.valueOf(i),"伏羲软件","2018-12-27","www.fuxi.com");

            dataItems.add(news);


        }




        recycler_view =(android.support.v7.widget.RecyclerView)findViewById(R.id.recycler_view);
      //  recycler_view.getLayoutManager();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter  adapter =new NewsAdapter();
        adapter.setDataItems(dataItems);

        recycler_view.setAdapter(adapter);

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

}
