package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.link.cloud.Constants;
import com.link.cloud.R;
import com.link.cloud.adapter.PublicTitleAdapter;
import com.link.cloud.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qianlu on 2018/10/24 19:32
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class AttendClassActivity extends BaseActivity {

    private RecyclerView recycle;
    private FrameLayout contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        recycle = (RecyclerView) findViewById(R.id.recycle);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle.setLayoutManager(layoutManager);
        PublicTitleAdapter publicTitleAdapter = new PublicTitleAdapter(this);
        String[] bindArray = getResources().getStringArray(R.array.Eliminate_Array);
        List<String> date = new ArrayList<>();

        for (String dateInfo : bindArray) {
            date.add(dateInfo);
        }
        publicTitleAdapter.setDate(date);
        recycle.setAdapter(publicTitleAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attendclass;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
