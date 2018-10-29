package com.neusoft.heart.rate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.neusoft.heart.rate.bean.EchartsDataBean;
import com.neusoft.heart.rate.R;
public class Test extends AppCompatActivity implements View.OnClickListener{

    private Button barchart_bt;
    private WebView chartshow_wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_test);

        initView();
    }
    /**
     * 初始化页面元素
     */
    private void initView(){
        barchart_bt=(Button)findViewById(R.id.barchart_bt);
        barchart_bt.setOnClickListener(this);
        chartshow_wb=(WebView)findViewById(R.id.chartshow_wb);
        //进行webwiev的一堆设置
        //开启本地文件读取（默认为true，不设置也可以）
        chartshow_wb.getSettings().setAllowFileAccess(true);
        //开启脚本支持
        chartshow_wb.getSettings().setJavaScriptEnabled(true);
        chartshow_wb.loadUrl("file:///android_asset/echart/myechart.html");
      //  chartshow_wb.loadUrl(String.valueOf(Test.class.getClassLoader().getResourceAsStream("assets/echart/myechart.html")));
      //  "file:///android_assets/echart/myechart.html");
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.barchart_bt:
               // chartshow_wb.loadUrl("javascript:createBarLineChart();"); EchartsDataBean.getInstance().getEchartsLineJson()
               // chartshow_wb.loadUrl("javascript:createChart('line'," + e.getEchartsLineJson() + ");");
                //chartshow_wb.loadUrl("javascript:createChart('line'," + EchartsDataBean.getInstance().getEchartsLineJson() + ");");
                chartshow_wb.loadUrl("javascript:createBarLineChart()");
                break;
        }
    }



}
