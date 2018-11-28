package com.link.cloud.network;


import com.google.gson.JsonObject;
import com.link.cloud.User;
import com.link.cloud.bean.FaceDateBean;
import com.link.cloud.bean.Person;
import com.link.cloud.network.bean.Code_Message;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.network.bean.SignUser;
import com.link.cloud.network.request.GetDeviceIdRequest;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.AppUpdateInfoResponse;
import com.link.cloud.network.response.MemberdataResponse;
import com.link.cloud.network.response.PageInfoResponse;
import com.link.cloud.network.response.RegisterResponse;
import com.link.cloud.network.subscribe.SchedulersCompat;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Api协议工厂，具体方法代码通过{@link }来生成
 */
public class ApiFactory {

    private static Map<String, Object> mCache = new HashMap();


    public static ApiService getApiService() {
        return getService(ApiConstants.DAILY_DAILY_URL, ApiService.class);
    }

    public static <T> T getService(String baseUrl, Class<T> cls) {
        Object target = mCache.get(baseUrl);
        if (target == null || !cls.isAssignableFrom(target.getClass())) {
            target = RetrofitClient.getInstance().create(baseUrl, cls);
            mCache.put(baseUrl, target);
        }
        return (T) target;
    }


    /**
     * 注册
     *
     * @return
     */
    public static Observable<ApiResponse<RegisterResponse>> getDviceId(GetDeviceIdRequest request) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceType", request.getDeviceType());
        pareams.addProperty("deviceTargetValue", request.getDeviceTargetValue());
        return getApiService().getdeviceId(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 更新
     *
     * @return
     */
    public static Observable<ApiResponse<AppUpdateInfoResponse>> appUpdateInfo(String deviceId) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceId);
        return getApiService().appUpdateInfo(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


    public static Observable<ApiResponse<List<FaceDateBean>>> syncUserFacePages(String deviceId) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceId);
        return getApiService().syncUserFacePages(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());

    }


    /**
     * 更新
     *
     * @return
     */
    public static Observable<ApiResponse<List<SignUser>>> syncSignUserFeature(String deviceId) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceId);
        return getApiService().syncSignUserFeature(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取分页数据的大小
     *
     * @return
     */
    public static Observable<ApiResponse<PageInfoResponse>> syncUserFeatureCount(String deviceId) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceId);
        return getApiService().syncUserFeatureCount(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 获取数据
     *
     * @return
     */
    public static Observable<ApiResponse<List<Person>>> syncUserFeaturePages(String deviceId, int currentPage) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceId);
        pareams.addProperty("currPage", currentPage);
        return getApiService().syncUserFeaturePages(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 下载指静脉数据
     *
     * @return
     */
    public static Observable<ApiResponse<List<Person>>> downloadFeature(String messageId, String appid, String shopId, String deviceId, String uid) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("messageId", messageId);
        pareams.addProperty("appid", appid);
        pareams.addProperty("shopId", shopId);
        pareams.addProperty("deviceId", deviceId);
        pareams.addProperty("uid", uid);
        return getApiService().downloadFeature(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 1.根据设备ID和手机号查询会员信息
     *
     * @return Observable<Member>
     */
    public static Observable<ApiResponse<MemberdataResponse>> getMemInfo(String deviceID, int numberType, String numberValue) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceID);
        pareams.addProperty("numberType", numberType);
        pareams.addProperty("numberValue", numberValue);
        return getApiService().getMemInfo(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 2.指静脉设备绑定会员接口
     */
    public static Observable<ApiResponse<MemberdataResponse>> bindVeinMemeber(String userType, String numberValue, String feature) {
        JsonObject params = new JsonObject();
        params.addProperty("deviceId", User.get().getDeviceId());
        params.addProperty("userType", userType);
        params.addProperty("numberType", User.get().getNumberType());
        params.addProperty("numberValue", numberValue);
        params.addProperty("feature1", feature);
        params.addProperty("feature2", feature);
        params.addProperty("feature3", feature);
        params.addProperty("feature", feature);
        return getApiService().bindVeinMemeber(params).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 1.根据设备ID和手机号查询会员信息
     *
     * @return Observable<Member>
     */
    public static Observable<ApiResponse<LessonInfoResponse>> getLessonInfo(String deviceID, int type, String memberid, String coachid) {
        JsonObject pareams = new JsonObject();
        pareams.addProperty("deviceId", deviceID);
        pareams.addProperty("type", type);
        pareams.addProperty("memberid", memberid);
        pareams.addProperty("coachid", coachid);
        return getApiService().getLessonInfo(pareams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 选择消课接口
     */
    public static Observable<ApiResponse> selectLesson(int type, String lessonId, String memberid, String coachid, String clerkid, String CardNo, int count) {
        JsonObject params = new JsonObject();
        params.addProperty("deviceId", User.get().getDeviceId());
        params.addProperty("type", type);
        params.addProperty("lessonId", lessonId);
        params.addProperty("memberid", memberid);
        params.addProperty("coachid", coachid);
        params.addProperty("cardNo", CardNo);
        params.addProperty("number", count);
        return getApiService().selectLesson(params).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }





    public static Observable<ApiResponse> signedMember(String deviceId, String uid, String fromType) {
        JsonObject params = new JsonObject();
        params.addProperty("deviceId", deviceId);
        params.addProperty("uid", uid);
        params.addProperty("fromType", fromType);
        return getApiService().signMember(params).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());

    }

    public static Observable<ApiResponse> checkInByQrCode( String qrCodeStr) {
        JsonObject params = new JsonObject();
        params.addProperty("deviceId", User.get().getDeviceId());
        params.addProperty("qrCodeStr", qrCodeStr);
        return getApiService().checkInByQrCode(params).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());

    }


}
