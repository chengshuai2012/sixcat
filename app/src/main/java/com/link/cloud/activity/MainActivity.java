package com.link.cloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.link.cloud.Constants;
import com.link.cloud.R;
import com.link.cloud.base.BaseActivity;
import com.zitech.framework.utils.ViewUtils;

@SuppressLint("Registered")
public class MainActivity extends BaseActivity {


    private FrameLayout classBeginsLayout;
    private FrameLayout classeLiminateLayout;
    private TextView addFaceButton;
    private TextView addFingerButton;
    private FrameLayout signInLayout;

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
        signInLayout = (FrameLayout) findViewById(R.id.signInLayout);

        ViewUtils.setOnClickListener(addFaceButton, this);
        ViewUtils.setOnClickListener(signInLayout, this);
        ViewUtils.setOnClickListener(addFingerButton, this);
        ViewUtils.setOnClickListener(classBeginsLayout, this);
        classeLiminateLayout.setOnClickListener(this);

        ViewUtils.setOnClickListener(classeLiminateLayout,this);


        if (android.hardware.Camera.getNumberOfCameras() != 0) {
            addFaceButton.setVisibility(View.VISIBLE);
            requestRxPermissions(getString(R.string.open_camera_error), Manifest.permission.CAMERA);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.addFaceButton:
                Bundle faceBundle = new Bundle();
                faceBundle.putString(Constants.ActivityExtra.TYPE, "FACE");
                showActivity(BindActivity.class, faceBundle);
                break;
            case R.id.addFingerButton:
                Bundle fingerBundle = new Bundle();
                fingerBundle.putString(Constants.ActivityExtra.TYPE, "FINGER");
                showActivity(BindActivity.class, fingerBundle);
                break;
            case R.id.classeLiminateLayout:
                showActivity(EliminateClassActivity.class);
                break;

            case R.id.signInLayout:
                showActivity(ChoiceWayActivity.class);
                break;
            case R.id.classBeginsLayout:
                showActivity(AttendClassActivity.class);
                break;
        }
    }

    @Override
    public void modelMsg(int state, String msg) {

    }
}
