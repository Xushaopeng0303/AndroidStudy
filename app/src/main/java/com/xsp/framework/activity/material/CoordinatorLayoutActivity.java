package com.xsp.framework.activity.material;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsp.library.activity.BaseActivity;

import com.xsp.framework.R;

/**
 * CoordinatorLayout Demo
 * 它本质是一个 FrameLayout，然而它允许开发者通过制定 Behavior 从而实现各种复杂的 UI 效果
 * HeaderScrollingBehavior 负责协调 RecyclerView 与 Header View 的关系，它要根据 Header View 的位移调整自己的位置。
 * HeaderFloatBehavior 负责协调搜索框与 Header View 的关系，也是依赖于 Header View，相对比较简单
 */
public class CoordinatorLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_coordinator_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_coordinator_recycler_view);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.demo_item_two_text, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                String title = getResources().getString(R.string.common_item_up_title) + " : " + position;
                vh.titleText.setText(title);
                vh.subTitleText.setText(getResources().getString(R.string.common_item_down_title));
            }

            @Override
            public int getItemCount() {
                return 50;
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                TextView titleText;
                TextView subTitleText;

                ViewHolder(View itemView) {
                    super(itemView);
                    titleText    = (TextView) itemView.findViewById(R.id.id_common_text_up);
                    subTitleText = (TextView) itemView.findViewById(R.id.id_common_text_down);
                }

            }
        });
    }
}
