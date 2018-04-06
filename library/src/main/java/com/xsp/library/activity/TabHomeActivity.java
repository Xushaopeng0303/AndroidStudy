package com.xsp.library.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.xsp.library.R;
import com.xsp.library.fragment.BaseFragment;
import com.xsp.library.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of Home tab activity
 */
public abstract class TabHomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    private List<BaseFragment> mTabs = new ArrayList<>();
    private List<ImageTextView> mTabIndicator = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPager();
    }

    protected abstract List<BaseFragment> addFragment();
    protected abstract List<ImageTextView> addTab();

    private void initView() {
        // 添加Fragment
        List<BaseFragment> fragmentList = addFragment();
        if (fragmentList == null) {
            return;
        }
        for (BaseFragment fragment : fragmentList) {
            mTabs.add(fragment);
        }

        // 添加Tab
        mTabIndicator = addTab();
        if (mTabIndicator == null) {
            return;
        }

        if (mTabIndicator.size() != fragmentList.size()) {
            throw new RuntimeException("Tab size must equals fragment size");
        }

        LinearLayout tabContainer = (LinearLayout) findViewById(R.id.id_tab_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.leftMargin = 5;
        params.topMargin = 5;
        params.rightMargin = 5;
        params.bottomMargin = 5;

        int size = mTabIndicator.size();
        for (int i = 0; i < size; i++) {
            final int position = i;
            final ImageTextView tab = mTabIndicator.get(position);

            tab.setLayoutParams(params);
            tabContainer.addView(tab);

            tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetOtherTabs();
                    tab.setIconAlpha(1.0f);
                    mViewPager.setCurrentItem(position, false);
                }
            });
        }

        mTabIndicator.get(0).setIconAlpha(1.0f);        // 设置了默认的第一个颜色的颜色

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };

        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ImageTextView left = mTabIndicator.get(position);
            ImageTextView right = mTabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);  // 变小
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 这个方法是设置每个底部view的颜色为默认颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }

}
