package com.zxu.picturesxiangce.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxu.picturesxiangce.R;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> imgUrlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imgUrlList = getIntent().getStringArrayListExtra("imagesUrl");
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainAdapter mainAdapter = new MainAdapter(this,imgUrlList);
        recyclerView.setAdapter(mainAdapter);
    }
}
