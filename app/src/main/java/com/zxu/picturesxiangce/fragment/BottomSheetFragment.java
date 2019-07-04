package com.zxu.picturesxiangce.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.bean.Comment;

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

import static android.support.constraint.Constraints.TAG;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    RecyclerView mRecyclerView;
    EditText commentET;
    List<Comment> mCmmentList;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ListAdapter listAdapter = new ListAdapter();
                    mRecyclerView.setAdapter(listAdapter);
                    break;
                case 2:
                    if (msg.obj.equals("ok")) {
                        Toast.makeText(getContext(), "评论成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "评论失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NORMAL, R.style.MyDialogFragmentStyle);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getComments();
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);

        commentET = view.findViewById(R.id.speak_little);
        view.findViewById(R.id.comment_btn).setOnClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_btn:
                final String content = commentET.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        putComment(content);
                    }
                }).start();
                break;
        }
    }

    private final class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_bottom_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(mCmmentList.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return mCmmentList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textview);
            }
        }
    }

    private void getComments(){
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+MyContext.GETCOMMENTS);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody myName = new StringBody(MyContext.currentVideo, ContentType.TEXT_PLAIN);
        Log.i(TAG, "getComments: "+MyContext.currentVideo);
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("currentVideo", myName)
                .build();

        httpPost.setEntity(reqEntity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                JSONObject jsonpObject = JSON.parseObject(EntityUtils.toString(resEntity));
                mCmmentList = JSON.parseArray(jsonpObject.get("result").toString(), Comment.class);
                Message msg = new Message();
                msg.what = 1;
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
    private void putComment(String content){
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+MyContext.PUTCOMMENT);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody currentVideo = new StringBody(MyContext.currentVideo, ContentType.TEXT_PLAIN);
        HttpEntity reqEntity = null;
        try {
            reqEntity = MultipartEntityBuilder.create()
                    .addPart("currentVideo", currentVideo)
                    .addPart("content", new StringBody(content, Charset.forName("UTF-8")))
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(reqEntity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                JSONObject jsonpObject = JSON.parseObject(EntityUtils.toString(resEntity));
                String result = jsonpObject.get("result").toString();
                Message msg = new Message();
                msg.what = 2;
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
