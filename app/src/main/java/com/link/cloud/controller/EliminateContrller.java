package com.link.cloud.controller;

import android.content.Context;

import com.link.cloud.User;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.network.response.ApiResponse;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import rx.functions.Action1;


/**
 * 作者：qianlu on 2018/11/20 15:10
 * 邮箱：zar.l@qq.com
 */
public class EliminateContrller {

    EliminateListener listener;
    private Context context;

    public interface EliminateListener {

        void getLessonInfoSuccess(LessonInfoResponse response);

        void getLessonInfoFail(String message);

        void newWorkFail();
    }

    public EliminateContrller(EliminateListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    public void getLessonInfo(int type, String memberid, String coachid, String clerkid) {

        ApiFactory.getLessonInfo(User.get().getDeviceId(), type, memberid, coachid, clerkid).subscribe(new Action1<ApiResponse<LessonInfoResponse>>() {
            @Override
            public void call(ApiResponse<LessonInfoResponse> response) {
                listener.getLessonInfoSuccess(response.getData());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                    listener.newWorkFail();
                } else {
                    listener.getLessonInfoFail(throwable.getMessage());
                }
            }
        });


    }


}
