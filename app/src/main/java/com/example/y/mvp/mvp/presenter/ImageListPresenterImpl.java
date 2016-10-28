package com.example.y.mvp.mvp.presenter;


import com.example.y.mvp.ImageListInfo;
import com.example.y.mvp.activity.ImageDetailActivity;
import com.example.y.mvp.mvp.model.BaseBean;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.network.NetWorkRequest;

/**
 * by y on 2016/4/29.
 */
public class ImageListPresenterImpl extends BasePresenterImpl<BaseView.ImageListView,BaseBean.ImageListBean>
        implements Presenter.ImageListPresenter {


    public ImageListPresenterImpl(BaseView.ImageListView view) {
        super(view);
    }

    @Override
    protected void showProgress() {

    }

    @Override
    protected void netWorkNext(BaseBean.ImageListBean imageListBean) {
        view.setData(imageListBean.getTngou());
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
    public void requestNetWork(int id, int page, boolean isNull) {
        if (page == 1) {
            view.showProgress();
        } else {
            if (!isNull) {
                view.showFoot();
            }
        }
        NetWorkRequest.imageList(id, page, getSubscriber());
    }

    @Override
    public void onClick(ImageListInfo info) {
        ImageDetailActivity.startIntent(info.getId(), info.getTitle());
    }

}
