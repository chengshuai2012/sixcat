package com.link.cloud.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.utils.Venueutils;
import com.zitech.framework.utils.ViewUtils;

/**
 * 作者：qianlu on 2018/10/24 18:17
 * 邮箱：zar.l@qq.com
 */
public class AddFingerFragment extends BaseFragment {

    private android.widget.Button cardNumber;
    private android.widget.TextView backButton;
    private android.widget.TextView nextButton;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                RxBus.get().post(new Events.BackView());
                break;
        }
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        initView(contentView);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_addfinger;
    }


    private void initView(View contentView) {
        cardNumber = (Button) contentView.findViewById(R.id.cardNumber);
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton = (TextView) contentView.findViewById(R.id.nextButton);

        ViewUtils.setOnClickListener(backButton, this);
    }

}
