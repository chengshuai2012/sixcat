package com.link.cloud.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.adapter.CourseAdapter;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.network.bean.LessonInfoBean;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.widget.CountDownButton;
import com.zitech.framework.utils.ViewUtils;

/**
 * 作者：qianlu on 2018/11/20 15:38
 * 邮箱：zar.l@qq.com
 */
public class CourseFragment extends BaseFragment {

    private LessonInfoResponse response;
    private android.support.v7.widget.RecyclerView recycle;
    private android.widget.TextView backButton;
    private com.link.cloud.widget.CountDownButton nextButton;


    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        response = (LessonInfoResponse) getArguments().getSerializable(Constants.FragmentExtra.BEAN);
        initView(contentView);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_course;
    }


    private void initView(View contentView) {

        recycle = (RecyclerView) contentView.findViewById(R.id.recycle);
        recycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        final CourseAdapter courseAdapter = new CourseAdapter(getActivity(), response);
        recycle.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onCheackClick(LessonInfoBean lessonInfoBean, int position) {
                courseAdapter.init(false, position);
                courseAdapter.notifyDataSetChanged();
            }
        });
        backButton = (TextView) contentView.findViewById(R.id.backButton);
        nextButton = (CountDownButton) contentView.findViewById(R.id.nextButton);
        nextButton.startTimer();
        nextButton.setOnFinishListener(new CountDownButton.OnFinishListener() {
            @Override
            public void onfinish() {
                RxBus.get().post(new Events.finish());
            }
        });
        ViewUtils.setOnClickListener(backButton, this);
        ViewUtils.setOnClickListener(nextButton, this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                RxBus.get().post(new Events.BackAndClearnView());
                break;
            case R.id.nextButton:
                RxBus.get().post(new Events.NextView());
                break;

        }
    }
}
