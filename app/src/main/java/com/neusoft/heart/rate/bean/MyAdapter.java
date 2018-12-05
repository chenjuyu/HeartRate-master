package com.neusoft.heart.rate.bean;

import com.neusoft.heart.rate.R;

import java.util.List;
//Student
public class MyAdapter extends MyBaseAdapter<Employee> {

    public MyAdapter(List<Employee> data) {
        super(data);
    }

    @Override  //Student t
    public void setData(ViewHolder holder, Employee t) {
        holder.setText(R.id.EmployeeID,t.getEmployeeID()).setText(R.id.mTv1,t.getCode()).setText(R.id.mTv2,t.getName() );//t.getSex()

    }

}
