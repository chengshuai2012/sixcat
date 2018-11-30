package com.link.cloud.controller;

import android.content.Context;

import com.link.cloud.bean.Person;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.PageInfoResponse;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import rx.functions.Action1;

/**
 * 作者：qianlu on 2018/11/9 14:04
 * 邮箱：zar.l@qq.com
 */
public class SynchroDataController {

    SynchroDataListener listener;
    private Context context;

    public interface SynchroDataListener {

        void getPageInfoSuccess(PageInfoResponse response);

        void getPageInfoFail(String message);

        void getPageDateSuccess(List<Person> date);

        void getPageDateFail(String message);

        void newWorkFail();


    }

    public SynchroDataController(SynchroDataListener listener, Context context) {
        this.listener = listener;
        this.context = context;
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
                if ( throwable instanceof ConnectException|| throwable instanceof TimeoutException){
                    listener.newWorkFail();
                }else {
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
                if ( throwable instanceof ConnectException|| throwable instanceof TimeoutException){
                    listener.newWorkFail();
                }else {
                    listener.getPageDateFail(throwable.getMessage());
                }

            }
        });


    }


}
