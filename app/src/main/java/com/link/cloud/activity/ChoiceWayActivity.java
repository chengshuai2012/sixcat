package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.SixCatApplication;
import com.link.cloud.User;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.bean.Person;
import com.link.cloud.controller.SignedMemberContrller;
import com.link.cloud.utils.RxTimerUtil;
import com.link.cloud.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 作者：qianlu on 2018/10/24 19:20
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class ChoiceWayActivity extends BaseActivity implements SignedMemberContrller.SignedListener {


    private LinearLayout fingerLayout;
    private LinearLayout xiaochengxuLayout;
    private LinearLayout passwordLayout;
    private TextView buckButton;
    private EditText infoId;
    private RxTimerUtil rxTimerUtil;
    private List<Person> peoples;
    private boolean isScanning = false;
    private SignedMemberContrller signedMemberContrller;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signedMemberContrller = new SignedMemberContrller(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxTimerUtil.cancel();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.buckButton:
                finish();
                break;
            case R.id.xiaochengxuLayout:
                showActivity(FaceSignActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void initViews() {
        editText = (EditText) findViewById(R.id.infoId);

        fingerLayout = (LinearLayout) findViewById(R.id.fingerLayout);
        xiaochengxuLayout = (LinearLayout) findViewById(R.id.xiaochengxuLayout);
        passwordLayout = (LinearLayout) findViewById(R.id.passwordLayout);
        buckButton = (TextView) findViewById(R.id.buckButton);
        infoId = (EditText) findViewById(R.id.infoId);
        rxTimerUtil = new RxTimerUtil();
        Utils.setOnClickListener(buckButton, this);
        Utils.setOnClickListener(xiaochengxuLayout, this);
        if (android.hardware.Camera.getNumberOfCameras() != 0) {
            xiaochengxuLayout.setVisibility(View.VISIBLE);
        }
        setDate();
        showEditText();
    }


    private void showEditText() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    rxTimerUtil.timer(500, new RxTimerUtil.IRxNext() {
                        @Override
                        public void doNext(long number) {
                            String code = editText.getText().toString().trim();
                            if (!TextUtils.isEmpty(code)) {
                                signedMemberContrller.checkInByQrCode(code);
                            }

                        }
                    });
                }
            }
        });
    }

    private void setDate() {

        RealmResults<Person> users = realm.where(Person.class).findAll();
        peoples = new ArrayList<>();
        peoples.addAll(realm.copyFromRealm(users));

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                RealmResults<Person> users = realm.where(Person.class).findAll();
                peoples.clear();
                peoples.addAll(realm.copyFromRealm(users));
            }
        });
        showVenueu();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isScanning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isScanning = true;
    }


    private void showVenueu() {
        rxTimerUtil = new RxTimerUtil();
        rxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                System.out.println("number=" + number);
                if (isScanning) {
                    int state = SixCatApplication.getVenueUtils().getState();
                    if (state == 3 || state == 4) {
                        long startTime = System.currentTimeMillis();   //获取开始时间
                        String uid = SixCatApplication.getVenueUtils().identifyNewImg(peoples);
                        if (uid == null) {
                            Logger.e("贾工要的信息+Person:uid=get img failed, please try again ");
                            speak(getResources().getString(R.string.move_finger));
                        }
                        final Person uuid = realm.where(Person.class).equalTo("uid", uid).findFirst();
                        if (null != uuid) {
                            isScanning = false;
                            if (!isScanning) {
                                signedMemberContrller.signedMember(uuid.getUid(), "vein");
                            }
                            System.out.println("贾工要的信息+Person:uid=" + uuid.getUid());
                        } else {
                            speak(getResources().getString(R.string.move_finger));
                        }
                        long endTime = System.currentTimeMillis(); //获取结束时间
                        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
                    }
                }
            }

        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choiceway;
    }

    @Override
    public void modelMsg(int state, String msg) {

    }


    @Override
    public void signedMemberSuccess() {
        speak(getResources().getString(R.string.sign_successful));
        rxTimerUtil.timer(5000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                finish();
            }
        });
    }

    @Override
    public void signedMemberFail(String message) {
        speak(message);
    }

    @Override
    public void newWorkFail() {

    }
}
