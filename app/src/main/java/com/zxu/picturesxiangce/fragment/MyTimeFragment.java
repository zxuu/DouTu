package com.zxu.picturesxiangce.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.assionhonty.lib.assninegridview.AssNineGridView;
import com.assionhonty.lib.assninegridview.AssNineGridViewAdapter;
import com.assionhonty.lib.assninegridview.AssNineGridViewClickAdapter;
import com.assionhonty.lib.assninegridview.ImageInfo;
import com.wang.avi.AVLoadingIndicatorView;
import com.yalantis.phoenix.PullToRefreshView;
import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.bean.DemoBean;
import com.zxu.picturesxiangce.bean.Image;
import com.zxu.picturesxiangce.fragment.UserDetail.PhotoFragment;
import com.zxu.picturesxiangce.util.TimeGalleryUtill;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;


public class MyTimeFragment extends Fragment {
    private List<Image> imageList = new ArrayList<>();
    private List<DemoBean> mDatas = new ArrayList<>();
    RecyclerView mRv;
    AVLoadingIndicatorView avLoadingIndicatorView;
    PullToRefreshView mPullToRefreshView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    List<Image> list1 = (List<Image>) msg.obj;
                    mDatas = TimeGalleryUtill.getDifferTimes(list1);
                    mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    mRv.setAdapter(new MyAdapter());
                    avLoadingIndicatorView.hide();
                    break;
                case 3:
                    Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getContext(), "删除失败！", Toast.LENGTH_SHORT).show();
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
                getImages();
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_time, container, false);
        mRv = view.findViewById(R.id.rv_my_time);
        avLoadingIndicatorView = view.findViewById(R.id.loading_avi_my_time);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        avLoadingIndicatorView.show();
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getImages();
                    }
                }).start();
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 500);
            }
        });
        return view;
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_jiu_geng, parent, false);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

            holder.tv.setText(mDatas.get(position).getTimeTile());
            List<ImageInfo> imageInfos = getImageInfos(position);
            holder.angv.setAdapter(new AssNineGridViewClickAdapter(getContext(), imageInfos));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            delateMyTime(holder.tv.getText().toString());
                        }
                    }).start();
                }
            });

//            MyAssAdapter assAdapter = new MyAssAdapter(getContext(), imageInfos);
//            assAdapter.onImageItemClick(getContext(),  holder.angv, position, imageInfos);
//            holder.angv.setAdapter(assAdapter);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        private List<ImageInfo> getImageInfos(int position) {
            List<ImageInfo> imageInfos = new ArrayList<>();
            List<String> images = mDatas.get(position).getImages();
            for (String url : images) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl(url);
                imageInfo.setThumbnailUrl(url);
                imageInfos.add(imageInfo);
            }
            return imageInfos;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            AssNineGridView angv;
            TextView tv;
            View view;
            MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                angv = itemView.findViewById(R.id.angv);
                view = itemView.findViewById(R.id.item_my_time);
            }
        }

        private class MyAssAdapter extends AssNineGridViewAdapter {
            private Context mContext;
            MyAssAdapter(Context context, List<ImageInfo> imageInfo) {
                super(context, imageInfo);
                mContext = context;
            }

            @Override
            public void onImageItemClick(Context context, AssNineGridView angv, int index, List<ImageInfo> imageInfo) {
                super.onImageItemClick(context, angv, index, imageInfo);
                Toast.makeText(mContext, "条目"+index+":自定义点击效果", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getImages() {
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+MyContext.GETIMAGES);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody myName = null;
        try {
            myName = new StringBody(MyContext.USER, Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
                imageList = JSON.parseArray(jsonpObject.get("myImages").toString(),Image.class);
//                img_time_list = JSON.parseArray(jsonpObject.get("img_time_list").toString(),String.class);
//                List<List<String>> infoList = new ArrayList<>();
//                infoList.add(list);
//                infoList.add(img_time_list);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = imageList;
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

    private void delateMyTime(String timeTitle) {
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+MyContext.DELECTTIME);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody myName = null;
        try {
            myName = new StringBody(timeTitle, Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("idVideo", myName)
                .build();

        httpPost.setEntity(reqEntity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                JSONObject jsonpObject = JSON.parseObject(EntityUtils.toString(resEntity));
                String str = jsonpObject.get("result").toString();
                Message msg = new Message();
                msg.what = 3;
                msg.obj = str;
                mHandler.sendMessage(msg);
//                System.out.println("服务器正常返回的数据: " + EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据
//                System.out.println(resEntity.getContent());
            } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                Message msg = new Message();
                msg.what = 4;
                mHandler.sendMessage(msg);
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
