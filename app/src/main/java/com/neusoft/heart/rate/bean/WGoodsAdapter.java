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

public class WGoodsAdapter extends RecyclerView.Adapter<WGoodsAdapter.ViewHolder> {



    private List<WGoods> dataItems = new ArrayList<>();
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDataItems(List<WGoods> dataItems) {
        this.dataItems = dataItems;
        notifyDataSetChanged();
    }




    @Override
    public WGoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        WGoodsAdapter.ViewHolder holder = new WGoodsAdapter.ViewHolder(view);
        return holder;
    }
//接着在onBindViewHolder方法里面判断一下不为空就实现点击事件
    @Override
    public void onBindViewHolder(WGoodsAdapter.ViewHolder holder, final int position) {
        WGoods g = dataItems.get(position);
        holder.tvContent.setText(g.Code);
        holder.tvSource.setText(g.GoodsID);
        holder.tvTime.setText(g.GoodName);
        Glide.with(holder.itemView.getContext()).load(g.Imgpath).into(holder.ivPic);

        if (onItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(dataItems,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(dataItems,position);
                    return false;
                }
            });


        }



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


   // 然后写一个接口里面实现两个方法一个点击，一个长按


    public interface OnItemClickListener{
        void onClick( List<WGoods> dataItems,int position);
        void onLongClick(List<WGoods> dataItems, int position);
    }


}
