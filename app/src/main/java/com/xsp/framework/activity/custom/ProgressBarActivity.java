package com.xsp.framework.activity.custom;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;
import com.xsp.library.view.progressbar.AnimProgressBar;

/**
 * 进度条Activity
 */
public class ProgressBarActivity extends BaseActivity {
    Button mStartBtn;
    AnimProgressBar mPb1, mPb2, mPb3, mPb4, mPb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_custom_ui_progress_bar);

        mStartBtn = (Button) findViewById(R.id.id_progress_bar_start);
        mPb1 = (AnimProgressBar) findViewById(R.id.id_progress_bar_frame);
        mPb1.setDrawableIds(new int[]{R.mipmap.progress_bar_frame_one, R.mipmap.progress_bar_frame_two, R.mipmap.progress_bar_frame_three, R.mipmap.progress_bar_frame_four, R.mipmap.progress_bar_frame_five, R.mipmap.progress_bar_frame_six, R.mipmap.progress_bar_frame_seven});
        mPb2 = (AnimProgressBar) findViewById(R.id.id_progress_bar_null);
        mPb3 = (AnimProgressBar) findViewById(R.id.id_progress_bar_rotate);
        mPb4 = (AnimProgressBar) findViewById(R.id.id_progress_bar_scale);
        mPb5 = (AnimProgressBar) findViewById(R.id.id_progress_bar_rotate_scale);

        //使用属性动画来实现进度的变化
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = Integer.parseInt(animation.getAnimatedValue().toString());
                mPb1.setProgress(progress);
                if (mPb1.getProgress() >= mPb1.getMax()){
                    //进度满了之后停止动画
                    mPb1.setAnimRun(false);
                }
                mPb2.setProgress(progress);
                if (mPb2.getProgress() >= mPb2.getMax()){
                    //进度满了之后改变图片
                    mPb2.setPicture(R.mipmap.progress_bar_full);
                }
                mPb3.setProgress(progress);
                if (mPb3.getProgress() >= mPb3.getMax()){
                    mPb3.setAnimRun(false);
                }
                mPb4.setProgress(progress);
                if (mPb4.getProgress() >= mPb4.getMax()){
                    mPb4.setAnimRun(false);
                }
                mPb5.setProgress(progress);
                if (mPb5.getProgress() >= mPb5.getMax()){
                    mPb5.setAnimRun(false);
                }
            }
        });
        valueAnimator.setDuration(60000);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPb1.setAnimRun(true);
                mPb2.setPicture(R.mipmap.progress_bar_zero);
                mPb3.setAnimRun(true);
                mPb4.setAnimRun(true);
                mPb5.setAnimRun(true);
                valueAnimator.start();

            }
        });
    }
}
