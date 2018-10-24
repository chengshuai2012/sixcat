package com.link.cloud.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.adapter.LessonConsumeAdapter;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.utils.Utils;
import com.link.cloud.widget.CardConfig;
import com.link.cloud.widget.CountDownButton;
import com.link.cloud.widget.SwipeCardCallBack;
import com.link.cloud.widget.SwipeCardLayoutManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 作者：qianlu on 2018/10/24 14:19
 * 邮箱：zar.l@qq.com
 */
public class UserMemberCardInfoFragment extends BaseFragment {

    private ArrayList<String> mList = new ArrayList();
    private android.support.v7.widget.RecyclerView recycle;
    private String type;
    private android.widget.Button cardNumber;
    private android.widget.LinearLayout selecteLayout;
    private android.widget.TextView backButton;
    private com.link.cloud.widget.CountDownButton nextButton;
    private android.widget.LinearLayout successLayout;

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        type = getArguments().getString(Constants.FragmentExtra.TYPE);
        initView(contentView);
        setData();
    }


    private void setData() {

        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
        mList.add("5");

        SwipeCardLayoutManager swmanamger = new SwipeCardLayoutManager(getActivity());
        recycle.setLayoutManager(swmanamger);
        Collections.reverse(mList);
        LessonConsumeAdapter mAdatper = new LessonConsumeAdapter(mList, getActivity());
        recycle.setAdapter(mAdatper);
        mAdatper.setOnDateChangeListner(new LessonConsumeAdapter.onDateChangeListner() {
            @Override
            public void change(int position) {
                cardNumber.setText(mList.get(position));
            }
        });
        CardConfig.initConfig(getActivity());
        ItemTouchHelper.Callback callback = new SwipeCardCallBack(mList, mAdatper, recycle);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recycle);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_usermembercardinfo;
    }

    private void initView(View contentView) {
        recycle = (RecyclerView) contentView.findViewById(R.id.recycle);
        cardNumber = (Button) contentView.findViewById(R.id.cardNumber);
        selecteLayout = (LinearLayout) contentView.findViewById(R.id.selecteLayout);
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton = (CountDownButton) contentView.findViewById(R.id.nextButton);
        successLayout = (LinearLayout) contentView.findViewById(R.id.successLayout);

        if (type.equals("INFO")) {
            successLayout.setVisibility(View.GONE);
            selecteLayout.setVisibility(View.VISIBLE);
            nextButton.startTimer();
            cardNumber.setBackground(getResources().getDrawable(R.drawable.border_red_gradient_carinfo_bg));
        } else {
            successLayout.setVisibility(View.VISIBLE);
            selecteLayout.setVisibility(View.GONE);
            cardNumber.setBackground(getResources().getDrawable(R.drawable.border_add_face));
        }
        Utils.setOnClickListener(backButton, this);
        Utils.setOnClickListener(nextButton, this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                RxBus.get().post(new Events.BackView());
                getActivity().onBackPressed();
                break;
            case R.id.nextButton:
                RxBus.get().post(new Events.CardInfoNextView());
                break;


        }
    }
}
