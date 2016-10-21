package com.example.y.mvp.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.example.y.mvp.R;
import com.example.y.mvp.activity.TransitionActivity;
import com.example.y.mvp.data.Constant;
import com.example.y.mvp.utils.ActivityUtils;
import com.example.y.mvp.utils.CacheUitls;
import com.example.y.mvp.utils.DiaLogUtils;
import com.example.y.mvp.utils.RxUtil;
import com.example.y.mvp.utils.SpfUtils;
import com.example.y.mvp.widget.MImageView;

import java.util.List;


/**
 * by 12406 on 2016/6/16.
 */
public class MenuItemAdapter extends BaseRecyclerViewAdapter<String>
        implements RxUtil.RxBinding {


    private ImageView imageView;

    public MenuItemAdapter(List<String> mDatas) {
        super(mDatas);
    }

    @Override
    protected void getHeadLayoutId(View headView) {
        super.getHeadLayoutId(headView);
        imageView = (ImageView) headView.findViewById(R.id.iv);
        MImageView headImage = (MImageView) headView.findViewById(R.id.head_image);
        if (SpfUtils.isTheme(Constant.DAY)) {
            getImageViewDay();
        } else {
            getImageViewNight();
        }
        RxUtil.clicks(imageView, this);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaLogUtils.clearSql();
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.menu_item;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, String data) {
        holder.setTextView(R.id.tv_menu_item, data);
    }

    private void getImageViewDay() {
        imageView.setBackgroundResource(R.drawable.day);
    }

    private void getImageViewNight() {
        imageView.setBackgroundResource(R.drawable.night);
    }

    @Override
    public void clicks() {
        Activity activity = (Activity) context;
        if (SpfUtils.isTheme(Constant.DAY)) {
            activity.setTheme(Constant.NIGHT_STYLES);
            getImageViewNight();
            SpfUtils.setTheme(Constant.NIGHT);
        } else {
            activity.setTheme(Constant.DAY_STYLES);
            getImageViewDay();
            SpfUtils.setTheme(Constant.DAY);
        }
        CacheUitls.getInstance().put(Constant.BITMAP_CACHE_KEY, ActivityUtils.captureContent(activity));
        TransitionActivity.startIntent();
    }

}