package com.link.cloud.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.utils.Venueutils;

/**
 * 作者：qianlu on 2018/10/24 18:17
 * 邮箱：zar.l@qq.com
 */
public class AddFingerFragment extends BaseFragment implements Venueutils.VenueCallBack {

    private android.widget.Button cardNumber;
    private android.widget.TextView backButton;
    private android.widget.TextView nextButton;

    @Override
    public void onClick(View v) {
        super.onClick(v);
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

    @Override
    public void modelMsg(int state, String msg) {
        if (state == 3) {
//            BindFinger bindFinger = new BindFinger();
//            bindFinger.setFingerprint(msg);
//            bindFinger.setId(edituserRequest.getId());
//            bindFinger.setMerchantId(edituserRequest.getMerchantId());
//            bindFinger.setUserType(edituserRequest.getUserType());
//            bindFinger.setUuid(edituserRequest.getUuid());
//            ApiFactory.edituser(bindFinger).subscribe(new BaseProgressSubscriber<ApiResponse>(this) {
//                @Override
//                public void onNext(ApiResponse apiResponse) {
//                    bindMiddleTwo.setVisibility(View.INVISIBLE);
//                    bindMiddleThree.setVisibility(View.VISIBLE);
//                    registerIntroduceFive.setTextColor(getResources().getColor(R.color.red));
//                    registerIntroduceThree.setTextColor(getResources().getColor(R.color.text_register));
//                    cardNum.setText(getResources().getString(R.string.now_card)+edituserRequest.getPhone());
//                    animator.cancel();
//                }
//            });
            cardNumber.setText(getResources().getString(R.string.finger_success));
        }
        if (state == 2) {
            cardNumber.setText(getResources().getString(R.string.same_finger));
        }
        if (state == 1) {
            cardNumber.setText(getResources().getString(R.string.again_finger));
        }
    }

    private void initView(View contentView) {
        cardNumber = (Button) contentView.findViewById(R.id.cardNumber);
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton = (TextView) contentView.findViewById(R.id.nextButton);
    }
}
