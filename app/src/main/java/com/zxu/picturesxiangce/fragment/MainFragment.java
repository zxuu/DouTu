package com.zxu.picturesxiangce.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.avtivity.VideoDetailActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);

        initListener();
        return view;
    }
    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler);

        mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
        mAdapter = new MyAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
        final VideoView videoView = itemView.findViewById(R.id.video_view);
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
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        //private int[] imgs = {R.mipmap.luoli,R.mipmap.luoli};
        private int[] videos = {R.raw.video_2,R.raw.video_11};
        public MyAdapter(){
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.img_thumb.setImageResource(imgs[position%2]);
            holder.video_detail_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(getContext(),VideoDetailActivity.class));
                }
            });
            holder.videoView.setVideoURI(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+ videos[position%2]));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView img_thumb;
            VideoView videoView;
            ImageView img_play;
            RelativeLayout rootView;
            TextView video_detail_tv;
            public ViewHolder(View itemView) {
                super(itemView);
                img_thumb = itemView.findViewById(R.id.img_thumb);
                videoView = itemView.findViewById(R.id.video_view);
                img_play = itemView.findViewById(R.id.img_play);
                rootView = itemView.findViewById(R.id.root_view);
                video_detail_tv = itemView.findViewById(R.id.video_detail_tv);
            }
        }
    }

}
