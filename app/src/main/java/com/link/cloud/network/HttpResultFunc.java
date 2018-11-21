package com.link.cloud.network;


import com.link.cloud.network.exception.ApiException;
import com.link.cloud.network.response.ApiResponse;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<ApiResponse<T>, ApiResponse<T>> {

    @Override
    public ApiResponse<T> call(ApiResponse<T> httpResult) {

        if (!httpResult.getStatus().equals("0")) {
            throw new ApiException(httpResult.getStatus(), httpResult.getMsg());
        }
        return httpResult;
    }

}