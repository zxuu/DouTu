package com.zxu.picturesxiangce.avtivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.bean.Image;
import com.zxu.picturesxiangce.weight.CommonVideoView;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CommonVideoView mVideoView;
    private int REQUEST_CODE = 3;
    private String path;
    ImageView imageViewBg;
    private TextView userName;
    private TextView userTel;
    private TextView userPassWord;
    private TextView userGender;
    private TextView userDeclaration;

    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj.equals("ok")) {
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.user_name);
        userTel = findViewById(R.id.user_tel);
        userPassWord = findViewById(R.id.user_pass);
        userGender = findViewById(R.id.user_gender);
        userDeclaration = findViewById(R.id.user_declaration);

        mVideoView = (CommonVideoView) this.findViewById(R.id.videoView);
        imageViewBg =  findViewById(R.id.user_bg);
        imageViewBg.setOnClickListener(this);
        findViewById(R.id.register_user).setOnClickListener(this);

        playVideoView();
    }

    private void playVideoView()
    {
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login));
        //播放
//        mVideoView.start();
        //循环播放
//        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                mVideoView.start();
//            }
//        });
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        playVideoView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        mVideoView.stopPlayback();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_bg:
                ISListConfig config = new ISListConfig.Builder()
                        .multiSelect(false)
                        // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                        .rememberSelected(false)
                        // “确定”按钮背景色
                        .btnBgColor(Color.GRAY)
                        // “确定”按钮文字颜色
                        .btnTextColor(Color.BLUE)
                        // 使用沉浸式状态栏
                        .statusBarColor(Color.parseColor("#3F51B5"))
                        // 返回图标ResId
                        .backResId(android.support.v7.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
                        // 标题
                        .title("图片")
                        // 标题文字颜色
                        .titleColor(Color.WHITE)
                        // TitleBar背景色
                        .titleBgColor(Color.parseColor("#3F51B5"))
                        // 裁剪大小。needCrop为true的时候配置
                        .cropSize(1, 1, 200, 200)
                        .needCrop(false)
                        // 第一个是否显示相机，默认true
                        .needCamera(false)
                        // 最大选择图片数量，默认9
                        .maxNum(1)
                        .build();
                ISNav.getInstance().toListActivity(v.getContext(), config, REQUEST_CODE);
                break;
            case R.id.register_user:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        register();
                    }
                }).start();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            path = pathList.get(0);
            Glide.with(this)
                    .load(path)
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(imageViewBg){
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageViewBg.setImageDrawable(circularBitmapDrawable);
                        }
                    });

        }
    }
    private void register() {
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+MyContext.REGISTER);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody user_Name = null;
        try {
            user_Name = new StringBody(userName.getText().toString(), Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBody user_Tel = new StringBody(userTel.getText().toString(), ContentType.TEXT_PLAIN);
        StringBody user_PassW = new StringBody(userPassWord.getText().toString(), ContentType.TEXT_PLAIN);
        StringBody user_Gender = new StringBody(userGender.getText().toString(), ContentType.TEXT_PLAIN);
        StringBody user_Dec = null;
        try {
            user_Dec = new StringBody(userDeclaration.getText().toString(), Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        FileBody file = new FileBody(new File(path));
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("headPortrait", file)
                .addPart("user_Name", user_Name)
                .addPart("user_Tel", user_Tel)
                .addPart("user_PassW", user_PassW)
                .addPart("user_Gender", user_Gender)
                .addPart("user_Dec", user_Dec)
                .addPart("category", new StringBody("0", ContentType.TEXT_PLAIN))
                .build();

        httpPost.setEntity(reqEntity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                JSONObject jsonpObject = JSON.parseObject(EntityUtils.toString(resEntity));
                String result = (String) jsonpObject.get("result");
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
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
}
