package com.zxu.picturesxiangce.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zxu.picturesxiangce.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.tencent.liteav.demo.videorecord.view.TCAudioControl.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_collection, container, false);

        return view;
    }
}
