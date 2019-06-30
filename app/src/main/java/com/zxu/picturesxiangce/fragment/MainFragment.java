package com.zxu.picturesxiangce.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.qintong.library.InsLoadingView;
import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.adapter.MainAdapter;
import com.zxu.picturesxiangce.avtivity.PhotosGalleryActivity;
import com.zxu.picturesxiangce.avtivity.VideoDetailActivity;
import com.zxu.picturesxiangce.bean.User;
import com.zxu.picturesxiangce.bean.Video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    User me = new User();

    List<Video> list = new ArrayList<>();
    List<User> userslist = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    List<Video> videoList = (List<Video>) msg.obj;
                    mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
                    mAdapter = new MyAdapter(videoList);
//                    Toast.makeText(getContext(), me.getName(), Toast.LENGTH_SHORT).show();
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                    initListener();
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getVideos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);

    }

    private void initListener(){
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onPageRelease(boolean isNext,int position) {
                Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                Log.e(TAG,"选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                playVideo(0);
            }

            @Override
            public void onLayoutComplete() {
                playVideo(0);
            }

        });
    }

    private void playVideo(int position) {
        View itemView = mRecyclerView.getChildAt(0);
        final VideoView videoView = itemView.findViewById(R.id.video_view_v);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final RelativeLayout rootView = itemView.findViewById(R.id.root_view);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                Log.e(TAG,"onInfo");
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e(TAG,"onPrepared");

            }
        });


        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    Log.e(TAG,"isPlaying:"+videoView.isPlaying());
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                }else {
                    Log.e(TAG,"isPlaying:"+videoView.isPlaying());
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
    }

    private void releaseVideo(int index){
        View itemView = mRecyclerView.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view_v);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        //private int[] imgs = {R.mipmap.luoli,R.mipmap.luoli};
        private List<Video> mVideoList;
        public MyAdapter(List<Video> videoUrlList){
            this.mVideoList = videoUrlList;
//            this.me = me;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            for (int i = 0; i < userslist.size(); i++) {
                if (mVideoList.get(position).getUser_name().equals(userslist.get(i).getName())) {
                    User realUserr = userslist.get(i);
                    holder.declaration_tv.setText(realUserr.getDeclaration());
                    Glide.with(getContext())
                            .load(realUserr.getBack_img_url())
                            .asBitmap()
                            .centerCrop()
                            .into(new BitmapImageViewTarget(holder.loadingView){
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    holder.loadingView.setImageDrawable(circularBitmapDrawable);
                                }
                            });

                    break;
                }
            }

            holder.loadingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < userslist.size(); i++) {
                        if (mVideoList.get(position).getUser_name().equals(userslist.get(i).getName())) {
                            Intent intent = new Intent(getContext(),VideoDetailActivity.class);
//                            intent.putExtra("name", userslist.get(i).getName());
//                            intent.putExtra("tel", userslist.get(i).getTel());
//                            intent.putExtra("gender", userslist.get(i).getGender());
//                            intent.putExtra("declaration", userslist.get(i).getDeclaration());
//                            intent.putExtra("back_img_url", userslist.get(i).getBack_img_url());
                            intent.putExtra("user", userslist.get(i));
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });
            holder.video_detail_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),PhotosGalleryActivity.class);
                    intent.putExtra("videoId", mVideoList.get(position).getId_video());
                    v.getContext().startActivity(intent);
                }
            });

            holder.comment_num_tv.setText(mVideoList.get(position).getHeart_num());
            holder.comment_num_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomSheetDialog();
                }
            });
            holder.heart_num_tv.setText(mVideoList.get(position).getHeart_num());
            holder.likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });

            holder.user_name_tv.setText(mVideoList.get(position).getUser_name());

//            holder.videoView.setVideoURI(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+ videos[position%2]));
            holder.videoView.setVideoURI(Uri.parse(mVideoList.get(position%mVideoList.size()).getUrl_video()));
        }

        @Override
        public int getItemCount() {
            return mVideoList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView img_thumb;
            VideoView videoView;
            ImageView img_play;
            RelativeLayout rootView;
            TextView video_detail_tv;
            TextView comment_num_tv;
            TextView heart_num_tv;
            LikeButton likeButton;
            TextView declaration_tv;
            InsLoadingView loadingView;
            TextView user_name_tv;
            Button guan_zhu_btn;
            public ViewHolder(View itemView) {
                super(itemView);
                img_thumb = itemView.findViewById(R.id.img_thumb);
                videoView = itemView.findViewById(R.id.video_view_v);
                img_play = itemView.findViewById(R.id.img_play);
                rootView = itemView.findViewById(R.id.root_view);
                video_detail_tv = itemView.findViewById(R.id.video_detail_tv);
                comment_num_tv = itemView.findViewById(R.id.comment_num);
                heart_num_tv = itemView.findViewById(R.id.heart_num);
                likeButton = itemView.findViewById(R.id.heart_button);
                loadingView = itemView.findViewById(R.id.loading_view);
                user_name_tv = itemView.findViewById(R.id.user_name);
                guan_zhu_btn = itemView.findViewById(R.id.guan_zhu_btn);
                declaration_tv = itemView.findViewById(R.id.declaration_tv);
            }
        }
    }

    private void getVideos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                get();
            }
        }).start();
    }

    private void get() {
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+ MyContext.GETVIDEOS);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody myName = new StringBody("zxu", ContentType.TEXT_PLAIN);
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("myName", myName)
                .build();
        httpPost.setEntity(reqEntity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                JSONObject jsonpObject = JSON.parseObject(EntityUtils.toString(resEntity));
                list = JSON.parseArray(jsonpObject.get("result").toString(),Video.class);
                me = JSON.parseObject(jsonpObject.get("me").toString(), User.class);
                userslist = JSON.parseArray(jsonpObject.get("users").toString(),User.class);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = list;
                mHandler.sendMessage(msg);
//                System.out.println("服务器正常返回的数据: " + EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据
//                System.out.println(resEntity.getContent());
            } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
//                Toast.makeText(this, "上传文件发生异常，请检查服务端异常问题", Toast.LENGTH_SHORT).show();
            }
            EntityUtils.consume(resEntity);
            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBottomSheetDialog() {
        BottomSheetFragment fragment = BottomSheetFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager,BottomSheetFragment.class.getSimpleName());
    }


}
