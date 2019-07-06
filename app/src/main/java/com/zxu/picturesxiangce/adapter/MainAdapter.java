package com.zxu.picturesxiangce.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.qintong.library.InsLoadingView;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.avtivity.VideoDetailActivity;
import com.zxu.picturesxiangce.bean.Video;
import com.zxu.picturesxiangce.fragment.BottomSheetFragment;
import com.zxu.picturesxiangce.fragment.MainFragment;

import java.util.List;

import static com.tencent.liteav.demo.common.utils.VideoUtil.getContext;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private List<Video> mVideoList;
    public MainAdapter(List<Video> videoUrlList, Context context){
    }
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
