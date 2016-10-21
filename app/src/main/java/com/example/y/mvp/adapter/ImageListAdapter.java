package com.example.y.mvp.adapter;


import com.example.y.mvp.ImageListInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.network.Api;
import com.example.y.mvp.utils.ImageLoaderUtils;
import com.example.y.mvp.utils.UIUtils;

import java.util.List;

/**
 * by y on 2016/4/28.
 */
public class ImageListAdapter extends BaseRecyclerViewAdapter<ImageListInfo> {


    public ImageListAdapter(List<ImageListInfo> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.image_list_item;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, ImageListInfo data) {
        holder.setTextView(R.id.tv_title, data.getTitle());
        holder.setTextView(R.id.tv_count, UIUtils.getString(R.string.list_adapter_views) + data.getCount());
        holder.setTextView(R.id.tv_size, data.getSize() + UIUtils.getString(R.string.list_adapter_number));
        ImageLoaderUtils.display(UIUtils.getContext(), holder.getImageView(R.id.image), Api.IMAGER_URL + data.getImg());
    }

}
