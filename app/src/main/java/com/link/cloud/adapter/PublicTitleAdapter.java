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
import com.zitech.framework.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class PublicTitleAdapter extends RecyclerView.Adapter<PublicTitleAdapter.ContentViewHolder> {
    private List<String> data = new ArrayList<>();
    private Context activity;
    private int mLocation = 0;


    public PublicTitleAdapter(Context activity) {
        this.activity = activity;
    }

    public void setDate(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void next() {
        mLocation++;
        notifyDataSetChanged();
    }

    public void last() {
        mLocation--;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_layout, null);
        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        final String contentText = data.get(position);
        holder.contentText.setText(contentText);
        if (mLocation >= 0) {
            if (position == mLocation) {
                holder.contentText.setBackground(activity.getResources().getDrawable(R.drawable.border_red_gradient_buy_bg));
            } else {
                holder.contentText.setBackground(activity.getResources().getDrawable(R.drawable.border_lesson));
            }
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(holder.contentText.getLayoutParams());
        if (position == 0) {
            lp.setMargins(0, 0, 0, 0);
        }else {
            lp.setMargins(ViewUtils.getDimenPx(R.dimen.w30), 0, 0, 0);
        }
        holder.contentText.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void first() {
        mLocation=0;
        notifyDataSetChanged();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView contentText;


        public ContentViewHolder(View itemView) {
            super(itemView);
            contentText = itemView.findViewById(R.id.contentText);
        }
    }
}

