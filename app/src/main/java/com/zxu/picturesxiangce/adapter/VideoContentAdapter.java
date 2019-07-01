package com.zxu.picturesxiangce.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.avtivity.TimeDetailActivity;

import java.util.List;

public class VideoContentAdapter extends RecyclerView.Adapter<VideoContentAdapter.ViewHolder> {
    List<String> mVideoContentList;

    public VideoContentAdapter(List<String> videourlList) {
        this.mVideoContentList = videourlList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_content, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.videoView.setVideoURI(Uri.parse(mVideoContentList.get(i)));
        viewHolder.videoView.start();
        viewHolder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TimeDetailActivity.class);
                intent.putExtra("videoUrl", mVideoContentList.get(i));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoContentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        public ViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_content_vv);

        }
    }
}
