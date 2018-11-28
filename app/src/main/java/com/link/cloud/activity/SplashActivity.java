package com.link.cloud.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.link.cloud.R;
import com.link.cloud.User;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.bean.FaceDateBean;
import com.link.cloud.bean.Person;
import com.link.cloud.controller.SplashController;
import com.link.cloud.network.response.AppUpdateInfoResponse;
import com.link.cloud.network.response.PageInfoResponse;
import com.link.cloud.network.response.RegisterResponse;
import com.link.cloud.utils.DownLoad;
import com.link.cloud.utils.DownloadUtils;
import com.link.cloud.utils.Utils;
import com.link.cloud.widget.SimpleStyleDialog;

import com.orhanobut.logger.Logger;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.ViewUtils;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.link.cloud.utils.Utils.getMac;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;


/**
 * 作者：qianlu on 2018/11/8 15:31
 * 邮箱：zar.l@qq.com
 */
public class SplashActivity extends BaseActivity implements SplashController.SplashListener {


    private SplashController splashController;
    private android.widget.RelativeLayout rootLayout;
    private int thisPage = 1;
    private PageInfoResponse pageInfoResponse;
    private ZLoadingDialog dialog;
    private RegisterResponse response;


    @Override
    protected void initViews() {
        showLoading();
        speak("");
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        splashController = new SplashController(this, this);
        splashController.getDeviceID(Utils.getMD5(getMac()).toUpperCase(), 2);
        ViewUtils.setOnClickListener(rootLayout, this);

    }

    private void bindAccount() {
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        String deviceid = User.get().getDeviceId();
        pushService.bindAccount(deviceid, new CommonCallback() {
            @Override
            public void onSuccess(String s) {

                splashController.syncUserFeatureCount(response.getDeviceId());
            }

            @Override
            public void onFailed(String s, String s1) {
                System.out.println("String1=" + s + "String2=" + s1);
                speak("初始化失败,请点击屏幕重试");
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rootLayout:
                showLoading();
                splashController.getDeviceID(Utils.getMD5(getMac()).toUpperCase(), 2);
                break;
        }
    }

    private void showLoading() {
        final RealmResults<Person> cabinetInfoRealmList = realm.where(Person.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                cabinetInfoRealmList.deleteAllFromRealm();
            }
        });
        if (dialog == null)
            dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(DOUBLE_CIRCLE)
                .setCanceledOnTouchOutside(false)
                .setLoadingColor(Color.parseColor("#D71718"))
                .setHintText("数据同步中")
                .setHintTextSize(32) // 设置字体大小
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDialogBackgroundColor(Color.parseColor("#cc111111")) // 设置背景色
                .show();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void getDeviceIdSuccess(RegisterResponse registerResponse) {
        this.response = registerResponse;
        bindAccount();
    }

    @Override
    public void getDeviceIdFail(String message) {
        dialog.dismiss();
        speak("初始化失败,请点击屏幕重试");
    }

    @Override
    public void getPageInfoSuccess(PageInfoResponse response) {
        pageInfoResponse = response;
        if (response.getPageCount() > 0) {
            getDate(thisPage);
        } else {
            skipActivity(MainActivity.class);
            dialog.dismiss();
            speak("初始化成功");
        }
    }

    @Override
    public void getPageInfoFail(String message) {
        dialog.dismiss();
        speak("初始化失败,请点击屏幕重试");
    }

    @Override
    public void getPageDateSuccess(final List<Person> date) {
        thisPage++;
        final RealmResults<Person> cabinetInfoRealmList = realm.where(Person.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(date);
            }
        });
        if (thisPage <= pageInfoResponse.getPageCount()) {
            getDate(thisPage);
        } else {
            speak("初始化成功");
            skipActivity(MainActivity.class);
            dialog.dismiss();
        }

    }

    @Override
    public void getPageDateFail(String message) {
        speak("初始化失败,请点击屏幕重试");
    }

    @Override
    public void newWorkFail() {
        dialog.dismiss();
        final SimpleStyleDialog simpleStyleDialog = new SimpleStyleDialog(this, getResources().getString(R.string.net_error));
        simpleStyleDialog.setCancelButtonText("取消");
        simpleStyleDialog.setOnPositiveButtonClickListener(new SimpleStyleDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                simpleStyleDialog.dismiss();
            }
        });
        simpleStyleDialog.show();
    }

    @Override
    public void syncUserFacePagesSuccess(final List<FaceDateBean> dateBeans) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        for (int x = 0; x < dateBeans.size(); x++) {
            final int finalX = x;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    DownLoad.download(dateBeans.get(finalX).getFaceUrl(), dateBeans.get(finalX).getUid());
                }
            };
            service.execute(runnable);
        }
    }

    @Override
    public void syncUserFaceFail(String message) {
        speak("初始化失败,请点击屏幕重试");
    }

    @Override
    public void getUpdateInfoSuccess(AppUpdateInfoResponse response) {
        int version = Utils.getVersionCode(this);
        if(version<response.getPackage_version()){
            downLoadApk(response.getPackage_path());
        }
    }

    private void downLoadApk(String downloadurl) {
        // 判断当前用户是否有sd卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "lingxi.apk");
            if (file.exists()) {
                file.delete();
            }
            ToastMaster.longToast(R.string.download);
            DownloadUtils utils = new DownloadUtils(this);
            utils.downloadAPK(downloadurl, "lingxi.apk");
            Logger.e(file.getAbsolutePath());
        }
    }

    public void getDate(int thisPage) {
        splashController.syncUserFeaturePages(response.getDeviceId(), thisPage);
    }

    @Override
    public void modelMsg(int state, String msg) {

    }
}
