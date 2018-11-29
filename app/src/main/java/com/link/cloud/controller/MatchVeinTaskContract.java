package com.link.cloud.controller;


import android.content.Context;

import com.link.cloud.User;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.bean.Code_Message;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;

import rx.functions.Action1;

/**
 * Created by Shaozy on 2016/8/11.
 */
public class MatchVeinTaskContract {

    private MatchVeinListener listener;
    private Context context;


    public interface MatchVeinListener {
        void signFaild(String message);
        void checkInSuccess();
    }

    public MatchVeinTaskContract(MatchVeinListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    //2014/4/3新品台签到接口
    public void signedMember( String uid, String fromType) {



        ApiFactory.signedMember(User.get().getDeviceId(), uid, fromType).subscribe(new Action1<ApiResponse>() {
            @Override
            public void call(ApiResponse response) {
                listener.checkInSuccess();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                listener.signFaild(throwable.getMessage());
            }
        });

    }

}
