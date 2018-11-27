package com.link.cloud.controller;

import android.content.Context;

import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.MemberdataResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;

/**
 * 作者：qianlu on 2018/11/27 09:47
 * 邮箱：zar.l@qq.com
 */
public class InputPhoneContrller {

    private Context context;

    private InputPhoneListener listener;


    public interface InputPhoneListener {
        void getMemInfoSuccess(MemberdataResponse response);

        void getMemInfoFail(String message);

        void newWorkFail();
    }

    public InputPhoneContrller(InputPhoneListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    public void getMemInfo(String deviceID, int numberType, String numberValue) {

        ApiFactory.getMemInfo(deviceID,numberType,numberValue).subscribe(new ProgressSubscriber<ApiResponse<MemberdataResponse>>(context) {
            @Override
            public void onNext(ApiResponse<MemberdataResponse> memberdataResponseApiResponse) {
                if (listener != null) {
                    listener.getMemInfoSuccess(memberdataResponseApiResponse.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }


}
