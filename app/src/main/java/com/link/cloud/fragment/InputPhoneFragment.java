package com.link.cloud.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.utils.Utils;
import com.zitech.framework.utils.ViewUtils;

/**
 * 作者：qianlu on 2018/10/23 10:51
 * 邮箱：zar.l@qq.com
 */
public class InputPhoneFragment extends BaseFragment {
    private android.widget.EditText phoneNum;
    private android.widget.Button bindKeypad1;
    private android.widget.Button bindKeypad2;
    private android.widget.Button bindKeypad3;
    private android.widget.Button bindKeypad4;
    private android.widget.Button bindKeypad5;
    private android.widget.Button bindKeypad6;
    private android.widget.Button bindKeypad7;
    private android.widget.Button bindKeypad8;
    private android.widget.Button bindKeypad9;
    private android.widget.Button cleanButton;
    private android.widget.Button bindKeypad0;
    private android.widget.Button deleteButton;
    private android.widget.TextView backButton;
    private android.widget.TextView nextButton;
    private StringBuilder builder;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_inputphone;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        initView(contentView);
    }

    private void initView(View contentView) {
        phoneNum = (EditText) contentView.findViewById(R.id.phoneNum);
        bindKeypad1 = (Button) contentView.findViewById(R.id.bind_keypad_1);
        bindKeypad2 = (Button) contentView.findViewById(R.id.bind_keypad_2);
        bindKeypad3 = (Button) contentView.findViewById(R.id.bind_keypad_3);
        bindKeypad4 = (Button) contentView.findViewById(R.id.bind_keypad_4);
        bindKeypad5 = (Button) contentView.findViewById(R.id.bind_keypad_5);
        bindKeypad6 = (Button) contentView.findViewById(R.id.bind_keypad_6);
        bindKeypad7 = (Button) contentView.findViewById(R.id.bind_keypad_7);
        bindKeypad8 = (Button) contentView.findViewById(R.id.bind_keypad_8);
        bindKeypad9 = (Button) contentView.findViewById(R.id.bind_keypad_9);
        cleanButton = (Button) contentView.findViewById(R.id.cleanButton);
        bindKeypad0 = (Button) contentView.findViewById(R.id.bind_keypad_0);
        deleteButton = (Button) contentView.findViewById(R.id.deleteButton);
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton = (TextView) contentView.findViewById(R.id.nextButton);
        Utils.setCanNotEditAndClick(phoneNum);
        builder = new StringBuilder();
        ViewUtils.setOnClickListener(bindKeypad1, this);
        ViewUtils.setOnClickListener(bindKeypad2, this);
        ViewUtils.setOnClickListener(bindKeypad3, this);
        ViewUtils.setOnClickListener(bindKeypad4, this);
        ViewUtils.setOnClickListener(bindKeypad5, this);
        ViewUtils.setOnClickListener(bindKeypad6, this);
        ViewUtils.setOnClickListener(bindKeypad7, this);
        ViewUtils.setOnClickListener(bindKeypad8, this);
        ViewUtils.setOnClickListener(bindKeypad9, this);
        ViewUtils.setOnClickListener(cleanButton, this);
        ViewUtils.setOnClickListener(bindKeypad0, this);
        ViewUtils.setOnClickListener(deleteButton, this);
        ViewUtils.setOnClickListener(backButton, this);
        ViewUtils.setOnClickListener(nextButton, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bind_keypad_0:
            case R.id.bind_keypad_1:
            case R.id.bind_keypad_2:
            case R.id.bind_keypad_3:
            case R.id.bind_keypad_4:
            case R.id.bind_keypad_5:
            case R.id.bind_keypad_6:
            case R.id.bind_keypad_7:
            case R.id.bind_keypad_8:
            case R.id.bind_keypad_9:
                builder.append(((TextView) v).getText());
                if (phoneNum != null) {
                    phoneNum.setText(builder.toString());
                }
                break;
            case R.id.deleteButton:
                if (builder.length() >= 1) {
                    builder.deleteCharAt(builder.length() - 1);
                    builder.setLength(builder.length());
                } else {
                    builder.setLength(builder.length());
                }
                if (phoneNum != null) {
                    phoneNum.setText(builder.toString());
                }
                break;

            case R.id.cleanButton:
                builder.delete(0, builder.length());
                builder.setLength(builder.length());
                if (phoneNum != null) {
                    phoneNum.setText(builder.toString());
                }
                break;
            case R.id.backButton:
                getActivity().finish();
                break;
            case R.id.nextButton:
                RxBus.get().post(new Events.NextView());
                break;
        }

    }
}
