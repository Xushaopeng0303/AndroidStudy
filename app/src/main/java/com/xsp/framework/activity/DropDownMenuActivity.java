package com.xsp.framework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;
import com.xsp.library.view.dropdownmenu.CustomDropAdapter;
import com.xsp.library.view.dropdownmenu.DropDownMenu;
import com.xsp.library.view.dropdownmenu.ExListDropAdapter;
import com.xsp.library.view.dropdownmenu.ListDropAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drop Down Menu Demo
 */
public class DropDownMenuActivity extends BaseActivity {
    private DropDownMenu mDropDownMenu;
    private String mHeaders[] = {};
    private List<View> mPopupViews = new ArrayList<>();

    private ExListDropAdapter mCityAdapter;
    private ListDropAdapter mAgeAdapter;
    private ListDropAdapter mSexAdapter;
    private CustomDropAdapter mConstellationAdapter;

    private int mConstellationPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_menu);
        mHeaders = getResources().getStringArray(R.array.drop_down_header);
        mDropDownMenu = (DropDownMenu) findViewById(R.id.drop_down_menu);
        initView();
    }

    private void initView() {
        final String mCityArray[] = getResources().getStringArray(R.array.drop_down_city);
        final ListView cityListView = new ListView(this);
        mCityAdapter = new ExListDropAdapter(this, Arrays.asList(mCityArray));
        cityListView.setDividerHeight(0);
        cityListView.setAdapter(mCityAdapter);

        final String mAgeArray[] = getResources().getStringArray(R.array.drop_down_age);
        final ListView ageListView = new ListView(this);
        ageListView.setDividerHeight(0);
        mAgeAdapter = new ListDropAdapter(this, Arrays.asList(mAgeArray));
        ageListView.setAdapter(mAgeAdapter);

        final String mSexArray[]  = getResources().getStringArray(R.array.drop_down_sex);
        final ListView sexListView = new ListView(this);
        sexListView.setDividerHeight(0);
        mSexAdapter = new ListDropAdapter(this, Arrays.asList(mSexArray));
        sexListView.setAdapter(mSexAdapter);

        final String[]cArray = getResources().getStringArray(R.array.drop_down_c);
        final View cView = this.getLayoutInflater().inflate(R.layout.view_custom_drop_down_meun, null);
        GridView gridView = (GridView) cView.findViewById(R.id.drop_down_grid_c);
        mConstellationAdapter = new CustomDropAdapter(this, Arrays.asList(cArray));
        gridView.setAdapter(mConstellationAdapter);
        TextView okText = (TextView)cView.findViewById(R.id.drop_down_ok);
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(mConstellationPosition == 0 ? mHeaders[3] : cArray[mConstellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });
        mPopupViews.clear();
        mPopupViews.add(cityListView);
        mPopupViews.add(ageListView);
        mPopupViews.add(sexListView);
        mPopupViews.add(cView);

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mHeaders[0] : mCityArray[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAgeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mHeaders[1] : mAgeArray[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mHeaders[2] : mSexArray[position]);
                mDropDownMenu.closeMenu();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mConstellationAdapter.setCheckItem(position);
                mConstellationPosition = position;
            }
        });

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText(getString(R.string.drop_down_content));
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mDropDownMenu.setDropDownMenu(Arrays.asList(mHeaders), mPopupViews, contentView);
    }

    @Override
    public void onPause() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        }

        super.onPause();
    }
}
