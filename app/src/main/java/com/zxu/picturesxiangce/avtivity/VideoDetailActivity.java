package com.zxu.picturesxiangce.avtivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.bean.User;
import com.zxu.picturesxiangce.fragment.UserDetail.PhotoFragment;
import com.zxu.picturesxiangce.fragment.UserDetail.videoFragment;
import com.zxu.picturesxiangce.util.ViewFindUtils;

import java.util.ArrayList;

public class VideoDetailActivity extends AppCompatActivity implements OnTabSelectListener {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "相册","视频相册"
    };
    private MyPagerAdapter mAdapter;
    private ImageView touXing_iv;
    private ImageView user_bg_iv;
    private TextView userName;
    private TextView userDeclaration;
    User me;

    private String name;
    private String tel;
    private String gender;
    private String declaration;
    private String back_img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
//        tel = intent.getStringExtra("tel");
//        gender = intent.getStringExtra("gender");
//        declaration = intent.getStringExtra("declaration");
//        back_img_url = intent.getStringExtra("back_img_url");

        me = (User) intent.getSerializableExtra("user");
        initView();

        findViewById(R.id.guanzhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideoDetailActivity.this,Test.class));
            }
        });

        PhotoFragment photoFragment = PhotoFragment.newInstance(me.getName());
        mFragments.add(photoFragment);
        mFragments.add(new videoFragment());

        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        /** indicator圆角色块 */
        SlidingTabLayout tabLayout_10 = ViewFindUtils.find(decorView, R.id.tl_10);
        tabLayout_10.setViewPager(vp);
        vp.setCurrentItem(0);
    }

    private void initView(){
        touXing_iv =  findViewById(R.id.touxiang);
        userName = findViewById(R.id.user_name_vd_tv);
        userDeclaration = findViewById(R.id.user_declaration_tv);
        user_bg_iv = (ImageView) findViewById(R.id.user_back_g_image_view);
        if (null != me) {
            initUserInfo();
        } else {
            Toast.makeText(mContext, "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void initUserInfo(){
        Glide.with(this)
                .load(me.getBack_img_url())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(touXing_iv){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        touXing_iv.setImageDrawable(circularBitmapDrawable);
                    }
                });

        Glide.with(this)
                .load(me.getBack_img_url())
                .into(user_bg_iv);
        userName.setText(me.getName());
        userDeclaration.setText(me.getDeclaration());
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
