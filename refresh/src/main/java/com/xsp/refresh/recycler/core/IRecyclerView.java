package com.xsp.refresh.recycler.core;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Recycler view standard
 */
public interface IRecyclerView {
    void addHeaderView(View headerView);
    void addFooterView(View footerView);

    void addHeaderView(@LayoutRes int headerViewRes);
    void addFooterView(@LayoutRes int footerViewRes);

    LinearLayout getHeaderParent();
    LinearLayout getFooterParent();
}
