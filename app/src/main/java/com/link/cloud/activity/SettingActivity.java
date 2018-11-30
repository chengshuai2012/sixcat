package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.User;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.bean.Person;
import com.link.cloud.controller.SynchroDataController;
import com.link.cloud.network.response.PageInfoResponse;
import com.link.cloud.utils.RxTimerUtil;
import com.link.cloud.utils.Utils;
import com.zitech.framework.utils.ViewUtils;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.List;

import io.realm.Realm;

import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 作者：qianlu on 2018/11/30 14:06
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class SettingActivity  extends BaseActivity implements SynchroDataController.SynchroDataListener {
    private android.widget.TextView versionName;
    private android.widget.TextView macName;
    private android.widget.TextView back_system_main;
    private android.widget.TextView deviceId;
    private android.widget.Button sureButton;

    private android.widget.Button restartApp;
    private android.widget.Button synchroData;
    private String gpiostr = "1067";

    private RxTimerUtil rxTimerUtil;
    private String deviceType;
    private SynchroDataController controller;
    private int thisPage = 1;
    private PageInfoResponse pageInfoResponse;
    private ZLoadingDialog dialog;


    @Override
    protected void initViews() {
        controller = new SynchroDataController(this, this);
        deviceType = android.os.Build.MODEL;

        versionName = (TextView) findViewById(R.id.versionName);
        macName = (TextView) findViewById(R.id.macName);
        sureButton = (Button) findViewById(R.id.sureButton);
        restartApp = (Button) findViewById(R.id.restartApp);
        back_system_main = (Button) findViewById(R.id.back_system_main);
        deviceId = (TextView) findViewById(R.id.deviceId);
        synchroData = (Button) findViewById(R.id.synchroData);

        macName.setText(getResources().getString(R.string.mac_id) + Utils.getMac());
        versionName.setText(getResources().getString(R.string.version_name) + Utils.getVersionName(this));
        deviceId.setText(getResources().getString(R.string.device_id) + User.get().getDeviceId());
        ViewUtils.setOnClickListener(sureButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewUtils.setOnClickListener(restartApp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SettingActivity.this, SplashActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        ViewUtils.setOnClickListener(synchroData, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                controller.syncUserFeatureCount(User.get().getDeviceId());
            }
        });

        ViewUtils.setOnClickListener(back_system_main, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_MAIN, null);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void modelMsg(int state, String msg) {

    }


    @Override
    public void getPageInfoSuccess(PageInfoResponse response) {
        pageInfoResponse = response;
        if (response.getPageCount() > 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.deleteAll();
                }
            });
            getDate(thisPage);
        } else {
            speak("数据同步成功");
        }
    }

    public void getDate(int thisPage) {
        controller.syncUserFeaturePages(User.get().getDeviceId(), thisPage);
    }

    @Override
    public void getPageInfoFail(String message) {
        dialog.dismiss();
        speak("数据同步失败");
    }


    private void showLoading() {
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
    public void getPageDateSuccess(final List<Person> date) {
        thisPage++;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realms) {
                realms.copyToRealm(date);
            }
        });
        if (thisPage <= pageInfoResponse.getPageCount()) {
            getDate(thisPage);
        } else {
            thisPage = 1;
            speak("数据同步成功");
            dialog.dismiss();
        }
    }

    @Override
    public void getPageDateFail(String message) {
        dialog.dismiss();
    }

    @Override
    public void newWorkFail() {
        dialog.dismiss();
    }
}
