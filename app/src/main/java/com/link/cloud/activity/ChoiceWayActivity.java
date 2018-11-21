package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.link.cloud.R;
import com.link.cloud.base.BaseActivity;

/**
 * 作者：qianlu on 2018/10/24 19:20
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class ChoiceWayActivity  extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choiceway;
    }

    @Override
    public void modelMsg(int state, String msg) {

    }
}
