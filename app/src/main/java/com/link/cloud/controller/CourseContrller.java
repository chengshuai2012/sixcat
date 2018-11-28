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
 * 作者：qianlu on 2018/11/28 11:30
 * 邮箱：zar.l@qq.com
 */
public class CourseContrller {

    CourseContrllerListener listener;
    private Context context;

    public interface CourseContrllerListener {

        void selectLessonSuccess(LessonInfoResponse response);

        void selectLessonFail(String message);

        void newWorkFail();
    }

    public CourseContrller(CourseContrllerListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

}
