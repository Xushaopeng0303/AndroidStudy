package com.xsp.library.util.ui.view;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.xsp.library.util.BaseUtil;
import com.xsp.library.util.ui.DimenUtil;

/**
 * TextView util
 */
public class TextViewUtil extends BaseUtil {

    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM
    }

    public static void addTextDrawable(TextView tv, int resId, int padding, Direction direction) {
        if (null == tv || resId == 0) {
            return;
        }
        Drawable drawable = tv.getResources().getDrawable(resId);
        if (null == drawable) {
            return;
        }

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawablePadding(DimenUtil.dp2px(padding));
        if (direction == Direction.LEFT) {
            tv.setCompoundDrawables(drawable, null, null, null);
        } else if (direction == Direction.RIGHT) {
            tv.setCompoundDrawables(null, null, drawable, null);
        } else if (direction == Direction.TOP) {
            tv.setCompoundDrawables(null, drawable, null, null);
        } else if (direction == Direction.BOTTOM) {
            tv.setCompoundDrawables(null, null, null, drawable);
        }

        tv.setCompoundDrawables(null, null, null, null);
    }

    public static void setTextTwoColor(TextView tv, int index, int color01, int color02) {
        if (null == tv || index < 0) {
            return;
        }

        Resources res = tv.getContext().getResources();
        int flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        SpannableString ss = new SpannableString(tv.getText().toString());
        ss.setSpan(new ForegroundColorSpan(res.getColor(color01)), 0, index, flag);
        ss.setSpan(new ForegroundColorSpan(res.getColor(color02)), index, tv.getText().length(), flag);
        tv.setText(ss);
    }

    public static void setTextThreeColor(TextView tv, int index01, int index02, int color01, int color02, int color03) {
        if (null == tv || index01 < 0 || index02 < 0) {
            return;
        }
        Resources res = tv.getContext().getResources();
        int flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        SpannableString ss = new SpannableString(tv.getText().toString());
        ss.setSpan(new ForegroundColorSpan(res.getColor(color01)), 0, index01, flag);
        ss.setSpan(new ForegroundColorSpan(res.getColor(color02)), index01, index02, flag);
        ss.setSpan(new ForegroundColorSpan(res.getColor(color03)), index02, tv.getText().length(), flag);
        tv.setText(ss);
    }
}
