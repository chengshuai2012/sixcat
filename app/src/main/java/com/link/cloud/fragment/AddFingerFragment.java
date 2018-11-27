package com.link.cloud.fragment;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.hwangjr.rxbus.RxBus;
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
public class AddFingerFragment extends BaseFragment implements Venueutils.VenueCallBack {

    private android.widget.Button cardNumber;
    private android.widget.TextView backButton;
    private android.widget.TextView nextButton;
    private ValueAnimator animator;
    private CircleProgressBar customProgress;
    private MemberdataResponse response;
    private RxTimerUtil rxTimerUtil;


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                animator.cancel();
                RxBus.get().post(new Events.BackView());
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        initView(contentView);
        SixCatApplication.getVenueUtils().initVenue(getActivity(), this, false);
        simulateProgress();
        ((BindActivity) getActivity()).speak(getResources().getString(R.string.please_add_finger));
    }


    private void simulateProgress() {
        rxTimerUtil=new RxTimerUtil();
        animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                if (customProgress != null) {
                    customProgress.setProgress(progress);
                    int state = SixCatApplication.getVenueUtils().getState();
                    if (state == 3) {
                        if (getActivity() != null){
                            SixCatApplication.getVenueUtils().workModel();
                            animator.setCurrentPlayTime(0);
                            cardNumber.setText(getResources().getString(R.string.again_finger));
                        }
                    }
                    if (state == 4) {

                    }
                    if (state != 4 && state != 3) {
                        if (getActivity() != null)
                            cardNumber.setText(getResources().getString(R.string.right_finger));
                    }
                }
            }
        });
        animator.setDuration(40000);
        animator.start();

        rxTimerUtil.timer(40000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                RxBus.get().post(new Events.BackView());
                getActivity().onBackPressed();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        rxTimerUtil.cancel();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_addfinger;
    }


    private void initView(View contentView) {

        response = ((BindActivity) getActivity()).getMemberdataResponse();
        cardNumber = (Button) contentView.findViewById(R.id.cardNumber);
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton = (TextView) contentView.findViewById(R.id.nextButton);
        customProgress = contentView.findViewById(R.id.custom_progress);
        customProgress.setProgressFormatter(null);
        customProgress.setMax(100);
        ViewUtils.setOnClickListener(backButton, this);
    }


    @Override
    public void modelMsg(int state, String msg) {
        Log.e("modelMsg: ", state + ">>>>>>>>>");
        TTSUtils.getInstance().speak(msg);
        if (state == 3) {
            animator.cancel();
            ApiFactory.bindVeinMemeber(response.getUserInfo().getUserType(), response.getUserInfo().getPhone(), msg).subscribe(new ProgressSubscriber<ApiResponse<MemberdataResponse>>(getActivity()) {
                @Override
                public void onNext(ApiResponse<MemberdataResponse> apiResponse) {
                    response = apiResponse.getData();
                    RxBus.get().post(new Events.AddFingerSuccess());
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    RxBus.get().post(new Events.BackView());
                    getActivity().onBackPressed();
                }
            });

        }
        if (state == 2) {
            cardNumber.setText(getResources().getString(R.string.same_finger));
        }
        if (state == 1) {
            cardNumber.setText(getResources().getString(R.string.again_finger));
        }
    }
}
