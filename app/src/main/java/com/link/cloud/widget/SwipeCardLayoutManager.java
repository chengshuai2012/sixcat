package com.link.cloud.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ls on 2017/11/25.
 */

public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {
    Context context;
    int TRANS_Y_GAP;
    public SwipeCardLayoutManager(Context context){
        TRANS_Y_GAP= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,25,
                context.getResources().getDisplayMetrics());
    }
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        int itemCount=getItemCount();
        int bottomPosition;
        if(itemCount<3){
            bottomPosition=0;

        }else{
            bottomPosition=itemCount-3;
        }
        for(int i=bottomPosition;i<itemCount;i++){
            View view=recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view,0,0);
            int widthSpace=getWidth()-getDecoratedMeasuredWidth(view);
            int heightSpace=getWidth()-getDecoratedMeasuredHeight(view);
            //摆放cardView
            layoutDecorated(view,
                    widthSpace/2,
                    heightSpace/4,
                    widthSpace/2+getDecoratedMeasuredWidth(view),
                    heightSpace/4+getDecoratedMeasuredHeight(view));
            //层叠效果--Scale+TranslationY
            //层级的位置关系1/2/3
            int level=itemCount-i-1;
            if(level>0){
                if(level<CardConfig.MAX_SHOW_COUNT){
                    view.setTranslationY(-TRANS_Y_GAP*level);
                    view.setScaleX(1-CardConfig.SCALE_GAP*level);
                    view.setScaleY(1-CardConfig.SCALE_GAP*level);
                }
            }else {
                view.setTranslationY(-TRANS_Y_GAP*level);
                view.setScaleX(1-CardConfig.SCALE_GAP*level);
                view.setScaleY(1-CardConfig.SCALE_GAP*level);
            }
        }



    }
}
