package com.zxu.picturesxiangce.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.zxu.picturesxiangce.R;

public class VideoFirstFrameUtil {

//    Bitmap bitmap = null;
//    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//           try {
//
//        //根据网络视频的url获取第一帧--亲测可用。但是这个方法获取本地视频的第一帧，不可用，还没找到方法解决。
//        if (Build.VERSION.SDK_INT >= 14) {
//            retriever.setDataSource(videoUrl, new HashMap<String, String>());
//        } else {
//            retriever.setDataSource(videoUrl);
//        }
//        //获得第一帧图片
//        bitmap = retriever.getFrameAtTime();
//    } catch (IllegalArgumentException e) {
//        e.printStackTrace();
//    } finally {
//        retriever.release();
//    }
//        return bitmap;

    public  static Bitmap getVideoThumb(String path) {

        MediaMetadataRetriever media = new MediaMetadataRetriever();

        media.setDataSource(path);

        return  media.getFrameAtTime();

    }

}
