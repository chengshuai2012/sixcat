package com.link.cloud;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.google.gson.Gson;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.link.cloud.bean.BindFaceMes;
import com.link.cloud.bean.Person;
import com.link.cloud.bean.PushMessage;
import com.link.cloud.bean.PushUpDateBean;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.bean.SignUser;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;
import com.link.cloud.utils.DownLoad;
import com.link.cloud.utils.DownloadUtils;
import com.link.cloud.utils.TimeService;
import com.link.cloud.utils.Venueutils;
import com.orhanobut.logger.Logger;
import com.zitech.framework.BaseApplication;
import com.zitech.framework.utils.ToastMaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SixCatApplication extends BaseApplication {

    private Handler mainThreadHandler;
    private static final String TAG = "AccessContorlApplication";
    public static Venueutils venueUtils;
    private User user;

    public static final String COUNT_CHANGE = "change_count";
    public int count = 0;
    public static Realm realm;


    static PushMessage pushMessage;


    public static Venueutils getVenueUtils() {
        synchronized (Venueutils.class) {
            if (venueUtils == null) {
                venueUtils = new Venueutils();
            }
            return venueUtils;
        }
    }


    public User getUser() {
        return user;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        mainThreadHandler = new Handler(Looper.getMainLooper());
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("sixcat.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();
        intSpeak();
        lifeRegister();
        initCloudChannel(this);

    }


    /**
     * 初始化云推送通道
     */
    private void initCloudChannel(final Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        String id = pushService.getDeviceId();
        Logger.e("" + id);
        pushService.register(applicationContext, new CommonCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
                //获取设备号
                pushService.getDeviceId();
                Log.d(TAG, "初始化成功   " + response + "  " + pushService.getDeviceId());

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    private void lifeRegister() {
        Intent intent = new Intent(getApplicationContext(), TimeService.class);
        startService(intent);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                count++;
                Log.e("onActivityStarted: ", count + "");
                Intent countIntent = new Intent(COUNT_CHANGE);
                countIntent.putExtra("count", count);
                sendBroadcast(countIntent);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                count--;
                Intent countIntent = new Intent(COUNT_CHANGE);
                countIntent.putExtra("count", count);
                sendBroadcast(countIntent);
            }
        });
    }


    public static void setConsoleText(String text) {
        Logger.e("BaseApplication setConsoleText====================" + text);
        pushMessage = toJsonArray(text);
        if ("1".equals(pushMessage.getType())) {
            getDate();
        } else if ("9".equals(pushMessage.getType())) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    SignUser signUser = new SignUser();
                    signUser.setUid(pushMessage.getUid());
                    realm.copyToRealm(signUser);
                }
            });
        } else if ("10".equals(pushMessage.getType()) && android.hardware.Camera.getNumberOfCameras() != 0) {
            Gson gson = new Gson();
            BindFaceMes bindFaceMes = gson.fromJson(text, BindFaceMes.class);
            DownLoad.download(bindFaceMes.getFaceUrl(), bindFaceMes.getUid());
        }
        if ("4".equals(pushMessage.getType())) {
            Gson gson = new Gson();
            PushUpDateBean pushUpDateBean = gson.fromJson(text, PushUpDateBean.class);
            int device_type_id = pushUpDateBean.getDevice_type_id();
            if (device_type_id == 4) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "lingxi.apk");
                    if (file.exists()) {
                        file.delete();
                    }

                    ToastMaster.shortToast(R.string.download);
                    DownloadUtils utils = new DownloadUtils(getInstance());
                    utils.downloadAPK(pushUpDateBean.getPackage_path(), "lingxi.apk");
                    Logger.e(file.getAbsolutePath());
                }
            }
        }
    }


    private static void getDate() {

        ApiFactory.downloadFeature(pushMessage.getMessageId(), pushMessage.getAppid(), pushMessage.getShopId(), User.get().getDeviceId(), pushMessage.getUid()).subscribe(new ProgressSubscriber<ApiResponse<List<Person>>>(getInstance()) {

            @Override
            public void onNext(final ApiResponse<List<Person>> listApiResponse) {
                final RealmResults<Person> uid = realm.where(Person.class).equalTo("uid", listApiResponse.getData().get(0).getUid()).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (int x = 0; x < uid.size(); x++) {
                            uid.deleteAllFromRealm();
                        }
                    }
                });
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (int x = 0; x < listApiResponse.getData().size(); x++) {
                            realm.copyToRealm(listApiResponse.getData().get(x));
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }


    public static PushMessage toJsonArray(String json) {
        try {
            pushMessage = new PushMessage();
            JSONObject object = new JSONObject(json);
            pushMessage.setType(object.getString("type"));
            pushMessage.setAppid(object.getString("appid"));
            pushMessage.setShopId(object.getString("shopId"));
            pushMessage.setUid(object.getString("uid"));
            pushMessage.setSendTime(object.getString("sendTime"));
            pushMessage.setMessageId(object.getString("messageId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pushMessage;
    }


    public void post(Runnable r) {
        mainThreadHandler.post(r);
    }

    public static SixCatApplication getInstance() {
        return (SixCatApplication) BaseApplication.getInstance();
    }


    private void intSpeak() {
        StringBuffer param = new StringBuffer();
        param.append("appid=5b3d9df5");
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(this, param.toString());//=号后面写自己应用的APPID
        Setting.setShowLog(false);
    }

}
