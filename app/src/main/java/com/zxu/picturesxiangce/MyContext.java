package com.zxu.picturesxiangce;

public class MyContext {
    public static final String USER = "zxu";
    public static final String IP = "http://10.0.116.20:";
    //服务类型
    public static final String DJANGOSERVER = IP+"8000/";
    public static final String NGINXSERVER = IP+"8080/";

    public static final String GETVIDEOS = "getVideos/";
    public static final String GETUSER = "getUser/";
    public static final String GETIMAGES = "getImages/";
    public static final String GETVIDEOIMAGES = "getVideoImages/";
    //获取视频对应的评论
    public static final String GETCOMMENTS = "getComments/";
    //获取关注的人
    public static final String GETFOLLOWS = "getFollows/";

    public static final String UPLOADIAMGE = "uploadImage/";
    public static final String UPLOADVIDEO = "uploadVideo/";
    public static final String UPLOADFILE = "uploadFile/";
    //评论
    public static final String PUTCOMMENT = "putComment/";
    //关注
    public static final String PUTFOLLOW = "putFollow/";
    //点赞
    public static final String DIANZAN = "putDianZan/";



    //注册
    public static final String REGISTER = "register/";
    //登录
    public static final String SIGN = "sign/";

    public static String currentVideo = "";

}
