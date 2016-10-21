package com.example.y.mvp.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.y.mvp.JokePicInfo;
import com.example.y.mvp.R;
import com.example.y.mvp.adapter.JokePicAdapter;
import com.example.y.mvp.data.Constant;
import com.example.y.mvp.mvp.presenter.JokePicPresenterImpl;
import com.example.y.mvp.mvp.presenter.Presenter;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.utils.ActivityUtils;
import com.example.y.mvp.utils.UIUtils;
import com.example.y.mvp.widget.MRecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/5/30.
 */
public class JokePicFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MRecyclerView.LoadingData, BaseView.JokePicView {


    private MRecyclerView recyclerView;
    private SwipeRefreshLayout srfLayout;

    private Presenter.JokePicPresenter jokePresenter;
    private JokePicAdapter adapter;

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

        jokePresenter = new JokePicPresenterImpl(this);
        List<JokePicInfo> jokePicInfos = new LinkedList<>();

        srfLayout.setOnRefreshListener(this);

        adapter = new JokePicAdapter(jokePicInfos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadingData(this);
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
        return R.layout.fragment_joke_pic;
    }

    @Override
    public void onRefresh() {
        adapter.removeAll();
        page = 1;
        jokePresenter.requestNetWork(page, isNull);
    }

    @Override
    public void onLoadMore() {
        if (!srfLayout.isRefreshing()) {
            ++page;
            jokePresenter.requestNetWork(page, isNull);
        }
    }

    @Override
    public void setData(List<JokePicInfo> datas) {
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

    @Override
    public void hideFoot() {
        adapter.setFoot(false);
    }

}
