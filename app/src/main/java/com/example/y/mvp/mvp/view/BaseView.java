package com.example.y.mvp.mvp.view;


import com.example.y.mvp.ImageDetailInfo;
import com.example.y.mvp.ImageListInfo;
import com.example.y.mvp.ImageTabNameInfo;
import com.example.y.mvp.JokePicInfo;
import com.example.y.mvp.JokeTextInfo;
import com.example.y.mvp.NewsDetailInfo;
import com.example.y.mvp.NewsListInfo;
import com.example.y.mvp.NewsTabNameInfo;
import com.example.y.mvp.mvp.model.ImageNewInfo;

import java.util.List;

/**
 * by y on 2016/5/27.
 */
public interface BaseView<T> {


    void setData(List<T> datas);

    void netWorkError();

    void hideProgress();

    void showProgress();

    void showFoot();

    void hideFoot();

    interface JokePicView extends BaseView<JokePicInfo> {
    }

    interface JokeTextView extends BaseView<JokeTextInfo> {
    }

    interface ImageListView extends BaseView<ImageListInfo> {
    }

    interface ImageNewView extends BaseView<ImageNewInfo> {
        void offClose();
    }

    interface NewsListView extends BaseView<NewsListInfo> {
    }

    interface TabNameView extends BaseView<ImageTabNameInfo> {
    }

    interface TabNewsView extends BaseView<NewsTabNameInfo> {
    }

    interface ImageDetailView {
        void setData(List<ImageDetailInfo> datas);

        void netWorkError();
    }

    interface NewsDetailView {
        void setData(NewsDetailInfo datas);

        void netWorkError();

        void hideProgress();

        void showProgress();
    }

    interface MainView {
        void switchNews();

        void switchImageClassification();

        void switchNewImage();

        void switchJoke();

        void switchAbout();
    }

    interface ToolBarItemView {
        void switchShare();
    }
}
