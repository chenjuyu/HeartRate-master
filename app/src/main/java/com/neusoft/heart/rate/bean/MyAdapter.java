package com.neusoft.heart.rate.bean;

import com.neusoft.heart.rate.R;

import java.util.List;

public class MyAdapter extends MyBaseAdapter<Student> {

    public MyAdapter(List<Student> data) {
        super(data);
    }

    @Override
    public void setData(ViewHolder holder, Student t) {
        holder.setText(R.id.mTv1, t.getName()).setText(R.id.mTv2, t.getSex());

    }

}
