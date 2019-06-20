package com.zxu.picturesxiangce.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tencent.liteav.demo.videoediter.TCVideoEditChooseActivity;
import com.tencent.liteav.demo.videoediter.TCVideoEditerActivity;
import com.zxu.picturesxiangce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTimeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_time, container, false);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),TCVideoEditChooseActivity.class));
            }
        });

        return view;
    }



}
