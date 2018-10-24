package com.link.cloud.widget;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.link.cloud.base.CardBaseAdapter;

import java.util.List;

public class SwipeCardCallBack<T> extends ItemTouchHelper.SimpleCallback {
    private List<T> mDatas;
    private CardBaseAdapter adapter;
    private RecyclerView mRv;

    public SwipeCardCallBack(List<T> mDatas, CardBaseAdapter adapter, RecyclerView mRv) {
        super(0,
                 ItemTouchHelper.UP | ItemTouchHelper.DOWN
        );
        this.mDatas = mDatas;
        this.adapter = adapter;
        this.mRv = mRv;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        T remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
