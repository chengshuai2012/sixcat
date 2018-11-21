package com.link.cloud;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ActivityManager {
    private static ActivityManager sInstance = new ActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;


    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
