package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.link.cloud.R;
import com.link.cloud.adapter.PublicTitleAdapter;
import com.link.cloud.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qianlu on 2018/10/22 17:50
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class EliminateClassActivity extends BaseActivity {

    private RecyclerView recycle;
    private FrameLayout contentFrame;


    @Override
    protected void initViews() {
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eliminateclass;
    }

    private void initView() {

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
}
