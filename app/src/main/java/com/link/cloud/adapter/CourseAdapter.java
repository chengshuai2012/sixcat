package com.link.cloud.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.network.bean.LessonInfoBean;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.zitech.framework.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ContentViewHolder> {

    private List<LessonInfoBean> data = new ArrayList<>();
    private Context activity;
    public static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    private LessonInfoResponse lessonInfoResponse;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onCheackClick(LessonInfoBean lessonInfoBean, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseAdapter(Context activity, LessonInfoResponse lessonInfoResponse) {
        this.activity = activity;
        this.data = lessonInfoResponse.getLessonInfo();
        this.lessonInfoResponse = lessonInfoResponse;
        init(false, -1);
    }


    // 初始化 设置所有item都为未选择
    public void init(boolean isSelecter, int position) {
        for (int i = 0; i < data.size(); i++) {
            if (i == position) {
                isSelected.put(i, true);
            } else {
                isSelected.put(i, isSelecter);
            }
        }
    }


    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_layout, null);
        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, final int position) {
        final LessonInfoBean infoBean = data.get(position);
        if (isSelected.get(position) == true) {
            holder.rootLayout.setBackground(activity.getResources().getDrawable(R.drawable.border_gradient_course_bg));
        } else {
            holder.rootLayout.setBackground(activity.getResources().getDrawable(R.drawable.border_gradient_course_se_bg));
        }
        holder.className.setText(infoBean.getLessonName());
        holder.classTime.setText(activity.getResources().getString(R.string.class_time) + infoBean.getLessonDate());
        holder.coachName.setText(activity.getResources().getString(R.string.souke_coach) + lessonInfoResponse.getCoach());

        ViewUtils.setOnClickListener(holder.rootLayout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onCheackClick(infoBean, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView className;
        private TextView coachName;
        private TextView classTime;
        private LinearLayout rootLayout;

        public ContentViewHolder(View itemView) {
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.className);
            coachName = (TextView) itemView.findViewById(R.id.coachName);
            classTime = (TextView) itemView.findViewById(R.id.classTime);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }
}

