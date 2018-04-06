package com.xsp.library.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xsp.library.R;

/**
 * 使用系统 shader 实现简单发光文字
 */
public class LoomingTextView extends TextView {

    /** 起色 */
    private static final int COLOR_START = 0x60000000;
    /** 中色 */
    private static final int COLOR_MIDDLE = 0xff000000;
    /** 尾色 */
    private static final int COLOR_END = 0x60000000;

    /** 刷新周期 ms */
    private static final int CYCLE_REFRESH = 40;
    /** 步长因子dp */
    private static final int STEP = 5;

    /** 渐变色 */
    private int [] mGradientColor = new int[]{COLOR_START, COLOR_MIDDLE, COLOR_END};
    private int mRefreshFrequency = CYCLE_REFRESH;

    /** matrix */
    private Matrix mShaderMatrix;
    /** 线性渐变 */
    private LinearGradient mLinearGradient;
    /** 宽暂存，计算looming位置 */
    private int mWidth = 0;
    /** 移动的当前位移 */
    private int mTranslate = 0;
    /** 步长，像素 */
    private int mStepPX = 0;

    public LoomingTextView(Context context) {
        this(context, null);
    }

    public LoomingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mStepPX = (int) (context.getResources().getDisplayMetrics().density * STEP);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoomingTextView);

        int attrNum = typedArray.getIndexCount();
        for (int i = 0; i < attrNum; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.LoomingTextView_startColor) {
                mGradientColor[0] = typedArray.getColor(attr, COLOR_START);
            } else if (attr == R.styleable.LoomingTextView_middleColor) {
                mGradientColor[1] = typedArray.getColor(attr, COLOR_MIDDLE);
            } else if (attr == R.styleable.LoomingTextView_endColor) {
                mGradientColor[2] = typedArray.getColor(attr, COLOR_END);
            } else if (attr == R.styleable.LoomingTextView_refreshFrequency) {
                mRefreshFrequency = typedArray.getInt(attr, CYCLE_REFRESH);
            }
        }

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShaderMatrix != null) {
            mTranslate += mWidth / mStepPX; // 宽度，像素值
            if (mTranslate > 2 * mWidth) {
                mTranslate = -mWidth;
            }
            mShaderMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mShaderMatrix);
            postInvalidateDelayed(mRefreshFrequency);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth == 0) {
            mWidth = getMeasuredWidth();
            if (mWidth > 0) {
                mLinearGradient = new LinearGradient(-mWidth, 0, 0, 0, mGradientColor,
                        new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
                getPaint().setShader(mLinearGradient);
                mShaderMatrix = new Matrix();
            }
        }
    }
}