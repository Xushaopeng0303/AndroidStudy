package com.xsp.library.util.ui;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewBean {

    /**
     * {@link AbsListView}
     */
    // invalidate ListView's child view in spec position
    public static void updateView(AdapterView absListView, int position) {
        if (position < absListView.getFirstVisiblePosition()
                || position > absListView.getLastVisiblePosition()) {
            return;
        }

        View child = absListView.getChildAt(position - absListView.getFirstVisiblePosition());
        absListView.getAdapter().getView(position, child, absListView);
    }

    // generate view
    public static View getView(AdapterView absListView, int position) {
        Adapter adapter = absListView.getAdapter();
        if (adapter != null && position >= 0 && position < adapter.getCount()) {
            return adapter.getView(position, null, absListView);
        }
        return null;
    }

    public static int getHeaderViewsCount(AdapterView absListView) {
        AdapterDelegate delegate = AdapterDelegate.generateDelegate(absListView);
        return delegate.getHeaderViewsCount();
    }

    public static ListAdapter getWrappedAdapter(AdapterView listView) {
        AdapterDelegate delegate = AdapterDelegate.generateDelegate(listView);
        return delegate.getWrappedAdapter();
    }

    /**
     * {@link Fragment}
     */
    public static Fragment replaceFragment(FragmentActivity fa, int id, Class<?> clz, Bundle data, int[] anim) {
        try {
            Constructor constructor = clz.getConstructor();
            try {
                FragmentManager manager = fa.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = (Fragment) constructor.newInstance();
                if (data != null) {
                    fragment.setArguments(data);
                }
                if (anim != null) {
                    transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3]);
                }
                transaction.replace(id, fragment);
                transaction.commitAllowingStateLoss();
                return fragment;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removeFragment(FragmentActivity fa, int id, int[] anim) {
        FragmentManager manager = fa.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(id);
        FragmentTransaction transaction = manager.beginTransaction();
        if (anim != null) {
            transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3]);
        }
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
        manager.executePendingTransactions();
    }

    /**
     * {@link ViewGroup}
     */
    public static final int NOT_CHANGE = -10;

    public static void updateDimen(View view, int w, int h) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }

        boolean changed = false;
        if (w != NOT_CHANGE && params.width != w) {
            changed = true;
            params.width = w;
        }
        if (h != NOT_CHANGE && params.height != h) {
            changed = true;
            params.height = h;
        }

        if (changed) {
            view.setLayoutParams(params);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                view.setMinimumWidth(params.width);
                view.setMinimumHeight(params.height);
            }
        }
    }

    public static void updateMargin(View view, int l, int t, int r, int b) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }

        if (params instanceof RelativeLayout.LayoutParams) {
            updateMarginImpl(view, (RelativeLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof LinearLayout.LayoutParams) {
            updateMarginImpl(view, (LinearLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof FrameLayout.LayoutParams) {
            updateMarginImpl(view, (FrameLayout.LayoutParams) params, l, t, r, b);
        }
    }

    private static void updateMarginImpl(View view, ViewGroup.MarginLayoutParams params, int l, int t, int r, int b) {
        boolean changed = false;
        if (l != NOT_CHANGE && params.leftMargin != l) {
            changed = true;
            params.leftMargin = l;
        }
        if (t != NOT_CHANGE && params.topMargin != t) {
            changed = true;
            params.topMargin = t;
        }
        if (r != NOT_CHANGE && params.rightMargin != r) {
            changed = true;
            params.rightMargin = r;
        }
        if (b != NOT_CHANGE && params.bottomMargin != b) {
            changed = true;
            params.bottomMargin = b;
        }

        if (changed) {
            view.setLayoutParams(params);
        }
    }

    public static void updatePadding(View view, int l, int t, int r, int b) {
        if (view == null) {
            return;
        }

        if (l == NOT_CHANGE) {
            l = view.getPaddingLeft();
        }
        if (t == NOT_CHANGE) {
            t = view.getPaddingTop();
        }
        if (r == NOT_CHANGE) {
            r = view.getPaddingRight();
        }
        if (b == NOT_CHANGE) {
            b = view.getPaddingBottom();
        }
        view.setPadding(l, t, r, b);
    }

    /**
     * {@link TextView}
     */
    public static int getLineLeaveWidth(TextView textView, int textWidth, String str) {
        StaticLayout layout = new StaticLayout(str, textView.getPaint(), textWidth,
                Layout.Alignment.ALIGN_NORMAL, 0, 0, true);
        float min = Integer.MAX_VALUE;
        for (int i = 0; i < layout.getLineCount() - 1; i++) {
            String lineStr = str.substring(layout.getLineStart(i), layout.getLineEnd(i));
            float lineWidth = textView.getPaint().measureText(lineStr);
            float temp = textWidth - lineWidth;
            min = min < temp ? min : temp;
        }
        return (int) min;
    }

    public static float measureTextWidth(TextView textView, String text) {
        return textView.getPaint().measureText(text);
    }

    public static float measureTextHeight(TextView textView) {
        Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    /**
     * get blank string with measuredLength
     */
    public static StringBuilder getReplaceBlank(float measuredLength, TextView textView) {
        int count = (int) Math.ceil(measuredLength / ViewBean.measureTextWidth(textView, " "));
        StringBuilder sb = new StringBuilder();
        while (count-- > 0) {
            sb.append(" ");
        }
        return sb;
    }

    /**
     * Notice: not support {@link String} contains '\r\n' or '\n'
     */
    public static String getMaxLinesText(TextView textView, int textWidth, String str, int maxLines) {
        if (textWidth <= 0 || TextUtils.isEmpty(str) || maxLines < 2) {
            return str;
        }

        str = str.trim();
        if (str.length() == 0) {
            return str;
        }

        StaticLayout layout = new StaticLayout(str, textView.getPaint(), textWidth,
                Layout.Alignment.ALIGN_NORMAL, 0, 0, true);
        int lineCount = layout.getLineCount();
        if (lineCount > maxLines) {
            int lineStart = layout.getLineStart(maxLines - 1);
            int lineEnd = layout.getLineEnd(maxLines - 1) - 1;
            if (str.charAt(lineEnd) == '\n') {
                return str.substring(0, lineEnd);
            }

            for (int i = lineEnd; i < str.length(); i++) {
                String lineStr = str.substring(lineStart, i + 1);
                int restWidth = (int) (textWidth - measureTextWidth(textView, lineStr));
                if (restWidth <= 0) {
                    lineEnd = --i;
                    break;
                }
            }
            return str.substring(0, lineEnd - 1) + "...";
        } else {
            return str;
        }
    }

    /**
     * InputMethod
     */
    public static void showInputMethod(final View view, final boolean show, long delayTime) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                InputMethodManager mImm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (show) {
                    mImm.showSoftInput(view, 0);
                } else {
                    mImm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        };

        if (delayTime > 0) {
            view.postDelayed(runnable, delayTime);
        } else {
            runnable.run();
        }
    }

    /**
     * View
     */
    private static View.OnClickListener mEmptyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    public static View.OnClickListener getEmptyListener() {
        return mEmptyListener;
    }

}
