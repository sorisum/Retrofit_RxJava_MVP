package com.example.y.mvp.adapter;

import android.text.Html;

import com.example.y.mvp.JokeTextInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.utils.UIUtils;
import com.example.y.mvp.utils.db.JokeTextDbUtils;

import java.util.List;

/**
 * by y on 2016/5/30.
 */
public class JokeTextAdapter extends BaseRecyclerViewAdapter<JokeTextInfo> {


    public JokeTextAdapter(List<JokeTextInfo> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.joke_text_list_item;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, JokeTextInfo data) {
        holder.setTextView(R.id.tv_time, UIUtils.getString(R.string.news_time) + data.getCt());
        holder.setTextView(R.id.tv_text, Html.fromHtml(data.getText()));
        JokeTextDbUtils.insert(data.getId(), data.getText(), data.getCt());
    }

}
