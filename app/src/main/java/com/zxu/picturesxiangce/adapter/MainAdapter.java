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
    private Context context;

    public MainAdapter(List<Video> videoUrlList, Context context){
        this.mVideoList = videoUrlList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager,parent,false);
        final InsLoadingView imageView = view.findViewById(R.id.loading_view);
        Glide.with(parent.getContext())
                .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1104063590,3714887348&fm=26&gp=0.jpg")
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(parent.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.video_detail_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(getContext(), VideoDetailActivity.class));
            }
        });
        holder.comment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(v);
            }
        });

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

//            holder.videoView.setVideoURI(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+ videos[position%2]));
        holder.videoView.setVideoURI(Uri.parse(mVideoList.get(position%mVideoList.size()).getUrl_video()));
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_thumb;
        VideoView videoView;
        ImageView img_play;
        RelativeLayout rootView;
        TextView video_detail_tv;
        TextView comment_tv;
        TextView declaration_tv;
        LikeButton likeButton;
        InsLoadingView loadingView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            videoView = itemView.findViewById(R.id.video_view_v);
            img_play = itemView.findViewById(R.id.img_play);
            rootView = itemView.findViewById(R.id.root_view);
            video_detail_tv = itemView.findViewById(R.id.video_detail_tv);
            comment_tv = itemView.findViewById(R.id.comment_num);
            likeButton = itemView.findViewById(R.id.heart_button);
            loadingView = itemView.findViewById(R.id.loading_view);
            declaration_tv = itemView.findViewById(R.id.declaration_tv);
        }
    }

    private void showBottomSheetDialog(View view) {
        BottomSheetFragment fragment = BottomSheetFragment.newInstance();
//        FragmentManager fragmentManager = (Fragment)context.getFragmentManager();
//        fragment.show(fragmentManager,BottomSheetFragment.class.getSimpleName());
    }
}
