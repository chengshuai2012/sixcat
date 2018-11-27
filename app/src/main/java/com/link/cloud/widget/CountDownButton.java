package com.link.cloud.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.link.cloud.R;


/**
 * Author: Wh1te
 * Date: 2016/10/18
 */

@SuppressLint("AppCompatCustomView")
public class CountDownButton extends Button {


    private String afterText = "下一步";
    private int ms = 10000;
    private Context context;

    public CountDownButton(Context context) {
        super(context);
        this.context = context;

    }

    private OnFinishListener onFinishListener;

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public interface OnFinishListener {
        //打电话的点击事件
        void onfinish();
    }


    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.timerbutton);


        afterText = typedArray.getString(R.styleable.timerbutton_afterText);

        ms = typedArray.getInt(R.styleable.timerbutton_ms, 10000);

        typedArray.recycle();

    }

    public void init(String afterText, int ms) {
        this.afterText = afterText;
        this.ms = ms;
    }

    public void startTimer() {
        new CountDownTimer(ms + 1000, 1000) {

            @Override
            public void onTick(long finish) {
                CountDownButton.this.setText(context.getResources().getString(R.string.next) + finish / 1000 + " s");
            }


            @Override
            public void onFinish() {
                CountDownButton.this.setText(afterText);
                if (onFinishListener != null) {
                    onFinishListener.onfinish();
                }
            }
        }.start();

    }

}