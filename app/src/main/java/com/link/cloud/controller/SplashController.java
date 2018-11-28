package com.link.cloud.controller;

import android.content.Context;

import com.link.cloud.User;
import com.link.cloud.bean.FaceDateBean;
import com.link.cloud.bean.Person;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.request.GetDeviceIdRequest;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.AppUpdateInfoResponse;
import com.link.cloud.network.response.PageInfoResponse;
import com.link.cloud.network.response.RegisterResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import rx.functions.Action1;

/**
 * 作者：qianlu on 2018/11/9 14:04
 * 邮箱：zar.l@qq.com
 */
public class SplashController {

    SplashListener listener;
    private Context context;

    public interface SplashListener {
        void getDeviceIdSuccess(RegisterResponse registerResponse);

        void getDeviceIdFail(String message);


        void getPageInfoSuccess(PageInfoResponse response);

        void getPageInfoFail(String message);

        void getPageDateSuccess(List<Person> date);

        void getPageDateFail(String message);

        void newWorkFail();

        void syncUserFacePagesSuccess(List<FaceDateBean> dateBeans);

        void syncUserFaceFail(String message);

        void getUpdateInfoSuccess(AppUpdateInfoResponse response);

    }

    public SplashController(SplashListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void getDeviceID(String devicedata, int deviceType) {
        GetDeviceIdRequest request = new GetDeviceIdRequest();
        request.setDeviceTargetValue(devicedata);
        request.setDeviceType(deviceType);
        ApiFactory.getDviceId(request).subscribe(new Action1<ApiResponse<RegisterResponse>>() {
            @Override
            public void call(ApiResponse<RegisterResponse> response) {
                User.get().storageInfo(response.getData());
                listener.getDeviceIdSuccess(response.getData());
                if (android.hardware.Camera.getNumberOfCameras() != 0) {
                    syncUserFacePages(response.getData().getDeviceId());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable4=" + throwable.getMessage());
                if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                    listener.newWorkFail();
                } else {
                    listener.getDeviceIdFail(throwable.getMessage());
                }

            }
        });
    }


    public void syncUserFacePages(String deviceId) {
        ApiFactory.syncUserFacePages(deviceId).subscribe(new Action1<ApiResponse<List<FaceDateBean>>>() {
            @Override
            public void call(ApiResponse<List<FaceDateBean>> response) {
                listener.syncUserFacePagesSuccess(response.getData());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable3=" + throwable.getMessage());
                if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                    listener.newWorkFail();
                } else {
                    listener.syncUserFaceFail(throwable.getMessage());
                }
            }
        });

    }





    public void syncUserFeatureCount(String deviceId) {

        ApiFactory.syncUserFeatureCount(deviceId).subscribe(new Action1<ApiResponse<PageInfoResponse>>() {
            @Override
            public void call(ApiResponse<PageInfoResponse> response) {
                listener.getPageInfoSuccess(response.getData());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable2=" + throwable.getMessage());
                if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                    listener.newWorkFail();
                } else {
                    listener.getPageInfoFail(throwable.getMessage());
                }
            }
        });
    }

    public void syncUserFeaturePages(String deviceId, int currentPage) {

        ApiFactory.syncUserFeaturePages(deviceId, currentPage).subscribe(new Action1<ApiResponse<List<Person>>>() {
            @Override
            public void call(ApiResponse<List<Person>> listApiResponse) {
                listener.getPageDateSuccess(listApiResponse.getData());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable1=" + throwable.getMessage());
                if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                    listener.newWorkFail();
                } else {
                    listener.getPageDateFail(throwable.getMessage());
                }

            }
        });


    }


}
