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


    private PublicTitleAdapter publicTitleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        initView();
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
        String[] bindArray = getResources().getStringArray(R.array.bind_Array);
        List<String> date = new ArrayList<>();

        for (String dateInfo : bindArray) {
            date.add(dateInfo);
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
        showNewFragment(AddFaceFragment.class);
    }



    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void BackView(Events.BackView event) {
        publicTitleAdapter.last();
    }

}
