package com.example.gsh.zhbj2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gsh on 2017/5/18.
 */
public class NewsViewHolder extends RecyclerView.ViewHolder {
    ImageView imgView;
    TextView titleView;
    View view;


    public NewsViewHolder(View itemView) {
        super(itemView);
        view =itemView;
        imgView = (ImageView) itemView.findViewById(R.id.item_img);
        titleView = (TextView) itemView.findViewById(R.id.item_title);
    }
}
