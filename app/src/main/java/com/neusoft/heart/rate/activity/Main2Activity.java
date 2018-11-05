package com.neusoft.heart.rate.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.neusoft.heart.rate.R;
import com.neusoft.heart.rate.bean.PhoneAdapter;
import com.neusoft.heart.rate.bean.PhoneContact;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;

public class Main2Activity extends AppCompatActivity implements OnItemClickListener {

    List<PhoneContact> mList;
    private AutoCompleteTextView mACTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buildAppData();
        findView();
    }

    private void buildAppData() {
        String[] names = {"abc", "allen", "bird", "bike", "book", "cray",
                "david", "demon", "eclipse", "felling", "frank", "google",
                "green", "hill", "hook", "jin zhiwen", "jack", "jay", "king", "kevin", "kobe",
                "lily", "lucy", "mike", "nike", "nail", "open", "open cv",
                "panda", "pp", "queue", "ray allen", "risk", "tim cook", "T-MAC", "tony allen",
                "x man", "x phone", "yy", "world", "w3c", "zoom", "zhu ziqing"};

        mList = new ArrayList<PhoneContact>();

        for (int i = 0; i < names.length; i++) {
            PhoneContact pc = new PhoneContact(100 + i, names[i], "1861234567"
                    + i, names[i].concat("@gmail.com"));
            mList.add(pc);
        }

    }

    private void findView() {
        mACTV = (AutoCompleteTextView) findViewById(R.id.mACTV);
        PhoneAdapter mAdapter = new PhoneAdapter(mList, getApplicationContext());
        mACTV.setAdapter(mAdapter);
        mACTV.setThreshold(1);    //设置输入一个字符 提示，默认为2

        mACTV.setOnItemClickListener(this);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }  */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        PhoneContact pc = mList.get(position);
        mACTV.setText(pc.getName() + " " + pc.getPhone());
    

        //  ---------------------
        //      作者：Ricky_Fung
        // 来源：CSDN
        //  原文：https://blog.csdn.net/top_code/article/details/9326129
        //    版权声明：本文为博主原创文章，转载请附上博文链接！

    }
}


