package com.zxu.picturesxiangce.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.avtivity.VideoDetailActivity;
import com.zxu.picturesxiangce.bean.User;

import java.util.ArrayList;
import java.util.List;

public class FollowsAdapter extends RecyclerView.Adapter<FollowsAdapter.ViewHolder> {

    List<User> mUserList = new ArrayList<>();
    public FollowsAdapter(List<User> users) {
        this.mUserList = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_follows, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final User user = mUserList.get(position);
        Glide.with(viewHolder.itemView.getContext())
                .load(user.getBack_img_url())
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VideoDetailActivity.class);
                intent.putExtra("user", user);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
//        ImageView touxiang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_follows);
//            touxiang = itemView.findViewById(R.id.target_user_head_portrait);
        }
    }
}
