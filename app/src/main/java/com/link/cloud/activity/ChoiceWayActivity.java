package com.link.cloud.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.link.cloud.R;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.utils.RxTimerUtil;
import com.link.cloud.utils.Utils;

/**
 * 作者：qianlu on 2018/10/24 19:20
 * 邮箱：zar.l@qq.com
 */
@SuppressLint("Registered")
public class ChoiceWayActivity extends BaseActivity {


    private LinearLayout fingerLayout;
    private LinearLayout xiaochengxuLayout;
    private LinearLayout passwordLayout;
    private TextView buckButton;
    private EditText infoId;
    private RxTimerUtil rxTimerUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxTimerUtil.cancel();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.buckButton:
                finish();
                break;
        }
    }

    @Override
    protected void initViews() {
        fingerLayout = (LinearLayout) findViewById(R.id.fingerLayout);
        xiaochengxuLayout = (LinearLayout) findViewById(R.id.xiaochengxuLayout);
        passwordLayout = (LinearLayout) findViewById(R.id.passwordLayout);
        buckButton = (TextView) findViewById(R.id.buckButton);
        infoId = (EditText) findViewById(R.id.infoId);
        rxTimerUtil = new RxTimerUtil();
        showEditText();
        Utils.setOnClickListener(buckButton, this);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choiceway;
    }

    @Override
    public void modelMsg(int state, String msg) {

    }

    private void showEditText() {
        infoId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(infoId.getText().toString().trim())) {
                    rxTimerUtil.timer(500, new RxTimerUtil.IRxNext() {
                        @Override
                        public void doNext(long number) {
                            String code = infoId.getText().toString().trim();
                            if (!TextUtils.isEmpty(code)) {

                            }

                        }
                    });
                }
            }
        });
    }

}
