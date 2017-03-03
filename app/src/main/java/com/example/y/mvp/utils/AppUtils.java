package com.example.y.mvp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.y.mvp.BuildConfig;
import com.example.y.mvp.data.Constant;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/7/25.
 */
public class AppUtils extends Application {

    private List<Activity> activityList = new ArrayList<>();

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SpfUtils.init(this);
        KLog.init(BuildConfig.LOG_DEBUG, Constant.K_LOG);
    }


    public static AppUtils getInstance() {
        return (AppUtils) context;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        RxUtil.unsubscribe();
    }

    public void refreshAllActivity() {
        for (Activity activity : activityList) {
            activity.recreate();
        }
    }
}