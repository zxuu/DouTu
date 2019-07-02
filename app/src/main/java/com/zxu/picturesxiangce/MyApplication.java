package com.zxu.picturesxiangce;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.assionhonty.lib.assninegridview.AssNineGridView;
import com.bumptech.glide.Glide;
import com.tencent.ugc.TXUGCBase;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.zxu.picturesxiangce.weight.GlideImageLoader;

public class MyApplication extends Application {

    String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/c269b15d190499eeddf2f23e690199e0/TXUgcSDK.licence";
    String ugcKey = "1f552fb4c6e2b59e6f4731a325222c85";

//    String ugcLicenceUrl = "http://download-1252463788.cossh.myqcloud.com/xiaoshipin/licence_android/TXUgcSDK.licence";
//    String ugcKey = "731ebcab46ecc59ab1571a6a837ddfb6";
    @Override
    public void onCreate() {
        super.onCreate();

        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);

        AssNineGridView.setImageLoader(new GlideImageLoader());

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });

    }
}
