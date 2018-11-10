package com.neusoft.heart.rate.activity;

import android.content.Intent;
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
import android.widget.Spinner;

public class Main2Activity extends AppCompatActivity implements OnItemClickListener {

    List<PhoneContact> mList;
    private AutoCompleteTextView mACTV;
    private Spinner spinner;
    private android.widget.EditText  search;

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
        search =(android.widget.EditText)findViewById(R.id.search);
        search.setFocusable(false);//让EditText失去焦点，然后获取点击事件
     //   search.setOnClickListener(this);
    search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // android.widget.Toast.makeText(MainActivity.this, "do click", android.widget.Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(Main2Activity.this, SpinnerActivity.class);
               //startActivity(intent);
               startActivityForResult(intent, 100);
            }
        });


        spinner =(Spinner) findViewById(R.id.spinner);
        spinner.setSelection(0,true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //Intent intent=new Intent(Main2Activity.this, SpinnerActivity.class);
             //   startActivity(intent);
           String text=  spinner.getItemAtPosition(i).toString();

           android.widget.Toast.makeText(Main2Activity.this,text,android.widget.Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
             //   Intent intent=new Intent(Main2Activity.this, SpinnerActivity.class);
              //  startActivity(intent);
            }
        });


    }
    @Override

protected void onActivityResult(int requestCode, int resultCode, Intent data) {

switch (requestCode) {

case 100:

if (resultCode == RESULT_OK) {

//String returnedData = data.getStringExtra("bundle");
Bundle  b= data.getExtras();
android.util.Log.i("变量值：",b.getString("name").toString());
search.setHint(b.getString("EmployeeID").toString());
 search.setText(b.getString("name").toString());
android.widget.Toast.makeText(Main2Activity.this,search.getText() , android.widget.Toast.LENGTH_LONG).show();

}

break;

default:

}

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


