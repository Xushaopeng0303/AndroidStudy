package com.xsp.library.view.segment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.xsp.library.R;
import com.xsp.library.util.ui.DimenUtil;


/**
 * Tab 分段
 */
public class TabSegment extends View {

    private String[] mTabTest;
    private Rect[] mCacheBounds;
    private Rect[] mTextBounds;

    private GradientDrawable mBackgroundDrawable;
    private GradientDrawable mSelectedDrawable;

    private int mCurrentIndex;

    private int mTouchSlop;
    private boolean inTapRegion;
    private float mStartX;
    private float mStartY;

    private int mHorizonGap;
    private int mVerticalGap;

    private int mSingleChildWidth;
    private int mSingleChildHeight;

    private Paint mPaint;

    public enum Direction {
        HORIZON(0), VERTICAL(1);

        int mV;

        Direction(int v) {
            mV = v;
        }
    }

    private Direction mDirection;

    private int mTextSize;
    private ColorStateList mColors;
    private int mCornerRadius;

    public interface OnSegmentClickListener {
        void onSegmentControlClick(int index);
    }

    private OnSegmentClickListener mOnSegmentClickListener;

    public TabSegment(Context context) {
        this(context, null);
    }

    public TabSegment(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabSegment(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabSegment);

        String textArray = ta.getString(R.styleable.TabSegment_texts);
        if (textArray != null) {
            mTabTest = textArray.split("\\|");
        }

        mColors = ta.getColorStateList(R.styleable.TabSegment_colors);
        if (mColors == null) {
            mColors = new ColorStateList(new int[][]{{}}, new int[]{0xFF0099CC});
        }

        mTextSize = ta.getDimensionPixelSize(R.styleable.TabSegment_android_textSize, DimenUtil.sp2px(14));
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.TabSegment_cornerRadius, DimenUtil.dp2px(5));
        mDirection = Direction.values()[ta.getInt(R.styleable.TabSegment_direction, Direction.HORIZON.mV)];
        mHorizonGap = ta.getDimensionPixelSize(R.styleable.TabSegment_horizonGap, DimenUtil.dp2px(5));
        mVerticalGap = ta.getDimensionPixelSize(R.styleable.TabSegment_verticalGap, DimenUtil.dp2px(5));
        ta.recycle();

        Log.d("xsp", "TabSegment: 换算" + DimenUtil.dp2px(5));
        Log.d("xsp", "TabSegment: mColors = " + mColors);
        Log.d("xsp", "TabSegment: mTextSize = " + mTextSize);
        Log.d("xsp", "TabSegment: mCornerRadius = " + mCornerRadius);
        Log.d("xsp", "TabSegment: mHorizonGap = " + mHorizonGap);
        Log.d("xsp", "TabSegment: mVerticalGap = " + mVerticalGap);

        mBackgroundDrawable = new GradientDrawable();
        mBackgroundDrawable.setGradientRadius(mCornerRadius);
        mBackgroundDrawable.setColor(0);
        mBackgroundDrawable.setStroke(2, mColors.getDefaultColor());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(mBackgroundDrawable);
        } else {
            setBackground(mBackgroundDrawable);
        }

        mSelectedDrawable = new GradientDrawable();
        mSelectedDrawable.setCornerRadius(mCornerRadius);
        mSelectedDrawable.setColor(mColors.getDefaultColor());

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mColors.getDefaultColor());

        final ViewConfiguration config = ViewConfiguration.get(context);
        int touchSlop = config.getScaledTouchSlop();

        mTouchSlop = touchSlop * touchSlop;
        inTapRegion = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("xsp", "onMeasure: " + widthSize + " ; " + heightSize);

        int width = 0;
        int height = 0;

        if (mTabTest != null && mTabTest.length > 0) {

            if (mCacheBounds == null || mCacheBounds.length != mTabTest.length) {
                mCacheBounds = new Rect[mTabTest.length];
            }

            if (mTextBounds == null || mTextBounds.length != mTabTest.length) {
                mTextBounds = new Rect[mTabTest.length];
            }

            for (int i = 0; i < mTabTest.length; i++) {
                String text = mTabTest[i];

                if (text != null) {

                    if (mTextBounds[i] == null) mTextBounds[i] = new Rect();

                    mPaint.getTextBounds(text, 0, text.length(), mTextBounds[i]);

                    if (mSingleChildWidth < mTextBounds[i].width() + mHorizonGap * 2)
                        mSingleChildWidth = mTextBounds[i].width() + mHorizonGap * 2;
                    if (mSingleChildHeight < mTextBounds[i].height() + mVerticalGap * 2)
                        mSingleChildHeight = mTextBounds[i].height() + mVerticalGap * 2;
                }
            }

            for (int i = 0; i < mTabTest.length; i++) {

                if (mCacheBounds[i] == null) mCacheBounds[i] = new Rect();

                if (mDirection == Direction.HORIZON) {
                    mCacheBounds[i].left = i * mSingleChildWidth;
                    mCacheBounds[i].top = 0;
                } else {
                    mCacheBounds[i].left = 0;
                    mCacheBounds[i].top = i * mSingleChildHeight;
                }

                mCacheBounds[i].right = mCacheBounds[i].left + mSingleChildWidth;
                mCacheBounds[i].bottom = mCacheBounds[i].top + mSingleChildHeight;
            }

            switch (widthMode) {
                case MeasureSpec.AT_MOST:
                    if (mDirection == Direction.HORIZON) {
                        if (widthSize <= mSingleChildWidth * mTabTest.length) {
                            mSingleChildWidth = widthSize / mTabTest.length;
                            width = widthSize;
                        } else {
                            width = mSingleChildWidth * mTabTest.length;
                        }
                    } else {
                        width = widthSize <= mSingleChildWidth ? widthSize : mSingleChildWidth;
                    }
                    break;
                case MeasureSpec.EXACTLY:
                    width = widthSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    if (mDirection == Direction.HORIZON) {
                        width = mSingleChildWidth * mTabTest.length;
                    } else {
                        width = widthSize <= mSingleChildWidth ? widthSize : mSingleChildWidth;
                    }
                    break;
            }

            switch (heightMode) {
                case MeasureSpec.AT_MOST:
                    if (mDirection == Direction.VERTICAL) {
                        if (heightSize <= mSingleChildHeight * mTabTest.length) {
                            mSingleChildHeight = heightSize / mTabTest.length;
                            height = heightSize;
                        } else {
                            height = mSingleChildHeight * mTabTest.length;
                        }
                    } else {
                        height = heightSize <= mSingleChildHeight ? heightSize : mSingleChildHeight;
                    }
                    break;
                case MeasureSpec.EXACTLY:
                    height = heightSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    if (mDirection == Direction.VERTICAL) {
                        height = mSingleChildHeight * mTabTest.length;
                    } else {
                        height = heightSize <= mSingleChildHeight ? heightSize : mSingleChildHeight;
                    }
                    break;
            }

        } else {
            width = widthMode == MeasureSpec.UNSPECIFIED ? 0 : widthSize;
            height = heightMode == MeasureSpec.UNSPECIFIED ? 0 : heightSize;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("xsp", "onTouchEvent: " + event.getAction());

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                inTapRegion = true;
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float mCurrentX = event.getX();
                float mCurrentY = event.getY();

                int dx = (int) (mCurrentX - mStartX);
                int dy = (int) (mCurrentY - mStartY);

                int distance = dx * dx + dy * dy;

                if (distance > mTouchSlop) {
                    inTapRegion = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (inTapRegion) {
                    int index = 0;
                    if (mDirection == Direction.HORIZON) {
                        index = (int) (mStartX / mSingleChildWidth);
                    } else {
                        index = (int) (mStartY / mSingleChildHeight);
                    }

                    if (mOnSegmentClickListener != null)
                        mOnSegmentClickListener.onSegmentControlClick(index);

                    mCurrentIndex = index;

                    invalidate();
                }
                break;
        }

        return true;
    }

    public void setSelectedIndex(int index) {
        mCurrentIndex = index;

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.d("xsp", "onDraw");
        super.onDraw(canvas);

        if (mTabTest != null && mTabTest.length > 0) {
            for (int i = 0; i < mTabTest.length; i++) {

                //draw separate lines
                if (i < mTabTest.length - 1) {
                    mPaint.setColor(mColors.getDefaultColor());
                    if (mDirection == Direction.HORIZON) {
                        canvas.drawLine(mCacheBounds[i].right, 0, mCacheBounds[i].right, getHeight(), mPaint);
                    } else {
                        canvas.drawLine(mCacheBounds[i].left, mSingleChildHeight * (i + 1), mCacheBounds[i].right, mSingleChildHeight * (i + 1), mPaint);
                    }
                }

                //draw selected drawable
                if (i == mCurrentIndex && mSelectedDrawable != null) {
                    int topLeftRadius = 0;
                    int topRightRadius = 0;
                    int bottomLeftRadius = 0;
                    int bottomRightRadius = 0;

                    if (mDirection == Direction.HORIZON) {
                        if (i == 0) {
                            topLeftRadius = mCornerRadius;
                            bottomLeftRadius = mCornerRadius;
                        } else if (i == mTabTest.length - 1) {
                            topRightRadius = mCornerRadius;
                            bottomRightRadius = mCornerRadius;
                        }
                    } else {
                        if (i == 0) {
                            topLeftRadius = mCornerRadius;
                            topRightRadius = mCornerRadius;
                        } else if (i == mTabTest.length - 1) {
                            bottomLeftRadius = mCornerRadius;
                            bottomRightRadius = mCornerRadius;
                        }
                    }

                    float[] radius = {topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius};
                    mSelectedDrawable.setCornerRadii(radius);
                    mSelectedDrawable.setBounds(mCacheBounds[i]);


                    mPaint.setColor(0xFFFFFFFF);
                } else {
                    mPaint.setColor(mColors.getDefaultColor());
                }

                //draw texts
                canvas.drawText(mTabTest[i], mCacheBounds[i].left + (mSingleChildWidth - mTextBounds[i].width()) / 2, mCacheBounds[i].top + ((mSingleChildHeight + mTextBounds[i].height()) / 2), mPaint);
            }
        }

    }


    public void setOnSegmentClickListener(OnSegmentClickListener onSegmentClickListener) {
        mOnSegmentClickListener = onSegmentClickListener;
    }

    public void setText(String... texts) {
        mTabTest = texts;

        if (mTabTest != null) {
            requestLayout();
        }
    }

    public void setColors(ColorStateList colors) {
        mColors = colors;

        if (mBackgroundDrawable != null) {
            mBackgroundDrawable.setColor(colors.getDefaultColor());
        }

        if (mSelectedDrawable != null) {
            mSelectedDrawable.setColor(colors.getDefaultColor());
        }

        mPaint.setColor(colors.getDefaultColor());

        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        mCornerRadius = cornerRadius;

        if (mBackgroundDrawable != null) {
            mBackgroundDrawable.setCornerRadius(cornerRadius);
        }

        invalidate();
    }

    public void setDirection(Direction direction) {
        Direction tDirection = mDirection;

        mDirection = direction;

        if (tDirection != direction) {
            requestLayout();
            invalidate();
        }
    }

    public void setTextSize(int textSize) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setTextSize(int unit, int textSize) {
        mPaint.setTextSize((int) (TypedValue.applyDimension(unit, textSize, getContext().getResources().getDisplayMetrics())));

        if (textSize != mTextSize) {
            mTextSize = textSize;
            requestLayout();
        }
    }
}