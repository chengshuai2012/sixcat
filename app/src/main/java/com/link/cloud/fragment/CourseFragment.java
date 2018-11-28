package com.link.cloud.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Constants;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.activity.EliminateClassActivity;
import com.link.cloud.adapter.CourseAdapter;
import com.link.cloud.adapter.CourseCardAdapter;
import com.link.cloud.adapter.GridSpacingItemDecoration;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.controller.CourseContrller;
import com.link.cloud.network.bean.CardInfoBean;
import com.link.cloud.network.bean.LessonInfoBean;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.link.cloud.widget.CountDownButton;
import com.zitech.framework.utils.ViewUtils;

/**
 * 作者：qianlu on 2018/11/20 15:38
 * 邮箱：zar.l@qq.com
 */
public class CourseFragment extends BaseFragment  {

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
        recycle = contentView.findViewById(R.id.recycle);
        recycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycle.addItemDecoration(new GridSpacingItemDecoration(2, ViewUtils.getDimen(R.dimen.w20), false));
        final CourseAdapter courseAdapter = new CourseAdapter(getActivity(), response);
        recycle.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onCheackClick(final LessonInfoBean lessonInfoBean, int position) {
                courseAdapter.init(false, position);
                courseAdapter.notifyDataSetChanged();
                final CourseCardAdapter courseCardAdapter = new CourseCardAdapter(getActivity(), lessonInfoBean.getCardInfo());
                recycle.setAdapter(courseCardAdapter);
                ((BaseActivity) getActivity()).speak("请选择卡号");
                courseCardAdapter.setOnItemClickListener(new CourseCardAdapter.OnItemClickListener() {
                    @Override
                    public void onCheackClick(final CardInfoBean cardInfoBean, int position) {
                        courseCardAdapter.init(false, position);
                        courseCardAdapter.notifyDataSetChanged();
                        ((EliminateClassActivity) getActivity()).selectLesson(lessonInfoBean, cardInfoBean);
                    }
                });
            }
        });
        backButton = contentView.findViewById(R.id.backButton);
        nextButton = contentView.findViewById(R.id.nextButton);
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
                getActivity().finish();
                break;
            case R.id.nextButton:
                RxBus.get().post(new Events.NextView());
                break;

        }
    }

}
