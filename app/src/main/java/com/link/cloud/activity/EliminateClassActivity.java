package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.SixCatApplication;
import com.link.cloud.adapter.PublicTitleAdapter;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.bean.Person;
import com.link.cloud.controller.EliminateContrller;
import com.link.cloud.fragment.CheackFingerFragment;
import com.link.cloud.fragment.CourseFragment;
import com.link.cloud.fragment.MemberCardInfoFragment;
import com.link.cloud.network.bean.CardInfoBean;
import com.link.cloud.network.bean.LessonInfoBean;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.network.response.ApiResponse;
import com.link.cloud.utils.RxTimerUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：qianlu on 2018/10/22 17:50
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class EliminateClassActivity extends BaseActivity implements EliminateContrller.EliminateListener {

    private RecyclerView recycle;
    private RxTimerUtil rxTimerUtil;
    private boolean isScanning = false;
    private Person coachPerson;
    private Person studentPerson;
    private EliminateContrller eliminateContrller;
    private PublicTitleAdapter publicTitleAdapter;
    private List<Person> peoples;


    @Override
    protected void initViews() {
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eliminateclass;
    }

    private void initView() {
        speak(getResources().getString(R.string.coach_finger));
        recycle = (RecyclerView) findViewById(R.id.recycle);
        eliminateContrller = new EliminateContrller(this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle.setLayoutManager(layoutManager);
        publicTitleAdapter = new PublicTitleAdapter(this);
        String[] bindArray = getResources().getStringArray(R.array.Eliminate_Array);
        List<String> date = new ArrayList<>();
        for (String dateInfo : bindArray) {
            date.add(dateInfo);
        }
        publicTitleAdapter.setDate(date);
        recycle.setAdapter(publicTitleAdapter);
        recycle.setNestedScrollingEnabled(false);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FragmentExtra.TYPE, getResources().getString(R.string.coach_finger));
        showFragment(CheackFingerFragment.class, bundle);
        RealmResults<Person> users = realm.where(Person.class).findAll();
        peoples = new ArrayList<>();
        peoples.addAll(realm.copyFromRealm(users));
        showVenueu();
    }


    private void showVenueu() {
        rxTimerUtil = new RxTimerUtil();
        rxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                System.out.println("number=" + number);
                if (isScanning) {
                    int state = SixCatApplication.getVenueUtils().getState();
                    if (state == 3 ) {
                        long startTime = System.currentTimeMillis();   //获取开始时间
                        String uid = SixCatApplication.getVenueUtils().identifyNewImg(peoples);
                        if (uid == null) {
                            Logger.e("贾工要的信息+Person:uid=get img failed, please try again ");
                            speak(getResources().getString(R.string.move_finger));
                        }
                        final Person uuid = realm.where(Person.class).equalTo("uid", uid).findFirst();
                        if (null != uuid) {
                            System.out.println("贾工要的信息+Person:uid=" + uuid.getUid());
                            if (coachPerson == null) {
                                if (uuid.getUserType() == 2) {
                                    coachPerson = uuid;
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constants.FragmentExtra.TYPE, getResources().getString(R.string.student_finger));
                                    showNewFragment(CheackFingerFragment.class, bundle);
                                    publicTitleAdapter.next();
                                    speakMust(getResources().getString(R.string.student_finger));
                                } else {
                                    speak(getResources().getString(R.string.coach_finger));
                                }
                            } else {
                                if (uuid.getUserType() == 1) {
                                    studentPerson = uuid;
                                    if (studentPerson != null && coachPerson != null) {
                                        rxTimerUtil.cancel();
                                        eliminateContrller.getLessonInfo(2, studentPerson.getUid(), coachPerson.getUid());
                                        System.gc();
                                    }
                                } else {
                                    speak(getResources().getString(R.string.student_finger));
                                }
                            }

                        } else {
                            speak(getResources().getString(R.string.cheack_fail));
                        }
                        long endTime = System.currentTimeMillis(); //获取结束时间
                        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
                    }
                }
            }

        });
    }


    public void selectLesson(LessonInfoBean lessonInfoBean, CardInfoBean cardInfoBean) {
        eliminateContrller.selectLesson(2, lessonInfoBean.getLessonId(), studentPerson.getUid(), coachPerson.getUid(), "", cardInfoBean.getCardNo(), 1);
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void BackView(Events.BackView event) {
        finish();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void BackAndClearnView(Events.BackAndClearnView event) {
        isScanning = true;
        coachPerson = null;
        studentPerson = null;
        publicTitleAdapter.selectPosition(0);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void NextView(Events.NextView event) {
        isScanning = true;
        coachPerson = null;
        studentPerson = null;
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void onfinish(Events.finish event) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxTimerUtil.cancel();
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

    @Override
    public void modelMsg(int state, String msg) {
    }

    @Override
    public void getLessonInfoSuccess(LessonInfoResponse response) {
        isScanning = false;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.FragmentExtra.BEAN, response);
        showNewFragment(CourseFragment.class, bundle);
        publicTitleAdapter.selectPosition(2);

    }

    @Override
    public void getLessonInfoFail(String message) {
        speak(message);
        finish();
    }

    @Override
    public void selectLessonSuccess(ApiResponse response) {
        publicTitleAdapter.next();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.FragmentExtra.BEAN, studentPerson);
        showNewFragment(MemberCardInfoFragment.class, bundle);
        rxTimerUtil.timer(10000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                finish();
            }
        });
    }

    @Override
    public void selectLessonFail(String message) {
            speak(message);
    }

    @Override
    public void newWorkFail() {
        speak(getResources().getString(R.string.net_error));
    }
}
