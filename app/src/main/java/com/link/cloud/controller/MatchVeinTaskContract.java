package com.link.cloud.controller;


import android.content.Context;

import com.link.cloud.User;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.bean.Code_Message;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;

/**
 * Created by Shaozy on 2016/8/11.
 */
public class MatchVeinTaskContract {

    private MatchVeinListener listener;
    private Context context;


    public interface MatchVeinListener {
        void signSuccess(Code_Message signedResponse);

        void checkInSuccess(Code_Message code_message);
    }

    public MatchVeinTaskContract(MatchVeinListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    //2014/4/3新品台签到接口
    public void signedMember(String deviceId, String uid, String fromType) {
        ApiFactory.signedMember(User.get().getDeviceId(), uid, fromType).subscribe(new ProgressSubscriber<ApiResponse>(context) {
            @Override
            public void onNext(ApiResponse apiResponse) {
                super.onNext(apiResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

}
