package com.neusoft.heart.rate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.heart.rate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//AppCompatActivity
public class SpinnerActivity extends AppCompatActivity   {


  private  ListView searchls;
  private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

         android.support.v7.app.ActionBar actionBar = getSupportActionBar();
      //  android.app.ActionBar actionBar=  getActionBar();//Activity
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        searchls =(ListView)findViewById(R.id.searchls);
        List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
        Map<String,Object> m=new HashMap<String,Object>();
        m.put("EmployeeID","0AE");
        m.put("name","小陈");

        Map<String,Object> m1=new HashMap<String,Object>();
        m1.put("EmployeeID","0AB");
        m1.put("name","小李");
        ls.add(m);
        ls.add(m1);

        adapter=new SimpleAdapter(this,ls,R.layout.spinner_item,new String[]{"EmployeeID","name"},new int[]{R.id.EmployeeID,R.id.name});
        adapter.getFilter().filter("a");
        //adapter.notifyDataSetChanged();
        searchls.setAdapter(adapter);

       findView();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void findView(){
        searchls.setOnItemClickListener(new  android.widget.AdapterView.OnItemClickListener(){

            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                //我们需要的内容，跳转页面或显示详细信息

                //获得选中项的HashMap对象
                Map<String,String> map=(HashMap<String,String>)searchls.getItemAtPosition(position);
                String title=map.get("EmployeeID");
                String content=map.get("name");
                android.widget.Toast.makeText(getApplicationContext(),
                        "你选择了第"+position+"个Item，itemTitle的值是："+title+"itemContent的值是:"+content,
                        android.widget.Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
				bundle.putString("EmployeeID", title);
				bundle.putString("name",content);
				android.content.Intent intent = new android.content.Intent();
                intent.putExtras(bundle);
                 //intent.putExtra("result", result);
				//intent.putExtras("bundle",(bundle));
			    // intent.setClass(SpinnerActivity.this, Main2Activity.class);
				//android.util.Log.i("message", message[arg2]);
				//startActivity(intent);
            //   intent.putExtra("data_return", "Hello");

               setResult(RESULT_OK, intent);

              finish();



            }
        });

    }
    @Override
    public void onBackPressed() {
        android.content.Intent intent = new android.content.Intent();
        setResult(RESULT_OK, intent);

        finish();
    }
    android.content.Intent intent = new android.content.Intent();
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK, intent);
            finish();
            return true;// return false 或者return true 都不会走onBackPressed了
        }else
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return super.onKeyDown(keyCode, event);// 不拦截，如果这里拦截了，也不会走到onBackPressed方法了
        return false;
    }

}
