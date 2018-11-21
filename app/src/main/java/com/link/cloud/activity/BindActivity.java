package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.adapter.PublicTitleAdapter;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.fragment.AddFaceFragment;
import com.link.cloud.fragment.AddFingerFragment;
import com.link.cloud.fragment.InputPhoneFragment;
import com.link.cloud.fragment.UserMemberCardInfoFragment;
import com.link.cloud.utils.Utils;
import com.zitech.framework.utils.ToastMaster;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class BindActivity extends BaseActivity {


    private RecyclerView recycle;
    private FrameLayout contentFrame;
    private String type;


    private PublicTitleAdapter publicTitleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getExtras().getString(Constants.ActivityExtra.TYPE);
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
        publicTitleAdapter = new PublicTitleAdapter(this);
        String[] faceArray = getResources().getStringArray(R.array.bind_face_Array);
        String[] fingerArray = getResources().getStringArray(R.array.bind_finger_Array);
        List<String> date = new ArrayList<>();
        if (type.equals("FACE")) {
            for (String dateInfo : faceArray) {
                date.add(dateInfo);
            }
        } else {
            for (String dateInfo : fingerArray) {
                date.add(dateInfo);
            }
        }
        publicTitleAdapter.setDate(date);
        recycle.setAdapter(publicTitleAdapter);
        showFragment(InputPhoneFragment.class);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void onDataChanged(Events.NextView event) {
        publicTitleAdapter.next();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FragmentExtra.TYPE, "INFO");
        showNewFragment(UserMemberCardInfoFragment.class, bundle);
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void CardInfoNextView(Events.CardInfoNextView event) {
        publicTitleAdapter.next();
        if (type.equals("FACE")) {
            showNewFragment(AddFaceFragment.class);
        } else {
          showNewFragment(AddFingerFragment.class);
        }

    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void onDataChanged(Events.SuccessView event) {
        publicTitleAdapter.next();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FragmentExtra.TYPE, "FINISH");
        showNewFragment(UserMemberCardInfoFragment.class, bundle);
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void BackView(Events.BackView event) {
        publicTitleAdapter.last();
    }

    @Override
    public void modelMsg(int state, String msg) {

    }
}
