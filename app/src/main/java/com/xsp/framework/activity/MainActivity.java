package com.xsp.framework.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.xsp.framework.fragment.BaseListFragment;
import com.xsp.framework.util.TabItemGenerate;
import com.xsp.library.activity.TabHomeActivity;
import com.xsp.library.fragment.BaseFragment;
import com.xsp.library.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

import com.xsp.framework.R;

public class MainActivity extends TabHomeActivity {

    private List<String> mTabNameList = new ArrayList<>();
    private List<Drawable> mTabDrawableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<BaseFragment> addFragment() {
        List<BaseFragment> list = new ArrayList<>();
        list.add(BaseListFragment.getInstance("UI", TabItemGenerate.getUiList(this)));
        list.add(BaseListFragment.getInstance("Function", TabItemGenerate.getFunctionList(this)));
        list.add(BaseListFragment.getInstance("Frame", TabItemGenerate.getFrameList(this)));
        list.add(BaseListFragment.getInstance("Advanced", TabItemGenerate.getAdvancedList(this)));

        return list;
    }

    @Override
    protected List<ImageTextView> addTab() {
        initData();

        List<ImageTextView> tabList = new ArrayList<>();
        ImageTextView tab1 = new ImageTextView(this, mTabDrawableList.get(0), mTabNameList.get(0));
        ImageTextView tab2 = new ImageTextView(this, mTabDrawableList.get(1), mTabNameList.get(1));
        ImageTextView tab3 = new ImageTextView(this, mTabDrawableList.get(2), mTabNameList.get(2));
        ImageTextView tab4 = new ImageTextView(this, mTabDrawableList.get(3), mTabNameList.get(3));

        tabList.add(tab1);
        tabList.add(tab2);
        tabList.add(tab3);
        tabList.add(tab4);

        return tabList;
    }

    private void initData() {
        // Tab 名称
        mTabNameList.add(getResources().getString(R.string.tab_ui));
        mTabNameList.add(getResources().getString(R.string.tab_function));
        mTabNameList.add(getResources().getString(R.string.tab_frame));
        mTabNameList.add(getResources().getString(R.string.tab_advanced));

        // Tab Icon
        mTabDrawableList.add(getResources().getDrawable(R.mipmap.home_tab_ui));
        mTabDrawableList.add(getResources().getDrawable(R.mipmap.home_tab_function));
        mTabDrawableList.add(getResources().getDrawable(R.mipmap.home_tab_frame));
        mTabDrawableList.add(getResources().getDrawable(R.mipmap.home_tab_advanced));
    }
}
