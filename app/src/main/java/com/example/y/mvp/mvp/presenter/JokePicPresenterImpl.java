package com.example.y.mvp.mvp.presenter;

import com.example.y.mvp.mvp.model.JokePicBean;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.network.NetWorkRequest;

/**
 * by y on 2016/5/30.
 */
public class JokePicPresenterImpl extends BasePresenterImpl<BaseView.JokePicView, JokePicBean>
        implements Presenter.JokePicPresenter {


    public JokePicPresenterImpl(BaseView.JokePicView view) {
        super(view);
    }

    @Override
    protected void showProgress() {

    }

    @Override
    protected void netWorkNext(JokePicBean jokePicBean) {
        view.setData(jokePicBean.getShowapi_res_body().getContentlist());
    }

    @Override
    protected void hideProgress() {
        view.hideFoot();
        view.hideProgress();
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
    }

    @Override
    public void requestNetWork(int page, boolean isNull) {
        if (page == 1) {
            view.showProgress();
        } else {
            if (!isNull) {
                view.showFoot();
            }
        }
        NetWorkRequest.jokePicList(page, getSubscriber());
    }

}
