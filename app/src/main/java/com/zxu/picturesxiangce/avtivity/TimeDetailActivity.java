package com.zxu.picturesxiangce.avtivity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;

import java.net.URI;

public class TimeDetailActivity extends AppCompatActivity {
    VideoView videoView;
    String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail);


        videoView = (VideoView) findViewById(R.id.time_detail_vv);
        videoUrl = getIntent().getStringExtra("videoUrl");
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(videoUrl));
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
