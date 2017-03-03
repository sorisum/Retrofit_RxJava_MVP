package com.example.y.mvp.mvp.presenter;


import android.content.pm.PackageManager;
import android.widget.Toast;

import com.example.y.mvp.R;
import com.example.y.mvp.data.Constant;
import com.example.y.mvp.mvp.model.BaseBean;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.network.NetWorkRequest;
import com.example.y.mvp.utils.UIUtils;

/**
 * by y on 2016/4/29.
 */
public class ImageDetailPresenterImpl extends BasePresenterImpl<BaseView.ImageDetailView, BaseBean.ImageDetailBean>
        implements Presenter.ImageDetailPresenter {


    public ImageDetailPresenterImpl(BaseView.ImageDetailView view) {
        super(view);
    }

    @Override
    protected void showProgress() {
    }

    @Override
    protected void netWorkNext(BaseBean.ImageDetailBean imageDetailBean) {
        view.setData(imageDetailBean.getList());
    }

    @Override
    protected void hideProgress() {

    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
    }

    @Override
    public void requestNetWork(int id) {
        NetWorkRequest.imageDetail(id, getSubscriber());
    }


    @Override
    public void competence(int requestCode, int[] grantResults) {
        if (requestCode == Constant.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(UIUtils.getContext(), UIUtils.getString(R.string.competence_error), Toast.LENGTH_LONG).show();
            }
        }
    }

}
