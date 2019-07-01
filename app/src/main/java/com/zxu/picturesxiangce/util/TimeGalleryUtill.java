package com.zxu.picturesxiangce.util;

import android.util.Log;

import com.zxu.picturesxiangce.bean.DemoBean;
import com.zxu.picturesxiangce.bean.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class TimeGalleryUtill {
    public static List<DemoBean> getDifferTimes(List<Image> imageList){
        MyHashMap<String> myHashMap = new MyHashMap<>();
        List<DemoBean> demoBeans = new ArrayList<>();
        for (Image image : imageList) {
            myHashMap.put(image.getTarget_video(), image.getUrl_image());
        }
        for (HashMap.Entry<String, String> arg : myHashMap.entrySet()){
            String[] strings = arg.getValue().split(";");
            List<String> kk = new ArrayList<>();
            for (int i = 0; i < strings.length; i++) {
                kk.add(strings[i]);
            }
            DemoBean demoBean = new DemoBean();

            demoBean.setImages(kk);
            demoBean.setTimeTile(arg.getKey());

            demoBeans.add(demoBean);
            Log.i(TAG, "getDifferTimes: " + arg.getKey() + arg.getValue());
        }
//        for (String s : myHashMap.values()) {
//            String[] strings = s.split(";");
//            List<String> kk = new ArrayList<>();
//            for (int i = 0; i < strings.length; i++) {
//                kk.add(strings[i]);
//            }
//            DemoBean demoBean = new DemoBean();
//            demoBean.setImages(kk);
//            demoBeans.add(demoBean);
//            Log.i(TAG, "getDifferTimes: "+s);
//        }
        return demoBeans;
    }
}
