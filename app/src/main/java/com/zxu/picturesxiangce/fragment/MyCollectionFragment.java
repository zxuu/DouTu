package com.zxu.picturesxiangce.fragment;


import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.adapter.FollowsAdapter;
import com.zxu.picturesxiangce.bean.User;
import com.zxu.picturesxiangce.bean.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;


public class MyCollectionFragment extends Fragment {
    RecyclerView sharePhotos_rv;
    List<User> userslist = new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    FollowsAdapter followsAdapter = new FollowsAdapter(userslist);
                    sharePhotos_rv.setAdapter(followsAdapter);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                get();
            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_collection, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        sharePhotos_rv = view.findViewById(R.id.follow_photos_rv);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sharePhotos_rv.setLayoutManager(layoutManager);

    }

    private void get() {
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+ MyContext.GETFOLLOWS);
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
                userslist = JSON.parseArray(jsonpObject.get("meFollows").toString(),User.class);
                Message msg = new Message();
                msg.what = 1;
//                msg.obj = list;
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
