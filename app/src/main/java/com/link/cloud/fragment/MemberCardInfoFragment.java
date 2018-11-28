package com.link.cloud.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;

import com.link.cloud.base.BaseActivity;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.bean.Person;
import com.link.cloud.utils.Utils;


/**
 * 作者：qianlu on 2018/10/24 14:19
 * 邮箱：zar.l@qq.com
 */
public class MemberCardInfoFragment extends BaseFragment {

    private Person person;
    private TextView name;
    private TextView phoneText;
    private TextView userType;
    private LinearLayout successLayout;


    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        person = (Person) getArguments().getSerializable(Constants.FragmentExtra.BEAN);
        ((BaseActivity) getActivity()).speak(getResources().getString(R.string.please_begain));
        initView(contentView);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_usermembercardinfo;
    }

    private void initView(View contentView) {
        successLayout = (LinearLayout) contentView.findViewById(R.id.successLayout);
        name = (TextView) contentView.findViewById(R.id.name);
        phoneText = (TextView) contentView.findViewById(R.id.phoneText);
        userType = contentView.findViewById(R.id.userType);
        if (person != null) {
            name.setText(person.getUserName());
            userType.setText(person.getUserType() == 1 ? getResources().getString(R.string.member) : getResources().getString(R.string.office_holder));
            phoneText.setText(person.getNumber_value());
        }
        Utils.setOnClickListener(successLayout, this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.successLayout:
                getActivity().finish();
                break;
        }
    }
}
