package com.xsp.library.view.recycler.core;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Recycler view standard interface，add or remove header or footer
 */
public interface IRecycler {
    void addHeaderView(@NonNull View headerView);
    void addFooterView(@NonNull View footerView);

    void addHeaderView(@LayoutRes int headerViewRes);
    void addFooterView(@LayoutRes int footerViewRes);

    void removeHeaderView(@NonNull View headerView);
    void removeFooterView(@NonNull View headerView);

    void removeHeader();
    void removeFooter();

    LinearLayout getHeaderParent();
    LinearLayout getFooterParent();
}
