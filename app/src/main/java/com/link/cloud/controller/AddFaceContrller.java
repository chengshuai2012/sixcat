package com.link.cloud.controller;

import android.content.Context;

import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

/**
 * 作者：qianlu on 2018/11/29 15:47
 * 邮箱：zar.l@qq.com
 */
public class AddFaceContrller {

    private AddFaceContrllerListener listener;
    private Context context;

    public interface AddFaceContrllerListener {

        void addFaceSuccess();

        void addFaveFaild(String message);

        void newWorkFail();
    }

    public AddFaceContrller(AddFaceContrllerListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    public void bindFace(int numberType, String numberValue, int userType, String path, String faceFile) {
        ApiFactory.bindFace(numberType, numberValue, userType, path, faceFile).subscribe(new ProgressSubscriber<ApiResponse>(context) {

            @Override
            public void onNext(ApiResponse response) {
                if (listener != null) {
                    listener.addFaceSuccess();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                    listener.newWorkFail();
                } else {
                    listener.addFaveFaild(throwable.getMessage());
                }

            }
        });


    }


}
