package com.link.cloud.api;


import com.zitech.framework.data.network.exception.ApiException;
import com.zitech.framework.data.network.response.ApiResponse;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<ApiResponse<T>, ApiResponse<T>> {

    @Override
    public ApiResponse<T> call(ApiResponse<T> httpResult) {
//
//        if (!httpResult.getCode().equals("200000")) {
//            throw new ApiException(httpResult.getCode(), httpResult.getMessage());
//        }

        return httpResult;
    }

}