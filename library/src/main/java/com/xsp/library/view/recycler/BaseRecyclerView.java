package com.xsp.library.view.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xsp.library.view.recycler.adapter.WrapperAdapter;
import com.xsp.library.view.recycler.core.IRecycler;

/**
 * Base RecyclerView
 */
public class BaseRecyclerView extends RecyclerView implements IRecycler {
    private LinearLayout mHeaderViewContainer;
    private LinearLayout mFooterViewContainer;

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ensureHeaderViewContainer();
        ensureFooterViewContainer();
    }

    @Override
    public void addHeaderView(View headerView) {
        ensureHeaderViewContainer();
        mHeaderViewContainer.addView(headerView);
        mHeaderViewContainer.setVisibility(VISIBLE);
        Adapter adapter = getAdapter();
        if (null != adapter) {
            adapter.notifyItemChanged(0);
        }

    }

    @Override
    public void addFooterView(View footerView) {
        ensureFooterViewContainer();
        mFooterViewContainer.addView(footerView);
        mHeaderViewContainer.setVisibility(VISIBLE);
        Adapter adapter = getAdapter();
        if (null != adapter) {
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
        }
    }

    @Override
    public void addHeaderView(@LayoutRes int headerViewRes) {
        ensureHeaderViewContainer();
        final View headerView = LayoutInflater.from(getContext()).inflate(headerViewRes, mHeaderViewContainer, false);
        if (null != headerView) {
            addHeaderView(headerView);
        }
    }

    @Override
    public void addFooterView(@LayoutRes int footerViewRes) {
        ensureFooterViewContainer();
        final View footerView = LayoutInflater.from(getContext()).inflate(footerViewRes, mFooterViewContainer, false);
        if (null != footerView) {
            addFooterView(footerView);
        }
    }

    @Override
    public void removeHeaderView(@NonNull View headerView) {

    }

    @Override
    public void removeFooterView(@NonNull View headerView) {

    }

    @Override
    public void removeHeader() {

    }

    @Override
    public void removeFooter() {

    }

    @Override
    public LinearLayout getHeaderParent() {
        ensureHeaderViewContainer();
        return mHeaderViewContainer;
    }

    @Override
    public LinearLayout getFooterParent() {
        ensureFooterViewContainer();
        return mFooterViewContainer;
    }

    public void setIAdapter(Adapter adapter) {
        ensureHeaderViewContainer();
        ensureFooterViewContainer();
        setAdapter(new WrapperAdapter(adapter, mHeaderViewContainer, mFooterViewContainer));
    }

    private void ensureHeaderViewContainer() {
        if (null == mHeaderViewContainer) {
            mHeaderViewContainer = new LinearLayout(getContext());
            mHeaderViewContainer.setOrientation(LinearLayout.VERTICAL);
            mHeaderViewContainer.setLayoutParams(new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void ensureFooterViewContainer() {
        if (null == mFooterViewContainer) {
            mFooterViewContainer = new LinearLayout(getContext());
            mFooterViewContainer.setOrientation(LinearLayout.VERTICAL);
            mFooterViewContainer.setLayoutParams(new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

}
