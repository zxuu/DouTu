package com.zxu.picturesxiangce;

import android.app.Application;
import com.assionhonty.lib.assninegridview.AssNineGridView;
import com.tencent.ugc.TXUGCBase;
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

    }
}
