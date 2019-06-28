package com.zxu.picturesxiangce.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zxu.picturesxiangce.R;


public class MyCollectionFragment extends Fragment {
    RecyclerView sharePhotos_rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_collection, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        sharePhotos_rv = view.findViewById(R.id.share_photos_rv);
    }

}
