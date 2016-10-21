package com.example.y.mvp.mvp.presenter;


import com.example.y.mvp.mvp.view.BaseView;

/**
 * by 12406 on 2016/5/1.
 */
public class MainViewPresenterImpl extends BasePresenterImpl<BaseView.MainView>
        implements Presenter.MainViewPresenter {


    public MainViewPresenterImpl(BaseView.MainView view) {
        super(view);
    }

    @Override
    public void switchPosition(int position) {
        switch (position) {
            case 0:
                view.switchNews();
                break;
            case 1:
                view.switchImageClassification();
                break;
            case 2:
                view.switchNewImage();
                break;
            case 3:
                view.switchJoke();
                break;
            case 4:
                view.switchAbout();
                break;
        }
    }

    @Override
    protected void onNetWorkError() {
    }

}
