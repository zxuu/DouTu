package com.zxu.picturesxiangce.avtivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zxu.picturesxiangce.R;

import java.io.File;
import java.io.IOException;

public class TimeDetailActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail);

        Intent intent = getIntent();


        videoView = findViewById(R.id.time_detail_vv);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(intent.getStringExtra("videoUrl")));
        videoView.start();
    }

    @Override
    public void finish() {
        super.finish();
        videoView.stopPlayback();
    }
}
