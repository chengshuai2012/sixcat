package com.link.cloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.link.cloud.Constants;
import com.link.cloud.R;
import com.link.cloud.User;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.bean.FaceDateBean;
import com.link.cloud.network.ApiFactory;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.network.response.AppUpdateInfoResponse;
import com.link.cloud.network.subscribe.ProgressSubscriber;
import com.link.cloud.utils.DownLoad;
import com.link.cloud.utils.DownloadUtils;
import com.link.cloud.utils.Utils;
import com.link.cloud.widget.InputPassWordDialog;
import com.orhanobut.logger.Logger;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.ViewUtils;

import java.io.File;
import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import rx.functions.Action1;

@SuppressLint("Registered")
public class MainActivity extends BaseActivity {


    private FrameLayout classBeginsLayout;
    private FrameLayout classeLiminateLayout;
    private TextView addFaceButton;
    private TextView addFingerButton;
    private FrameLayout signInLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        appUpdateInfo(User.get().getDeviceId());

        if (android.hardware.Camera.getNumberOfCameras() != 0) {
            syncUserFacePages(User.get().getDeviceId());
        }
    }


    public void syncUserFacePages(String deviceId) {
        ApiFactory.syncUserFacePages(deviceId).subscribe(new Action1<ApiResponse<List<FaceDateBean>>>() {
            @Override
            public void call(final ApiResponse<List<FaceDateBean>> response) {
                ExecutorService service = Executors.newFixedThreadPool(1);
                for (int x = 0; x < response.getData().size(); x++) {
                    final int finalX = x;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            DownLoad.download(response.getData().get(finalX).getFaceUrl(), response.getData().get(finalX).getUid());
                        }
                    };
                    service.execute(runnable);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

    }


    @Override
    protected void initViews() {

    }

    public void appUpdateInfo(String deviceId) {
        ApiFactory.appUpdateInfo(deviceId).subscribe(new ProgressSubscriber<ApiResponse<AppUpdateInfoResponse>>(this) {
            @Override
            public void onNext(ApiResponse<AppUpdateInfoResponse> response) {
                int version = Utils.getVersionCode(MainActivity.this);
                if (version < response.getData().getPackage_version()) {
                    downLoadApk(response.getData().getPackage_path());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        classBeginsLayout = (FrameLayout) findViewById(R.id.classBeginsLayout);
        classeLiminateLayout = (FrameLayout) findViewById(R.id.classeLiminateLayout);
        addFaceButton = (TextView) findViewById(R.id.addFaceButton);
        addFingerButton = (TextView) findViewById(R.id.addFingerButton);
        signInLayout = (FrameLayout) findViewById(R.id.signInLayout);
        TextView title = (TextView) findViewById(R.id.title);

        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSet();
                return false;
            }
        });

        ViewUtils.setOnClickListener(addFaceButton, this);

        ViewUtils.setOnClickListener(signInLayout, this);
        ViewUtils.setOnClickListener(addFingerButton, this);
        ViewUtils.setOnClickListener(classBeginsLayout, this);
        classeLiminateLayout.setOnClickListener(this);

        ViewUtils.setOnClickListener(classeLiminateLayout, this);
        requestRxPermissions(getString(R.string.open_read_error), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (android.hardware.Camera.getNumberOfCameras() != 0) {
            addFaceButton.setVisibility(View.VISIBLE);
            requestRxPermissions(getString(R.string.open_camera_error), Manifest.permission.CAMERA);
        }

    }



    private void showSet() {
        final InputPassWordDialog   inputPassWordDialog = new InputPassWordDialog(this);
        inputPassWordDialog.show();
        inputPassWordDialog.setCheakListener(new InputPassWordDialog.CheakListener() {
            @Override
            public void inputCheakSuccess() {
                speak(getResources().getString(R.string.psw_cheak_success));
                showActivity(SettingActivity.class);
                inputPassWordDialog.dismiss();
            }

            @Override
            public void inputCheakFail() {
                speak(getResources().getString(R.string.psw_cheak_fail));
                inputPassWordDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.addFaceButton:
                speak("请输入手机号码");
                Bundle faceBundle = new Bundle();
                faceBundle.putString(Constants.ActivityExtra.TYPE, "FACE");
                showActivity(BindActivity.class, faceBundle);
                break;
            case R.id.addFingerButton:
                speak("请输入手机号码");
                Bundle fingerBundle = new Bundle();
                fingerBundle.putString(Constants.ActivityExtra.TYPE, "FINGER");
                showActivity(BindActivity.class, fingerBundle);
                break;
            case R.id.classeLiminateLayout:
                showActivity(EliminateClassActivity.class);
                break;

            case R.id.signInLayout:
                showActivity(ChoiceWayActivity.class);
                break;
            case R.id.classBeginsLayout:
                showActivity(AttendClassActivity.class);
                break;
        }
    }

    @Override
    public void modelMsg(int state, String msg) {

    }
}
