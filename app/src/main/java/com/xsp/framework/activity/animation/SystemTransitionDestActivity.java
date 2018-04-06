package com.xsp.framework.activity.animation;

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
public class SystemTransitionDestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
            getWindow().setExitTransition(slide);         // 退出时使用
            getWindow().setEnterTransition(slide);        // 第一次进入时使用
            getWindow().setReenterTransition(slide);      // 再次进入时使用
        }
        setContentView(R.layout.common_one_text_layout);

        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.id_common_text_show);
        textView.setText(R.string.click_to_finish);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 600);
        textView.setLayoutParams(lp);
        textView.setBackgroundColor(Color.GREEN);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
