package com.example.y.mvp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.y.mvp.data.Constant;
import com.example.y.mvp.utils.AppUtils;
import com.example.y.mvp.utils.SpfUtils;
import com.example.y.mvp.utils.swipeback.SwipeBackActivity;
import com.example.y.mvp.utils.swipeback.SwipeBackLayout;

/**
 * by y on 2016/4/28.
 */
public abstract class BaseActivity extends SwipeBackActivity {

    private static Activity activity;
    protected SwipeBackLayout swipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        activity = this;
        setContentView(getLayoutId());
        initById();
        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        initCreate(savedInstanceState);
        setStatusBar();
        AppUtils.getInstance().addActivity(activity);
    }

    protected void initTheme() {
        if (SpfUtils.isTheme(Constant.DAY)) {
            setTheme(Constant.DAY_STYLES);
        } else {
            setTheme(Constant.NIGHT_STYLES);
        }
    }

    protected void setStatusBar() {
    }

    public static Activity getActivity() {
        return activity;
    }

    <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();
}
