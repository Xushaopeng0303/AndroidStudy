package com.xsp.framework.activity.material;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xsp.framework.R;
import com.xsp.framework.fragment.EmptyFragment;
import com.xsp.library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TabLayout
 */
public class TabLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_frame);

        initTabLayout();
    }

    /**
     * Android Support Design 库 ,gradle 中 compile 'com.android.support:design:25.1.0'
     *
     * 参考：http://www.jianshu.com/p/7f79b08f5afa
     *
     * TabLayout 可以自定义TabView
     */
    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_frame_tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.id_frame_view_pager);

        List<Fragment> fragmentList = new ArrayList<>();

        String[] items = getResources().getStringArray(R.array.tab_layout_title_array);
        List<String> titleList = Arrays.asList(items);

        int size = titleList.size();
        for (int i = 0; i < size; i++) {
            fragmentList.add(EmptyFragment.getInstance(titleList.get(i)));
        }

        ListFragmentPagerAdapter adapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        tabLayout.setupWithViewPager(viewPager);  // 将 TabLayout 和 ViewPager 关联起来
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Tab 选中
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Tab 未选中
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Tab 再次被选中
            }
        });

    }

    public class ListFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private List<String> titleList;

        ListFragmentPagerAdapter(FragmentManager fm, List<Fragment> fList, List<String> tList) {
            super(fm);
            this.fragmentList = fList;
            this.titleList = tList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
