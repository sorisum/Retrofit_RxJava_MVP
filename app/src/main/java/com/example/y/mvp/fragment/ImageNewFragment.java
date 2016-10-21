package com.example.y.mvp.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.y.mvp.R;
import com.example.y.mvp.adapter.BaseRecyclerViewAdapter;
import com.example.y.mvp.adapter.ImageNewAdapter;
import com.example.y.mvp.data.Constant;
import com.example.y.mvp.mvp.model.ImageNewInfo;
import com.example.y.mvp.mvp.presenter.ImageNewPresenterImpl;
import com.example.y.mvp.mvp.presenter.Presenter;
import com.example.y.mvp.mvp.view.BaseView;
import com.example.y.mvp.utils.ActivityUtils;
import com.example.y.mvp.utils.UIUtils;
import com.example.y.mvp.widget.MRecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * by 12406 on 2016/5/1.
 */
public class ImageNewFragment extends BaseFragment implements
        BaseView.ImageNewView, SwipeRefreshLayout.OnRefreshListener,
        MRecyclerView.LoadingData, View.OnClickListener,
        BaseRecyclerViewAdapter.OnItemClickListener<ImageNewInfo> {


    private EditText etId;
    private EditText etRows;
    private Button button;

    private MRecyclerView recyclerView;
    private SwipeRefreshLayout srfLayout;

    private ImageNewAdapter adapter;
    private Presenter.ImageNewPresenter imageNewPresenter;

    @Override
    protected void initById() {
        etId = getView(R.id.et_id);
        etRows = getView(R.id.et_rows);
        recyclerView = getView(R.id.recyclerView);
        srfLayout = getView(R.id.srf_layout);
        button = getView(R.id.btn_image);
    }

    @Override
    public void initData() {


        List<ImageNewInfo> data = new LinkedList<>();

        imageNewPresenter = new ImageNewPresenterImpl(this);
        button.setOnClickListener(this);
        srfLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadingData(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW, LinearLayoutManager.VERTICAL));

        adapter = new ImageNewAdapter(data);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_image;
    }


    @Override
    public void setData(List<ImageNewInfo> datas) {
        if (!datas.isEmpty()) {
            adapter.addAll(datas);
        }
    }

    @Override
    public void netWorkError() {
        ActivityUtils.Toast(UIUtils.getString(R.string.network_error));
    }

    @Override
    public void hideProgress() {
        srfLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        srfLayout.setRefreshing(true);
    }

    @Override
    public void showFoot() {

    }

    @Override
    public void hideFoot() {

    }

    @Override
    public void onRefresh() {
        adapter.removeAll();
        imageNewPresenter.requestNetWork(etId.getText().toString().trim(), etRows.getText().toString().trim());
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onItemClick(View view, int position, ImageNewInfo info) {
        imageNewPresenter.onClick(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image:
                onRefresh();
                break;
        }
    }
}
