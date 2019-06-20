package com.zxu.picturesxiangce.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxu.picturesxiangce.R;
import com.zxu.picturesxiangce.adapter.TimeViewAdapter;
import com.zxu.picturesxiangce.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTimeFragment extends Fragment {
    private RecyclerView Rv;
    private ArrayList<HashMap<String,Object>> listItem;
    private TimeViewAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_time, container, false);

        // 初始化显示的数据
        initData();

        // 绑定数据到RecyclerView
        initView(view);

        return view;
    }


    // 初始化显示的数据
    public void initData(){
        listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
        int[] videos = {R.raw.video_2,R.raw.video_11};
        for (int i = 0; i <20; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String s = "android.resource://"+getContext().getPackageName()+"/"+ String.valueOf(videos[0]);
            map.put("videoTitle","2019-6-20");
            map.put("videoUrl",s);
            listItem.add(map);
        }
    }

    // 绑定数据到RecyclerView
    public void initView(View view){
        Rv = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        //使用线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        Rv.setLayoutManager(layoutManager);
        Rv.setHasFixedSize(true);

        //用自定义分割线类设置分割线
        Rv.addItemDecoration(new DividerItemDecoration(getContext()));

        //为ListView绑定适配器
        myAdapter = new TimeViewAdapter(getContext(),listItem);
        Rv.setAdapter(myAdapter);
    }

}
