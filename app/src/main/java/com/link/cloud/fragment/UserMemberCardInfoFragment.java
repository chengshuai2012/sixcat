package com.link.cloud.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.activity.BindActivity;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.network.bean.MemberCardBean;
import com.link.cloud.network.response.MemberdataResponse;
import com.link.cloud.utils.Utils;
import com.link.cloud.widget.CountDownButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qianlu on 2018/10/24 14:19
 * 邮箱：zar.l@qq.com
 */
public class UserMemberCardInfoFragment extends BaseFragment {

    private List<MemberCardBean> mList = new ArrayList();
    private String type;

    private android.widget.LinearLayout selecteLayout;
    private android.widget.TextView backButton;
    private Button nextButton;
    private android.widget.LinearLayout successLayout;
    private TextView name;
    private TextView phoneText;
    private TextView userType;

    private MemberdataResponse memberdataResponse;


    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        type = getArguments().getString(Constants.FragmentExtra.TYPE);
        initView(contentView);
        setData();
    }


    private void setData() {


    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_usermembercardinfo;
    }

    private void initView(View contentView) {

        selecteLayout = (LinearLayout) contentView.findViewById(R.id.selecteLayout);
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton =  contentView.findViewById(R.id.nextButton);
        successLayout = (LinearLayout) contentView.findViewById(R.id.successLayout);
        name = (TextView) contentView.findViewById(R.id.name);
        phoneText = (TextView) contentView.findViewById(R.id.phoneText);
        userType = contentView.findViewById(R.id.userType);
        memberdataResponse = ((BindActivity) getActivity()).getMemberdataResponse();

        if (type.equals("INFO")) {
            successLayout.setVisibility(View.GONE);
            selecteLayout.setVisibility(View.VISIBLE);
        } else {
            successLayout.setVisibility(View.VISIBLE);
            selecteLayout.setVisibility(View.GONE);
        }
        if (memberdataResponse != null) {
            if (memberdataResponse.getUserInfo().getSex() == 0) {
                name.setText(memberdataResponse.getUserInfo().getName() + "   " + getResources().getString(R.string.boy));
            } else {
                name.setText(memberdataResponse.getUserInfo().getName() + "   " + getResources().getString(R.string.girl));
            }
            userType.setText(memberdataResponse.getUserInfo().getUserType().equals("1") ? getResources().getString(R.string.member) : getResources().getString(R.string.office_holder));
            phoneText.setText(memberdataResponse.getUserInfo().getPhone());
        }

        Utils.setOnClickListener(backButton, this);
        Utils.setOnClickListener(nextButton, this);
        Utils.setOnClickListener(successLayout, this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                RxBus.get().post(new Events.finish());
                getActivity().onBackPressed();
                break;
            case R.id.nextButton:
                RxBus.get().post(new Events.CardInfoNextView());
                getActivity().onBackPressed();
                break;

            case R.id.successLayout:
                RxBus.get().post(new Events.finish());
                break;
        }
    }
}
