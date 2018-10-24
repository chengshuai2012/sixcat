package com.link.cloud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.base.CardBaseAdapter;

import java.util.ArrayList;


/**
 * Created by ls on 2017/11/25.
 */

public class LessonConsumeAdapter extends CardBaseAdapter<LessonConsumeAdapter.LessonConsumeHolder> {
    private ArrayList<String> mData;
    private Context context;


    private onDateChangeListner onDateChangeListner;

    public void setOnDateChangeListner(LessonConsumeAdapter.onDateChangeListner onDateChangeListner) {
        this.onDateChangeListner = onDateChangeListner;
    }

    public interface onDateChangeListner {
        void change(int position);
    }

    public LessonConsumeAdapter(ArrayList<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public LessonConsumeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stack, null);
        LessonConsumeHolder holder = new LessonConsumeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (onDateChangeListner!=null){
            onDateChangeListner.change(position);
        }
        LessonConsumeHolder lessonConsumeHolder = (LessonConsumeHolder) holder;
        lessonConsumeHolder.name.setText(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class LessonConsumeHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phoneText;
        private TextView cardName;
        private TextView time;


        public LessonConsumeHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phoneText = (TextView) itemView.findViewById(R.id.phoneText);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}