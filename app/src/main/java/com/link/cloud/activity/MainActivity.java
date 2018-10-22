package com.link.cloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.base.BaseActivity;
import com.zitech.framework.utils.ViewUtils;

public class MainActivity extends BaseActivity {


    private FrameLayout classBeginsLayout;
    private FrameLayout classeLiminateLayout;
    private TextView addFaceButton;
    private TextView addFingerButton;

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
        return R.layout.activity_main;
    }

    private void initView() {

        classBeginsLayout = (FrameLayout) findViewById(R.id.classBeginsLayout);
        classeLiminateLayout = (FrameLayout) findViewById(R.id.classeLiminateLayout);
        addFaceButton = (TextView) findViewById(R.id.addFaceButton);
        addFingerButton = (TextView) findViewById(R.id.addFingerButton);

        ViewUtils.setOnClickListener(addFaceButton,this);

        classeLiminateLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.addFaceButton:
                showActivity(BindActivity.class);
                break;

            case R.id.classeLiminateLayout:
                showActivity(EliminateClassActivity.class);
                break;
        }
    }
}
