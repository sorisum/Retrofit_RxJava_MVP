package com.example.y.mvp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.y.mvp.NewsListInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.adapter.BaseRecyclerViewAdapter;
import com.example.y.mvp.adapter.NewsListAdapter;
import com.example.y.mvp.data.Constant;
import com.example.y.mvp.mvp.presenter.NewsListPresenterImpl;
import com.example.y.mvp.mvp.presenter.Presenter;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.utils.ActivityUtils;
import com.example.y.mvp.utils.UIUtils;
import com.example.y.mvp.widget.MRecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * by 12406 on 2016/5/14.
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        MRecyclerView.LoadingData, BaseView.NewsListView, BaseRecyclerViewAdapter.OnItemClickListener<NewsListInfo> {

    private SwipeRefreshLayout srfLayout;
    private MRecyclerView recyclerView;
    private NewsListAdapter adapter;
    private Presenter.NewsListPresenter newsListPresenter;

    public static Fragment newInstance(int index) {
        Bundle bundle = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        srfLayout = getView(R.id.srf_layout);
    }

    @Override
    protected void initData() {

        if (!isPrepared || !isVisible || isLoad) {
            return;
        }

        newsListPresenter = new NewsListPresenterImpl(this);
        LinkedList<NewsListInfo> list = new LinkedList<>();
        adapter = new NewsListAdapter(list, index + 1);
        adapter.setOnItemClickListener(this);
        srfLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadingData(this);
        adapter.setFoot(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LINEAR, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        srfLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }


    @Override
    public void onRefresh() {
        page = 1;
        adapter.removeAll();
        newsListPresenter.requestNetWork(index + 1, page, isNull);
    }

    @Override
    public void onLoadMore() {
        if (!srfLayout.isRefreshing()) {
            ++page;
            newsListPresenter.requestNetWork(index + 1, page, isNull);
        }
    }


    @Override
    public void setData(List<NewsListInfo> datas) {
        if (datas.isEmpty()) {
            isNull = true;
        } else {
            adapter.addAll(datas);
        }
    }

    @Override
    public void netWorkError() {
        ActivityUtils.Toast(UIUtils.getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        srfLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srfLayout.setRefreshing(false);
    }

    @Override
    public void showFoot() {
        adapter.setFoot(true);
    }

    public void hideFoot() {
        adapter.setFoot(false);
    }

    @Override
    public void onItemClick(View view, int position, NewsListInfo info) {
        newsListPresenter.onClick(info);
    }

}
