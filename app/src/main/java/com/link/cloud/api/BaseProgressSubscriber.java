package com.link.cloud.api;

import android.app.Dialog;
import android.content.Context;

import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.Utils;

//import com.zitech.framework.data.network.IContext;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public abstract class BaseProgressSubscriber<T> extends com.zitech.framework.data.network.subscribe.ProgressSubscriber<T> {

    public BaseProgressSubscriber(Context context) {
        super(context);
    }

    public BaseProgressSubscriber(Context context, Dialog dialog) {
        super(context, dialog);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ToastMaster.shortToast(Utils.parseError(e));
        dismissProgressDialog();
//        if(e.getMessage().equals("用户登录信息失效")){
//                Intent intent = new Intent(context, SplashActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
//        }

    }


}