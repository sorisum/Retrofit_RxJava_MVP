package com.example.y.mvp.adapter;


import com.example.y.mvp.NewsListInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.network.Api;
import com.example.y.mvp.utils.ImageLoaderUtils;
import com.example.y.mvp.utils.TimeUtils;
import com.example.y.mvp.utils.UIUtils;
import com.example.y.mvp.utils.db.NewsListDbUtils;

import java.util.List;

/**
 * by 12406 on 2016/5/15.
 */
public class NewsListAdapter extends BaseRecyclerViewAdapter<NewsListInfo> {

    private int i = 0;

    public NewsListAdapter(List<NewsListInfo> datas, int i) {
        super(datas);
        this.i = i;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.news_list_item;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, NewsListInfo data) {
        holder.setTextView(R.id.tv_time, UIUtils.getString(R.string.news_time) + TimeUtils.getDateToString(data.getTime()));
        holder.setTextView(R.id.tv_title, data.getTitle());
        holder.setTextView(R.id.tv_url, data.getFromurl());
        ImageLoaderUtils.display(UIUtils.getContext(), holder.getImageView(R.id.image), Api.IMAGER_URL + data.getImg());
        NewsListDbUtils.insert(data.getId(), i, data.getTitle(), data.getImg(), data.getFromurl(), data.getTime());
    }

}
