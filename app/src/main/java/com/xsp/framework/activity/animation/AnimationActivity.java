package com.xsp.framework.activity.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;

/**
 * 学习 Android 的动画
 * AlphaAnimation 	渐变透明度动画效果
 * ScaleAnimation 	渐变尺寸伸缩动画效果
 * TranslateAnimation 	画面转换位置移动动画效果
 * RotateAnimation 	画面转移旋转动画效果
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_animation_activity);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        mImgPic = (ImageView) findViewById(R.id.id_animation_iv);
        findViewById(R.id.id_animation_alpha).setOnClickListener(this);
        findViewById(R.id.id_animation_scale).setOnClickListener(this);
        findViewById(R.id.id_animation_translate).setOnClickListener(this);
        findViewById(R.id.id_animation_rotate).setOnClickListener(this);

        findViewById(R.id.id_animation_start).setOnClickListener(this);
        findViewById(R.id.id_animation_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation myAnimation;
        switch (v.getId()) {
            case R.id.id_animation_alpha:
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.demo_alpha);
                mImgPic.startAnimation(myAnimation);
                break;
            case R.id.id_animation_scale:
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.demo_scale);
                mImgPic.startAnimation(myAnimation);
                break;
            case R.id.id_animation_translate:
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.demo_translate);
                mImgPic.startAnimation(myAnimation);
                break;
            case R.id.id_animation_rotate:
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.demo_rotate);
                mImgPic.startAnimation(myAnimation);
                break;
            case R.id.id_animation_start:
                mImgPic.setImageResource(R.drawable.animation_demo_frame);
                ((AnimationDrawable) mImgPic.getDrawable()).start();
                break;
            case R.id.id_animation_stop:
                mImgPic.setImageResource(R.drawable.animation_demo_frame);
                ((AnimationDrawable) mImgPic.getDrawable()).stop();
                break;
        }
    }
}
