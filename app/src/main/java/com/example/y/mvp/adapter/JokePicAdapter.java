package com.example.y.mvp.adapter;


import com.example.y.mvp.JokePicInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.utils.ImageLoaderUtils;
import com.example.y.mvp.utils.UIUtils;

import java.util.List;

/**
 * by y on 2016/5/30.
 */
public class JokePicAdapter extends BaseRecyclerViewAdapter<JokePicInfo> {


    public JokePicAdapter(List<JokePicInfo> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.joke_pic_list_item;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, JokePicInfo data) {
        ImageLoaderUtils.display(UIUtils.getContext(), holder.getImageView(R.id.image), data.getImg());
        holder.setTextView(R.id.tv_time, data.getTitle());
    }

}
