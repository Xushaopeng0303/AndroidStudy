package com.xsp.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义圆形进度条
 */
public class CircleProgressView extends View {
    private static final int CIRCLE_LINE_OUTER_STROKE_WIDTH = 2;
    private static final int CIRCLE_LINE_INNER_STROKE_WIDTH = 50;
    private static final int CIRCLE_LINE_INNER_DASH_WIDTH   = 5;
    private static final int CIRCLE_LINE_INNER_DASH_MARGIN  = 5;
    private static final int CIRCLE_LINE_MARGIN = CIRCLE_LINE_INNER_STROKE_WIDTH / 2;

    private static final int MAX_PROGRESS = 100;
    private static final int DEFAULT_PROGRESS = 0;

    private static final int TEXT_PROGRESS_MARGIN = 10;
    private static final int TEXT_STROKE_WIDTH = 2;

    /** 实际进度 */
    private int mCurtProgress = DEFAULT_PROGRESS;

    /** 设置圆环的颜色 */
    private int mOuterCircleColor = Color.RED;
    private int mInnerCircleColor = Color.GRAY;
    private int mInnerCircleProgressColor = Color.RED;
    /** 设置圆环的宽度 */
    private int mOuterCircleLineStrokeWidth = CIRCLE_LINE_OUTER_STROKE_WIDTH;
    private int mInnerCircleLineStrokeWidth = CIRCLE_LINE_INNER_STROKE_WIDTH;
    private int mInnerCircleLineDashWidth   = CIRCLE_LINE_INNER_DASH_WIDTH;
    private int mInnerCircleLineDashMargin  = CIRCLE_LINE_INNER_DASH_MARGIN;
    /** 设置两圆环间距 */
    private int mCircleMargin = CIRCLE_LINE_MARGIN;

    /** 文本属性 */
    private int mTextStrokeWidth = TEXT_STROKE_WIDTH;
    private int mTextProgressMargin = TEXT_PROGRESS_MARGIN;
    private String mTextHint;
    private int mProgressColor = Color.BLACK;
    private int mTextHintColor = Color.GRAY;

    /** 画圆所在的距形区域 */
    private final RectF mInnerRectF, mOuterRectF;
    /** 创建画笔对象 */
    private final Paint mInnerPaint, mOuterPaint;
    /** 实际进度虚线 */
    private PathEffect mInnerEffects;

    private OnClickListener mOnClickListener;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInnerRectF = new RectF();
        mOuterRectF = new RectF();
        mInnerPaint = new Paint();
        mOuterPaint = new Paint();
        mInnerEffects = new DashPathEffect(new float[]{mInnerCircleLineDashWidth, mInnerCircleLineDashMargin}, 1);
        this.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width  = this.getWidth();   // 获取View宽度
        int height = this.getHeight();  // 获取View高度

        //如果高度不等于宽度，取最小值  同时对宽高分别设置最小值
        if (width != height) {
            int min = Math.min(width, height);
            width  = min;
            height = min;
        }

        // 设置画笔相关属性
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterCircleColor);
        mOuterPaint.setStrokeWidth(mOuterCircleLineStrokeWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerCircleProgressColor);
        mInnerPaint.setStrokeWidth(mInnerCircleLineStrokeWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setPathEffect(mInnerEffects);     // 设置画笔为虚线

        // 位置
        int edge = mOuterCircleLineStrokeWidth / 2;
        mOuterRectF.left   = edge;           // 左上角x
        mOuterRectF.top    = edge;           // 左上角y
        mOuterRectF.right  = width - edge;   // 左下角x
        mOuterRectF.bottom = height - edge;  // 右下角y

        edge = mOuterCircleLineStrokeWidth + mCircleMargin + mInnerCircleLineStrokeWidth / 2;
        mInnerRectF.left   = edge;
        mInnerRectF.top    = edge;
        mInnerRectF.right  = width - edge;
        mInnerRectF.bottom = height - edge;

        // 绘制两圆
        float percentage = ((float) mCurtProgress / MAX_PROGRESS) * 360;        // 计算进度百分比
        canvas.drawArc(mOuterRectF, -90, 360, false, mOuterPaint);              // 绘制外圆
        if (percentage == 0) {                                                  // 进度为 0
            mInnerPaint.setColor(mInnerCircleColor);
            canvas.drawArc(mInnerRectF, -90, 360, false, mInnerPaint);
        } else if (percentage == 360) {                                         // 进度为 100
            mInnerPaint.setColor(mInnerCircleProgressColor);
            canvas.drawArc(mInnerRectF, -90, 360, false, mInnerPaint);
        } else {                                                                // 进度为 0-100之间
            canvas.drawArc(mInnerRectF, -90, percentage, false, mInnerPaint);   // 绘制内圆 进度条
            mInnerPaint.setColor(mInnerCircleColor);                            // 绘制内圆，进度条背景
            canvas.drawArc(mInnerRectF, percentage - 90, 360 - percentage, false, mInnerPaint);
        }

        // 绘制进度文案显示
        mInnerPaint.setStrokeWidth(mTextStrokeWidth);
        mInnerPaint.setStyle(Paint.Style.FILL);

        String text = mCurtProgress + "%";
        int textHeight = height / 8;
        mInnerPaint.setTextSize(textHeight);
        mInnerPaint.setColor(mProgressColor);
        int textWidth  = (int) mInnerPaint.measureText(text, 0, text.length());
        int baseline = height / 2 + textHeight / 2 - 15;
        canvas.drawText(text, width / 2 - textWidth / 2, baseline, mInnerPaint);

        if (!TextUtils.isEmpty(mTextHint)) {
            textHeight = height / 16;
            mInnerPaint.setTextSize(textHeight);
            mInnerPaint.setColor(mTextHintColor);
            textWidth = (int) mInnerPaint.measureText(mTextHint, 0, mTextHint.length());
            canvas.drawText(mTextHint, width / 2 - textWidth / 2, baseline + textHeight + mTextProgressMargin, mInnerPaint);
        }
    }

    /**
     * 设置外圆环宽度，默认值{@link CircleProgressView#CIRCLE_LINE_OUTER_STROKE_WIDTH}
     */
    public void setOuterCircleLineStrokeWidth(int outerCircleLineStrokeWidth) {
        this.mOuterCircleLineStrokeWidth = outerCircleLineStrokeWidth;
    }

    /**
     * 设置外圈颜色，默认值{@link Color#RED}
     */
    public void setOuterCircleColor(@ColorInt int outerCircleColor) {
        this.mOuterCircleColor = outerCircleColor;
    }

    /**
     * 设置内圆环宽度，默认值{@link CircleProgressView#CIRCLE_LINE_INNER_STROKE_WIDTH}
     */
    public void setInnerCircleLineStrokeWidth(int innerCircleLineStrokeWidth) {
        this.mInnerCircleLineStrokeWidth = innerCircleLineStrokeWidth;
    }

    /**
     * 设置内圆环线的宽度和间距
     * @param width 线宽，默认值{@link CircleProgressView#CIRCLE_LINE_INNER_DASH_WIDTH}
     * @param margin 间距，默认值{@link CircleProgressView#CIRCLE_LINE_INNER_DASH_MARGIN}
     */
    public void setInnerCircleDash(int width, int margin) {
        this.mInnerCircleLineDashWidth = width;
        this.mInnerCircleLineDashMargin = margin;
    }

    /**
     * 设置内圈颜色，默认值{@link Color#GRAY}
     */
    public void setInnerCircleColor(@ColorInt int innerCircleColor) {
        this.mInnerCircleColor = innerCircleColor;
    }

    /**
     * 设置内圈进度颜色，默认值{@link Color#GRAY}
     */
    public void setInnerCircleProgressColor(@ColorInt int innerCircleProgressColor) {
        this.mInnerCircleProgressColor = innerCircleProgressColor;
    }

    /**
     * 设置文本 Hint 和进度文本之间的距离，单位像素，默认值{@link CircleProgressView#TEXT_PROGRESS_MARGIN}
     */
    public void setTextProgressMargin(int margin) {
        this.mTextProgressMargin = margin;
    }

    /**
     * 设置内圆环 和 外圆环间距，默认值{@link CircleProgressView#CIRCLE_LINE_MARGIN}
     */
    public void setCircleLineMargin(int circleMargin) {
        this.mCircleMargin = circleMargin;
    }

    /**
     * 设置 提示文本
     */
    public void setTextHint(String textHint) {
        this.mTextHint = textHint;
    }

    /**
     * 设置 提示文本颜色
     */
    public void setTextHintColor(int textHintColor) {
        this.mTextHintColor = textHintColor;
    }

    /**
     * 设置文本线宽，默认值{@link CircleProgressView#TEXT_STROKE_WIDTH}
     */
    public void setTextStrokeWidth(int width) {
        this.mTextStrokeWidth = width;
    }

    /**
     * 设置 进度文本颜色
     */
    public void setProgressColor(@ColorInt int progressColor) {
        this.mProgressColor = progressColor;
    }

    /**
     * 设置当前进度, 默认值{@link CircleProgressView#DEFAULT_PROGRESS,CircleProgressView#MAX_PROGRESS}
     */
    public void setProgress(int progress) {
        this.mCurtProgress = progress > MAX_PROGRESS ? MAX_PROGRESS : progress;
        this.invalidate();
    }

    /**
     * 获取当前进度, 默认值{@link CircleProgressView#DEFAULT_PROGRESS}
     */
    public int getProgress() {
        return mCurtProgress;
    }

    /**
     * 设置点击监听器
     */
    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

}
