package com.zxu.picturesxiangce.avtivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zxu.picturesxiangce.Context;
import com.zxu.picturesxiangce.R;

import java.io.File;
import java.io.IOException;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class TimeDetailActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail);

        videoView = (VideoView) findViewById(R.id.time_detail_vv);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(Context.NGINXSERVER+"pada.mp4"));
        videoView.start();

    }

    @Override
    public void finish() {
        super.finish();
        videoView.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
