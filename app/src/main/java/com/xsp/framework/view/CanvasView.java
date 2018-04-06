package com.xsp.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xsp.framework.R;
import com.xsp.library.util.ui.DimenUtil;

/**
 * 画布 学习。
 */
public class CanvasView extends View {
    private static final int VERTICAL_MARGIN = 30;
    private static final int AREA_HEIGHT = 220;
    private static final int LEFT = 20;
    /** 实际进度虚线 */
    private PathEffect mInnerEffects;

    /** 通用画笔 */
    private final Paint mBasicPaint;
    /** 画矩形用 */
    private final RectF mRectF;
    private final Rect mRect;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mBasicPaint = new Paint();
        mRectF = new RectF();
        mRect = new Rect();

        mInnerEffects = new DashPathEffect(new float[]{LEFT, 10}, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();   // 获取View宽度
        int height = this.getHeight();  // 获取View高度
        Log.d("xsp", "onDraw: 获取View在XML中设置的宽度：" + width + " ; 高度：" + height);

        // 设置画布背景
        canvas.drawColor(Color.LTGRAY);

        // 初始化画笔
        mBasicPaint.setAntiAlias(true);
        mBasicPaint.setColor(Color.RED);
        mBasicPaint.setStyle(Paint.Style.FILL);
        mBasicPaint.setStrokeWidth(3);


        /******************************** 画点 ********************************/
        int yHeight = VERTICAL_MARGIN;
        canvas.drawPoint(LEFT, yHeight, mBasicPaint);         // 画单点

        yHeight += VERTICAL_MARGIN;
        float []pts1 = {LEFT, yHeight, LEFT * 2, yHeight, LEFT * 3, yHeight, LEFT * 4, yHeight, LEFT * 5, yHeight, LEFT * 6, yHeight, LEFT * 7, yHeight, LEFT * 8, yHeight, LEFT * 9, yHeight, LEFT * 10, yHeight};
        canvas.drawPoints(pts1, mBasicPaint);                 // 画多点


        /******************************** 画线 ********************************/
        yHeight += VERTICAL_MARGIN;
        canvas.drawLine(LEFT, yHeight, width - LEFT, yHeight, mBasicPaint);  // 画单线

        yHeight += VERTICAL_MARGIN;
        mBasicPaint.setPathEffect(mInnerEffects);
        canvas.drawLine(LEFT, yHeight, width - LEFT, yHeight, mBasicPaint);  // 画虚线

        yHeight += VERTICAL_MARGIN;
        float []pts2 = {LEFT, yHeight, width / 2 - LEFT / 2, yHeight, width / 2 + LEFT / 2, yHeight, width - LEFT, yHeight};
        mBasicPaint.setPathEffect(null);
        canvas.drawLines(pts2, mBasicPaint);                                 // 画多线


        /******************************** 矩形 ********************************/
        yHeight += VERTICAL_MARGIN;
        mBasicPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(LEFT, yHeight, width / 3 - LEFT, yHeight + AREA_HEIGHT, mBasicPaint);

        mRectF.left = width / 2 - AREA_HEIGHT / 2;
        mRectF.top = yHeight;
        mRectF.right = width / 2 + AREA_HEIGHT / 2;
        mRectF.bottom = yHeight + AREA_HEIGHT;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        mBasicPaint.setPathEffect(mInnerEffects);
        canvas.drawRect(mRectF, mBasicPaint);

        mRect.left = width / 3 * 2 + LEFT;
        mRect.top = yHeight;
        mRect.right = width - LEFT;
        mRect.bottom = yHeight + AREA_HEIGHT;
        mBasicPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBasicPaint.setPathEffect(null);
        canvas.drawRect(mRect, mBasicPaint);


        /****************************** 圆角矩形 ******************************/
        yHeight += AREA_HEIGHT + VERTICAL_MARGIN;
        mRectF.left = LEFT;
        mRectF.top = yHeight;
        mRectF.right = width / 2 - LEFT;
        mRectF.bottom = yHeight + AREA_HEIGHT;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(mRectF, LEFT, LEFT / 2, mBasicPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBasicPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(width / 2 + LEFT / 2, yHeight, width - LEFT / 2, yHeight + AREA_HEIGHT, LEFT, LEFT, mBasicPaint);
        }


        /******************************** 圆形 ********************************/
        yHeight += AREA_HEIGHT + VERTICAL_MARGIN;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 6, yHeight + AREA_HEIGHT / 2, AREA_HEIGHT / 2, mBasicPaint);


        /******************************* 椭圆形 *******************************/
        mRectF.left = width / 3 + LEFT;
        mRectF.top = yHeight + VERTICAL_MARGIN;
        mRectF.right = width / 3 * 2 - LEFT;
        mRectF.bottom = yHeight + AREA_HEIGHT - VERTICAL_MARGIN;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(mRectF, mBasicPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBasicPaint.setStyle(Paint.Style.STROKE);
            mBasicPaint.setPathEffect(mInnerEffects);
            canvas.drawOval(width / 3 * 2 + LEFT, yHeight + VERTICAL_MARGIN, width - LEFT, yHeight + AREA_HEIGHT - VERTICAL_MARGIN, mBasicPaint);
        }


        /******************************** 弧线 ********************************/
        yHeight += AREA_HEIGHT + VERTICAL_MARGIN;
        mRectF.left = LEFT;
        mRectF.top = yHeight;
        mRectF.right = width / 3 - LEFT;
        mRectF.bottom = yHeight + AREA_HEIGHT;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        mBasicPaint.setPathEffect(null);
        canvas.drawArc(mRectF, -90, 180, false, mBasicPaint);

        mRectF.left = width / 3 + LEFT;
        mRectF.top = yHeight;
        mRectF.right = width / 3 * 2 - LEFT;
        mRectF.bottom = yHeight + AREA_HEIGHT;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, -135, 90, true, mBasicPaint);

        mRectF.left = width / 3 * 2 + LEFT;
        mRectF.top = yHeight;
        mRectF.right = width - LEFT;
        mRectF.bottom = yHeight + AREA_HEIGHT;
        mBasicPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, -60, 300, false, mBasicPaint);


        /******************************** 文本 ********************************/
        yHeight += AREA_HEIGHT + VERTICAL_MARGIN;
        String text = "I Love China 我爱中国";
        mBasicPaint.setAntiAlias(true);
        mBasicPaint.setColor(getResources().getColor(R.color.black));
        mBasicPaint.setStyle(Paint.Style.STROKE);
        mBasicPaint.setTextSize(DimenUtil.dp2px(20));
        mBasicPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int baseline = yHeight + DimenUtil.dp2px(20);
        int textWidth = (int) mBasicPaint.measureText(text, 0, text.length());
        canvas.drawText(text, width / 2 - textWidth / 2, baseline, mBasicPaint);
    }


}
