package com.xsp.library.view.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xsp.library.R;


public class ClassicRefreshHeaderView extends SwipeHeaderLayout {
    private ImageView mArrowImageView;
    private ImageView mSuccessImageView;
    private TextView mRefreshTextView;
    private ProgressBar mProgressBar;

    private int mHeaderHeight;

    private Animation mRotateUp;
    private Animation mRotateDown;

    private boolean mRotated = false;

    public ClassicRefreshHeaderView(Context context) {
        this(context, null);
    }

    public ClassicRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.classic_refresh_header_height);
        mRotateUp     = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        mRotateDown   = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRefreshTextView  = (TextView) findViewById(R.id.id_tv_refresh);
        mArrowImageView   = (ImageView) findViewById(R.id.id_iv_arrow);
        mSuccessImageView = (ImageView) findViewById(R.id.id_iv_success);
        mProgressBar      = (ProgressBar) findViewById(R.id.id_pb_progress);
    }

    @Override
    public void onRefresh() {
        mSuccessImageView.setVisibility(GONE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);
        mRefreshTextView.setText(getResources().getString(R.string.classic_refresh_refreshing));
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onSwipe(int yOffset, boolean isComplete) {
        if (!isComplete) {
            mArrowImageView.setVisibility(VISIBLE);
            mProgressBar.setVisibility(GONE);
            mSuccessImageView.setVisibility(GONE);
            if (yOffset > mHeaderHeight) {
                mRefreshTextView.setText(getResources().getString(R.string.classic_refresh_release_to_refresh));
                if (!mRotated) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUp);
                    mRotated = true;
                }
            } else if (yOffset < mHeaderHeight) {
                if (mRotated) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateDown);
                    mRotated = false;
                }
                mRefreshTextView.setText(getResources().getString(R.string.classic_refresh_swipe_to_refresh));
            }
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        mRotated = false;
        mSuccessImageView.setVisibility(VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
        mRefreshTextView.setText(getResources().getString(R.string.classic_refresh_complete));
    }

    @Override
    public void onReset() {
        mRotated = false;
        mSuccessImageView.setVisibility(GONE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
    }

}
