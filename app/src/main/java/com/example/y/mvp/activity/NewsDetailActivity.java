package com.example.y.mvp.activity;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.y.mvp.NewsDetailInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.mvp.presenter.NewsDetailPresenterImpl;
import com.example.y.mvp.mvp.presenter.Presenter;
import com.example.y.mvp.mvp.presenter.ToolBarItemPresenterImpl;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.network.Api;
import com.example.y.mvp.utils.ActivityUtils;
import com.example.y.mvp.utils.ImageLoaderUtils;
import com.example.y.mvp.utils.StatusBarUtil;
import com.example.y.mvp.utils.UIUtils;
import com.example.y.mvp.utils.db.NewsDetailDbUtils;
import com.example.y.mvp.widget.MImageView;

/**
 * by 12406 on 2016/5/30.
 */
public class NewsDetailActivity extends DarkViewActivity
        implements BaseView.NewsDetailView, BaseView.ToolBarItemView {

    private MImageView image;
    private CollapsingToolbarLayout collapsingToolbar;
    private ProgressBar progressBar;
    private TextView content;
    private Toolbar toolbar;

    private int id;
    private String message;
    private Presenter.ToolBarItemPresenter toolBarItemPresenter;


    public static void startIntent(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        ActivityUtils.startActivity(NewsDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getBundle();
        init();
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setTranslucentForImageView(this, image);
    }

    @Override
    protected void initById() {
        image = getView(R.id.image);
        collapsingToolbar = getView(R.id.collapsing_toolbar);
        progressBar = getView(R.id.progressBar);
        content = getView(R.id.content);
        toolbar = getView(R.id.toolbar);
    }

    private void init() {
        swipeBackLayout.setEdgeDp(100);
        Presenter.NewsDetailPresenter newsDetailPresenter = new NewsDetailPresenterImpl(this);
        toolBarItemPresenter = new ToolBarItemPresenterImpl(this);
        newsDetailPresenter.requestNetWork(id);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                toolBarItemPresenter.switchId(item.getItemId());
                return true;
            }
        });

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle && !bundle.isEmpty()) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public void setData(NewsDetailInfo datas) {
        ImageLoaderUtils.display(getApplicationContext(), image, Api.IMAGER_URL + datas.getImg());
        content.setText(Html.fromHtml(datas.getMessage()));
        collapsingToolbar.setTitle(datas.getTitle());
        message = String.valueOf(Html.fromHtml(datas.getMessage()));
        NewsDetailDbUtils.insert(datas.getId(), datas.getTitle(), datas.getMessage(), datas.getImg());
    }

    @Override
    public void switchShare() {
        ActivityUtils.share(this, message);
    }

    @Override
    public void netWorkError() {
        ActivityUtils.Toast(UIUtils.getString(R.string.network_error));
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }
}
