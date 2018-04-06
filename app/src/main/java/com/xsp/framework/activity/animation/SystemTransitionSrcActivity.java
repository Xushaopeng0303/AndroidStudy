package com.xsp.framework.activity.animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;

/**
 * 系统 转场动画，支持API 21以上（5.0以上）
 */
public class SystemTransitionSrcActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Window页面切换需要使用动画
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // 获取动画
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);

            getWindow().setExitTransition(explode);         // 退出时使用
            getWindow().setEnterTransition(explode);        // 第一次进入时使用
            getWindow().setReenterTransition(explode);      // 再次进入时使用
        }
        setContentView(R.layout.common_one_text_layout);

        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.id_common_text_show);
        textView.setText(R.string.click_to_see_effect);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 300);
        textView.setLayoutParams(lp);
        textView.setBackgroundColor(Color.BLUE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(SystemTransitionSrcActivity.this, SystemTransitionDestActivity.class);
                    // 通过makeSceneTransitionAnimation函数创建一个ActivityOptions对象，再将其转为Bundle对象
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SystemTransitionSrcActivity.this).toBundle());
                }
            }
        });
    }
}
