package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.link.cloud.R;
import com.link.cloud.adapter.PublicTitleAdapter;
import com.link.cloud.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class BindActivity extends BaseActivity {


    private RecyclerView recycle;
    private FrameLayout contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind;
    }

    private void initView() {

        recycle = (RecyclerView) findViewById(R.id.recycle);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle.setLayoutManager(layoutManager);
        PublicTitleAdapter publicTitleAdapter = new PublicTitleAdapter(this);
        String[] bindArray = getResources().getStringArray(R.array.bind_Array);
        List<String> date = new ArrayList<>();

        for (String dateInfo : bindArray) {
            date.add(dateInfo);
        }
        publicTitleAdapter.setDate(date);
        recycle.setAdapter(publicTitleAdapter);

    }

}
