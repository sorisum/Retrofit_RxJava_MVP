package com.example.y.mvp.mvp.presenter;


import com.example.y.mvp.NewsDetailInfo;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.network.NetWorkRequest;

/**
 * by 12406 on 2016/5/30.
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl<BaseView.NewsDetailView, NewsDetailInfo>
        implements Presenter.NewsDetailPresenter {


    public NewsDetailPresenterImpl(BaseView.NewsDetailView view) {
        super(view);
    }

    @Override
    protected void showProgress() {
        view.showProgress();
    }

    @Override
    protected void netWorkNext(NewsDetailInfo newsDetailInfo) {
        view.setData(newsDetailInfo);
    }

    @Override
    protected void hideProgress() {
        view.hideProgress();
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
    }


    @Override
    public void requestNetWork(int id) {
        NetWorkRequest.newsDetail(id, getSubscriber());
    }

}
