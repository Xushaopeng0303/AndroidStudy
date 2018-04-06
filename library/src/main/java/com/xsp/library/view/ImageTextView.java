package com.xsp.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xsp.library.R;


/**
 * 首页Tab标签
 *
 * @author xsp
 */
public class ImageTextView extends View {
    private static final int ICON_SELECTED_COLOR = 0xFF45C01A;
    private static final int TEXT_DEFAULT_COLOR = 0xFF555555;

    /**
     * 内存中绘图属性
     */
    private Bitmap mBitmap;

    // 透明度 0.0-1.0
    private float mAlpha = 0f;
    // 限制绘制icon的范围
    private Rect mIconRect;
    private Paint mTextPaint;
    private Rect mTextBound = new Rect();

    /**
     * 下面四个是自定属性的
     */
    private Bitmap mIcon;                           // 图标
    private String mText;                           // 文本
    private int mIconColor = ICON_SELECTED_COLOR;   // 颜色 先设置默认的
    private int mTextSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

    /**
     * 代码中创建对象的时候调用
     */
    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, Drawable drawable, String str) {
        super(context);

        mIcon = ((BitmapDrawable) drawable).getBitmap();
        mText = str;

        initTextPaint();
    }

    /**
     * XML文件创建对象的时候调用
     */
    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // TypedArray存放属性的数组，在使用完成后一定要调用recycle方法
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconView);

        int attrNum = typedArray.getIndexCount();       // 获取属性的个数
        for (int i = 0; i < attrNum; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ChangeColorIconView_c_icon) {
                BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(attr);
                mIcon = drawable.getBitmap();
            } else if (attr == R.styleable.ChangeColorIconView_c_color) {
                mIconColor = typedArray.getColor(attr, ICON_SELECTED_COLOR);
            } else if (attr == R.styleable.ChangeColorIconView_c_text) {
                mText = typedArray.getString(attr);
            } else if (attr == R.styleable.ChangeColorIconView_c_text_size) {
                mTextSize = (int) typedArray.getDimension(attr, TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                                getResources().getDisplayMetrics()));
            }
        }

        typedArray.recycle();               // 回收掉

        initTextPaint();
    }

    private void initTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(TEXT_DEFAULT_COLOR);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);  // 得到Text绘制范围
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Icon 图标的宽度 和 高度
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height();
        int iconWidth = Math.min(width, height);

        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth / 2;
        // 设置Icon的绘制范围
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画笔的alpha是0-255 是整数 所以转为int (向上取整)
        int alpha = (int) Math.ceil((255 * mAlpha));
        // 绘制原图
        canvas.drawBitmap(mIcon, null, mIconRect, null);    // 绘制原图
        setupTargetBitmap(alpha);                           // 绘制可变Icon
        drawSourceText(canvas, alpha);                      // 绘制源文本 当前view上
        drawTargetText(canvas, alpha);                      // 绘制变色的文本
        // 上面两个方法里面的 alpha  一个mTextPaint.setAlpha(255 - alpha); 一个 mTextPaint.setAlpha(alpha);
        // 一个越来越透明 那么另外一个就越来越不透明
        // 最后把内存中两者的交集绘制出啦  覆盖掉上面绘制的原图
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 在内存中绘制可变的icon，这里绘制了一个纯绿色的图标
     *
     * @param alpha    透明度
     */
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setColor(mIconColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);

        // canvas原有的图片 可以理解为背景 就是dst 新画上去的图片 可以理解为前景 就是src
        // 我们知道 在正常的情况下，在已有的图像上绘图将会在其上面添加一层新的形状。 如果新的Paint是完全不透明的，那么它将完全遮挡住下面的Paint；
        // 而setXfermode就可以来解决这个问题
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);       // 设回来 设成不透明 也就是画笔是绿色的
        mCanvas.drawBitmap(mIcon, null, mIconRect, mPaint);     // 把icon绘制上去
    }

    /**
     * 绘制原文本到当前View 上
     *
     * @param canvas        画布
     * @param alpha         透明度
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(TEXT_DEFAULT_COLOR);
        mTextPaint.setAlpha(255 - alpha);
        float x = mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2;
        float y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制变色的  文本到当前View上
     *
     * @param canvas        画布
     * @param alpha         透明度
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mIconColor);
        mTextPaint.setAlpha(alpha);
        float x = mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2;
        float y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 设置颜色值
     */
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.myLooper() == Looper.getMainLooper()) {  // 判断当前线程是否是UI线程(主线程)
            // invalidate()必须工作在主线程，谁请求绘制谁，是View的话，只绘制该View ；是ViewGroup，则绘制整个 ViewGroup
            invalidate();
        } else {                                            // 当前是子线程的时候，直接刷新界面就可以
            postInvalidate();
        }
    }

    /**
     * 设置Icon的颜色
     */
    public void setIconColor(int color) {
        mIconColor = color;
    }

    /**
     * 设置Icon资源
     */
    public void setIcon(int resId) {
        this.mIcon = BitmapFactory.decodeResource(getResources(), resId);
        if (mIconRect != null) {
            invalidateView();
        }
    }

    /**
     * 设置Icon
     */
    public void setIcon(Bitmap iconBitmap) {
        this.mIcon = iconBitmap;
        if (mIconRect != null) {
            invalidateView();
        }
    }

    private static final String INSTANCE_STATE = "instance_state";
    private static final String STATE_ALPHA = "state_alpha";

    /**
     * 长期后台运行或者旋转屏幕时会重建Activity
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(STATE_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }


}
