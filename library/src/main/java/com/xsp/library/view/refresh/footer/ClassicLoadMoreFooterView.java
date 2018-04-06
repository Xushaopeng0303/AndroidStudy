package com.xsp.library.view.refresh.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xsp.library.R;


public class ClassicLoadMoreFooterView extends SwipeFooterLayout {
    private TextView mLoadMoreTv;
    private ImageView mSuccessIv;
    private ProgressBar mProgressBar;

    private int mFooterHeight;

    public ClassicLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.classic_load_more_footer_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadMoreTv  = (TextView) findViewById(R.id.id_tv_load_more);
        mSuccessIv   = (ImageView) findViewById(R.id.id_iv_success);
        mProgressBar = (ProgressBar) findViewById(R.id.id_pb_progress);
    }

    @Override
    public void onPrepare() {
        mSuccessIv.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
        mLoadMoreTv.setVisibility(VISIBLE);
        mLoadMoreTv.setText(getResources().getString(R.string.classic_refresh_swipe_to_load_more));
    }

    @Override
    public void onSwipe(int yOffset, boolean isComplete) {
        Log.d("xsp", "yOffset :" + yOffset + " ; isComplete :" + isComplete);
        if (!isComplete) {
            mSuccessIv.setVisibility(GONE);
            mProgressBar.setVisibility(GONE);
            if (-yOffset >= mFooterHeight) {
                mLoadMoreTv.setText(getResources().getString(R.string.classic_refresh_release_to_load_more));
            } else {
                mLoadMoreTv.setText(getResources().getString(R.string.classic_refresh_swipe_to_load_more));
            }
        }
    }

    @Override
    public void onLoadMore() {
        mLoadMoreTv.setText(getResources().getString(R.string.classic_refresh_loading_more));
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        mProgressBar.setVisibility(GONE);
        mSuccessIv.setVisibility(VISIBLE);
    }

    @Override
    public void onReset() {
        mSuccessIv.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
    }

}
