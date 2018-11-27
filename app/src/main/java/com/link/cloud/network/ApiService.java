package com.link.cloud.network;


import com.google.gson.JsonObject;
import com.link.cloud.bean.FaceDateBean;
import com.link.cloud.bean.Person;
import com.link.cloud.network.bean.Code_Message;
import com.link.cloud.network.bean.DownLoadData;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.network.bean.Lockdata;
import com.link.cloud.network.bean.SignUser;
import com.link.cloud.network.bean.UpdateMessage;
import com.link.cloud.network.request.DeviceIdRequest;
import com.link.cloud.network.request.ValidationQrCodeRequest;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.AppUpdateInfoResponse;
import com.link.cloud.network.response.MemberdataResponse;
import com.link.cloud.network.response.PageInfoResponse;
import com.link.cloud.network.response.RegisterResponse;

import java.lang.reflect.Member;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lu on 2016/6/2.
 */
public interface ApiService {
    /**
     * 1.获取设备id
     */
    @POST("deviceRegister")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<RegisterResponse>> getdeviceId(@Body JsonObject params);

    /**
     * 2.设备更新
     */
    @POST("appUpdateInfo")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AppUpdateInfoResponse>> appUpdateInfo(@Body JsonObject params);


    @POST("syncUserFacePages")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<FaceDateBean>>>syncUserFacePages(@Body JsonObject params);

    /**
     * 3同步接口
     *
     * @param params
     * @return
     */
    @POST("appUpdateInfo")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<SignUser>>> syncSignUserFeature(@Body JsonObject params);

    /**
     * 4 获取数据大小
     */
    @POST("syncUserFeatureCount")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<PageInfoResponse>> syncUserFeatureCount(@Body JsonObject params);


    /**
     * 获取数据大小
     */
    @POST("syncUserFeaturePages")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<Person>>> syncUserFeaturePages(@Body JsonObject params);


    /**
     * 下载指静脉数据
     *
     * @param params
     * @return
     */
    @POST("downloadFeature")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<Person>>> downloadFeature(@Body JsonObject params);



    /**
     * 1.根据设备ID和手机号查询会员信息
     *
     * @param params REQUEST BODY请求体
     * @return Observable<Member>
     */
    @POST("validationUser")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<MemberdataResponse>> getMemInfo(@Body JsonObject params);


    /**
     * 1.消课接口
     *
     * @param params
     * @return Observable<Member>
     */
    @POST("getLessonInfo")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<LessonInfoResponse>> getLessonInfo(@Body JsonObject params);



    /**
     * 选择消课接口
     */
    @POST("selectLesson")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse>selectLesson(@Body JsonObject params);



    /**
     * 2.指静脉设备绑定会员接口
     *
     * @param params REQUEST BODY请求体
     * @return Observable<ReturnBean>
     */
    @POST("bindUserFinger")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<MemberdataResponse>> bindVeinMemeber(@Body JsonObject params);



    /**
     * 12.检测软件版本
     */
    @POST("deviceUpgrade")
    Observable<UpdateMessage> deviceUpgrade(@Body DeviceIdRequest params);


    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);


    /**
     * 储物柜操作
     *
     * @param params
     * @return
     */
    @POST("isOpenBrake")
    Observable<Lockdata> isOpenCabinet(@Body JsonObject params);




    //验证指静脉
    @POST("openBrakeByQrCode")
    Observable<Code_Message> validationQrCode(@Body ValidationQrCodeRequest paras);


    @POST("getNotReveiceFeature")
    Observable<DownLoadData> downloadNotReceiver(@Body DeviceIdRequest params);


}
