package com.xsp.framework.activity.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;
import com.xsp.library.util.KShareViewActivityManager;

/**
 * Android 转场动画
 */
public class TransitionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_transition_activity);

        final TextView textSearch = (TextView) findViewById(R.id.id_translate_text_view);
        final Button textDirect = (Button) findViewById(R.id.id_translate_direct);
        final Button textAnim = (Button) findViewById(R.id.id_translate_direct_with_animate);

        textDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directTranslate(textSearch);
            }
        });
        textAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directTranslateWithAnimate(textSearch);
            }
        });


    }

    private void directTranslate(View view) {
        KShareViewActivityManager.getInstance(TransitionActivity.this).startActivity(
                TransitionActivity.this, TransitionDestActivity.class,
                R.layout.demo_transition_activity, R.layout.demo_translate_desc_activity,
                view);
    }

    private void directTranslateWithAnimate(View view) {
        KShareViewActivityManager.getInstance(TransitionActivity.this).withAction(new KShareViewActivityManager.KShareViewActivityAction() {
            @Override
            public void onAnimatorStart() {
                Log.d("xsp", "onAnimatorStart: 动画开始：" + System.currentTimeMillis());
            }

            @Override

            public void onAnimatorEnd() {
                Log.d("xsp", "onAnimatorEnd: 动画结束" + System.currentTimeMillis());
            }

            @Override
            public void changeViewProperty(View view) {
                Log.d("xsp", "changeViewProperty: 动画改变");
            }
        }).setDuration(200).startActivity(TransitionActivity.this, TransitionDestActivity.class,
                R.layout.demo_transition_activity, R.layout.demo_translate_desc_activity,
                view);
    }

    private void start(View firstSharedView) {
        Intent intent = new Intent(this, TransitionDestActivity.class);

        Pair first = new Pair<>(firstSharedView, ViewCompat.getTransitionName(firstSharedView));

        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, first);

        ActivityCompat.startActivity(this,
                intent, transitionActivityOptions.toBundle());
    }

}
