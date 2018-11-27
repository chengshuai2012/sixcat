package com.link.cloud.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.R;
import com.link.cloud.SixCatApplication;
import com.link.cloud.utils.TTSUtils;
import com.link.cloud.utils.Utils;
import com.link.cloud.utils.Venueutils;
import com.link.cloud.widget.SimpleStyleDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zitech.framework.utils.ViewUtils;

import io.realm.Realm;
import rx.functions.Action1;

/**
 * Created by OFX002 on 2018/9/20.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, Venueutils.VenueCallBack{

    private SimpleStyleDialog denyDialog;
    public Realm realm;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        SixCatApplication.getVenueUtils().initVenue(this, this, false);
        RxBus.get().register(this);
        realm = Realm.getDefaultInstance();
        initViews();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        RxBus.get().unregister(this);
        try {
            SixCatApplication.getVenueUtils().unBindService();
        }catch (Exception e){

        }
    }


    protected abstract void initViews();

    protected abstract int getLayoutId();

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onResume() {
        super.onResume();

    }

    protected void onPause() {
        super.onPause();

    }

//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) { // 第一次System.currentTimeMillis()无论何时调用，差值肯定大于2000
//
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//



    public void speak(String message) {
        TTSUtils.getInstance().speak(message);
    }

    public void speakMust(String message){
        TTSUtils.getInstance().speakMust(message);
    }


    /**
     * @param cls 目标activity
     *            跳转并finish当前activity
     * @throws ActivityNotFoundException
     */
    public void skipActivity(Class<?> cls) {
        showActivity(cls);
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);
        finish();
    }

    /**
     * @param cls
     * @param extras
     */
    public void skipActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(this, cls);
        startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);
        finish();
    }

    public void showActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        super.startActivityForResult(intent, requestCode);
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);
    }

    public void showActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        super.startActivityForResult(intent, requestCode);
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);

    }

    public void showActivity(Class<?> cls) {
        Intent intent = new Intent();

        
        intent.setClass(this, cls);
        super.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);

    }

    public void showActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(extras);
        super.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);
    }

    @Override
    public void finish() {
        super.finish();
        applyCloseTransition();
    }

    protected void applyCloseTransition() {
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);
    }

    @Override
    public void onClick(View v) {

    }

    //请求权限
    protected void requestRxPermissions(final String denyText, final String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissions).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) {
                    onPermissionGranted(permissions);
                } else {
                    showDeniedDialog(denyText, permissions);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    protected void onPermissionGranted(String... permissions) {

    }


    private void showDeniedDialog(final String denyText, final String... permissions) {
        if (denyDialog == null) {
            denyDialog = new SimpleStyleDialog(this, denyText);
            denyDialog.setCancelButtonText("取消");
            denyDialog.setOnPositiveButtonClickListener(new SimpleStyleDialog.OnPositiveButtonClickListener() {
                @Override
                public void onClick(Dialog dialog) {
                    requestRxPermissions(denyText, permissions);
                }
            });
            denyDialog.setPositiveButtonText("去开启");
        } else {
            denyDialog.setContent(denyText);
        }
        if (!denyDialog.isShowing()) {
            denyDialog.show();
        }
    }

    public Fragment showFragment(Class<? extends Fragment> fragmentClass) {
        return Utils.replace(getSupportFragmentManager(), R.id.content_frame, fragmentClass);
    }

    public Fragment showNewFragment(Class<? extends Fragment> fragmentClass) {
        return Utils.replaceNew(getSupportFragmentManager(), R.id.content_frame, fragmentClass);
    }

    public Fragment showFragment(Class<? extends Fragment> fragmentClass,Bundle bundle) {
        return Utils.replace(getSupportFragmentManager(),fragmentClass, R.id.content_frame, bundle);
    }

    public Fragment showNewFragment(Class<? extends Fragment> fragmentClass,Bundle bundle) {
        return Utils.replaceNew(getSupportFragmentManager(),fragmentClass, R.id.content_frame, bundle);
    }



    public Context getContext() {
        return this;
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) return;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
