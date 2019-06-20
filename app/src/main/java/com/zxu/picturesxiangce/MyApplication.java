package com.zxu.picturesxiangce;

import android.app.Application;
import android.util.Log;

import com.tencent.ugc.TXUGCBase;

public class MyApplication extends Application {

    String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/fb69d1e9e0d01838daad0825eb255caf/TXUgcSDK.licence";
    String ugcKey = "809ecccc3c50466012be96e95a5896cb";

    @Override
    public void onCreate() {
        super.onCreate();

        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);

        String string = TXUGCBase.getInstance().getLicenceInfo(this);
        Log.i("SDK", "string=" + string);

    }
}
