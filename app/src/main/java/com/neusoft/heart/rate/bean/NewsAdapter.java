package com.neusoft.heart.rate.bean;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neusoft.heart.rate.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> dataItems = new ArrayList<>();

    public void setDataItems(List<News> dataItems) {
        this.dataItems = dataItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = dataItems.get(position);
        holder.tvContent.setText(news.content);
        holder.tvSource.setText(news.source);
        holder.tvTime.setText(news.time);
        Glide.with(holder.itemView.getContext()).load(news.imgUrl).into(holder.ivPic);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvContent;
        TextView tvSource;
        TextView tvTime;

        public ViewHolder(View view) {
            super(view);

            ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            tvContent =(TextView) view.findViewById(R.id.tv_content);
            tvSource = (TextView)view.findViewById(R.id.tv_source);
            tvTime = (TextView)view.findViewById(R.id.tv_time);
        }
    }





}
