package com.example.y.mvp.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.y.mvp.R;
import com.example.y.mvp.adapter.TabJokeAdapter;
import com.example.y.mvp.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/5/30.
 */
public class JokeMainPagerFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void initById() {
        tabLayout = getView(R.id.tab_layout);
        viewPager = getView(R.id.viewPager);
    }

    @Override
    protected void initData() {

        List<String> data = new LinkedList<>();
        data.add(UIUtils.getString(R.string.joke_text));
        data.add(UIUtils.getString(R.string.joke_pic));


        TabJokeAdapter adapter = new TabJokeAdapter(getChildFragmentManager(), data);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_viewpager;
    }
}
