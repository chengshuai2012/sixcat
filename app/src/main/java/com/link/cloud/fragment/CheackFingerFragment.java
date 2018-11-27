package com.link.cloud.fragment;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.SixCatApplication;
import com.link.cloud.activity.BindActivity;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.MemberdataResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;
import com.link.cloud.utils.RxTimerUtil;
import com.link.cloud.utils.TTSUtils;
import com.link.cloud.utils.Venueutils;
import com.zitech.framework.utils.ViewUtils;

/**
 * 作者：qianlu on 2018/10/24 18:17
 * 邮箱：zar.l@qq.com
 */
public class CheackFingerFragment extends BaseFragment {

    private Button cardNumber;
    private TextView backButton;
    private TextView nextButton;
    private ValueAnimator animator;


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                RxBus.get().post(new Events.BackView());
                getActivity().onBackPressed();
                break;
        }
    }


    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        backButton = contentView.findViewById(R.id.backButton);
        cardNumber = contentView.findViewById(R.id.cardNumber);
        cardNumber.setText(getArguments().getString(Constants.FragmentExtra.TYPE));
        backButton.setOnClickListener(this);

    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_addfinger;
    }


}
