package com.zxu.picturesxiangce.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.avtivity.TimeDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class TimeViewAdapter extends RecyclerView.Adapter {
    private ArrayList<HashMap<String, Object>> listItem;

    //构造函数，传入数据
    public TimeViewAdapter(ArrayList<HashMap<String, Object>> listItem) {
        this.listItem = listItem;
    }


    //定义Viewholder
    class Viewholder extends RecyclerView.ViewHolder  {
        private TextView Title;
        private ImageView videoView;

        public Viewholder(View root) {
            super(root);
            Title = (TextView) root.findViewById(R.id.Itemtitle);
            videoView = root.findViewById(R.id.time_list_view_video);
        }
        public TextView getTitle() {
            return Title;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_list_cell, parent, false);
        Viewholder viewholder = new Viewholder(view);

        return viewholder;
    }//在这里把ViewHolder绑定Item的布局

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Viewholder vh = (Viewholder) holder;
        // 绑定数据到ViewHolder里面
        vh.Title.setText((String) listItem.get(position).get("videoTitle"));
        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        vh.videoView.setImageResource(R.mipmap.time);
        vh.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimeDetailActivity.class);
                intent.putExtra("videoUrl", (String) listItem.get(position).get("videoUrl"));
                v.getContext().startActivity(intent);
            }
        });

    }

    //返回Item数目
    @Override
    public int getItemCount() {
        return listItem.size();
    }


}
