package com.zxu.picturesxiangce.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.next.easynavigation.view.CustomViewPager;
import com.zxu.picturesxiangce.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/07 11:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MainStarViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewpager)
        public CustomViewPagerr mViewPager;
        @BindView(R.id.tv_star_desc)
        public TextView tvStarDesc;
        @BindView(R.id.view_left)
        public View viewLeft;
        @BindView(R.id.view_right)
        public View viewRight;

        public MainStarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


}
