package com.zxu.picturesxiangce.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zxu.picturesxiangce.MyContext;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.avtivity.LoginActivity;

import java.io.IOException;

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


public class LoginDailogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private Button btn;
    private ImageView iv;
    private TextView toReg;


    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj.equals("ok")) {
                        Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "handleMessage: "+"ok");
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_login, null);
        iv= view.findViewById(R.id.login_iv);
        toReg= view.findViewById(R.id.login_register);
        mUsername= view.findViewById(R.id.login_et1);
        btn= view.findViewById(R.id.login_btn);
        mPassword= view.findViewById(R.id.login_et2);

        iv.setOnClickListener(this);
        toReg.setOnClickListener(this);
        btn.setOnClickListener(this);
        view.findViewById(R.id.login_register).setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sign();
                    }
                }).start();
                break;
            case R.id.login_register:
//                registerFragment = new RegisterFragment();
                startActivity(new Intent(v.getContext(),LoginActivity.class));
                break;


        }
    }

    private void sign() {
        HttpPost httpPost = new HttpPost(MyContext.DJANGOSERVER+MyContext.SIGN);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBody user_Name = new StringBody(mUsername.getText().toString(), ContentType.TEXT_PLAIN);
        StringBody user_Tel = new StringBody(mPassword.getText().toString(), ContentType.TEXT_PLAIN);

        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("user_Name", user_Name)
                .addPart("user_PassW", user_Tel)
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
