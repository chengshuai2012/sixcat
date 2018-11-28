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
import com.link.cloud.network.bean.CardInfoBean;
import com.link.cloud.network.bean.LessonInfoResponse;
import com.zitech.framework.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseCardAdapter extends RecyclerView.Adapter<CourseCardAdapter.ContentViewHolder> {

    private List<CardInfoBean> data = new ArrayList<>();
    private Context activity;
    public static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    private LessonInfoResponse lessonInfoResponse;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onCheackClick(CardInfoBean lessonInfoBean, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseCardAdapter(Context activity, List<CardInfoBean> data) {
        this.activity = activity;
        this.data = data;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_card_layout, null);
        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, final int position) {
        final CardInfoBean cardInfoBean = data.get(position);
        if (isSelected.get(position) == true) {
            holder.rootLayout.setBackground(activity.getResources().getDrawable(R.drawable.border_choose_one_gradient_bg));
        } else {
            holder.rootLayout.setBackground(activity.getResources().getDrawable(R.drawable.border_gradient_course_se_bg));
        }
        holder.cardName.setText(activity.getResources().getString(R.string.class_name) + cardInfoBean.getCardName());
        holder.surplusTime.setText(activity.getResources().getString(R.string.class_name) + cardInfoBean.getCardTimes());
        holder.classTime.setText(activity.getResources().getString(R.string.card_number) + cardInfoBean.getCardNo());
        holder.cardType.setText(activity.getResources().getString(R.string.card_type) + cardInfoBean.getCardType());
        holder.termOfValidity.setText(activity.getResources().getString(R.string.term_of_validity) + cardInfoBean.getCardDate());
        ViewUtils.setOnClickListener(holder.rootLayout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onCheackClick(cardInfoBean, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout rootLayout;
        private TextView cardName;
        private TextView surplusTime;
        private TextView classTime;
        private TextView cardType;
        private TextView termOfValidity;

        public ContentViewHolder(View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
            surplusTime = (TextView) itemView.findViewById(R.id.surplusTime);
            classTime = (TextView) itemView.findViewById(R.id.classTime);
            cardType = (TextView) itemView.findViewById(R.id.cardType);
            termOfValidity = (TextView) itemView.findViewById(R.id.termOfValidity);
        }
    }
}

