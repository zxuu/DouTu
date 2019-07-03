package com.zxu.picturesxiangce.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.adapter.VideoContentAdapter;
import com.zxu.picturesxiangce.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    private RecyclerView videoContentRv;
    private ImageView bg_img;
    private ImageView bg_tou_img;
    private TextView myName;
    private TextView myDeclaration;
    private LoginDailogFragment loginDailogFragment;
    private RegisterFragment registerFragment = new RegisterFragment();
    User me = new User();
    List<String> mVideoUrlList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        me.setName("zxu");
        me.setTel("18255713582");
        me.setGender("male");
        me.setDeclaration("时光清浅，岁月留香");
        me.setBack_img_url("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1104063590,3714887348&fm=26&gp=0.jpg");

        mVideoUrlList.add("http://192.168.0.19:8080/TXVideo_20190629_142906.mp4");
        mVideoUrlList.add("http://192.168.0.19:8080/TXVideo_20190701_092500.mp4");
        mVideoUrlList.add("http://192.168.0.19:8080/TXVideo_20190701_144424.mp4");

        initView(view);
        initVideoContent();
        return view;
    }

    private void initView(View view) {
        videoContentRv = view.findViewById(R.id.my_video_content_rv);
        bg_img = view.findViewById(R.id.me_bg_iv);
        bg_tou_img = view.findViewById(R.id.me_touxiang);
        myName = view.findViewById(R.id.me_name_vd_tv);
        myDeclaration = view.findViewById(R.id.me_declaration_tv);
        view.findViewById(R.id.login_btn).setOnClickListener(this);

        Glide.with(this)
                .load(me.getBack_img_url())
                .into(bg_img);
        Glide.with(this)
                .load(me.getBack_img_url())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(bg_tou_img){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        bg_tou_img.setImageDrawable(circularBitmapDrawable);
                    }
                });
        myName.setText(me.getName());
        myDeclaration.setText(me.getDeclaration());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.my_like_view:
//
//                break;
            case R.id.my_info_view:

                break;
            case R.id.login_btn:
                loginDailogFragment = new LoginDailogFragment();
                loginDailogFragment.show(getFragmentManager(),"login");
                break;
        }
    }

    private void initVideoContent(){
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        videoContentRv.setLayoutManager(layoutManager);
        VideoContentAdapter collocationAdapter = new VideoContentAdapter(mVideoUrlList);
        videoContentRv.setAdapter(collocationAdapter);
    }
}
