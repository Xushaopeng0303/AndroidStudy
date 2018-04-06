package com.xsp.library.util.ui;

import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public abstract class AdapterDelegate {

    public abstract ViewGroup getDepositor();

    public static AdapterDelegate generateDelegate(AdapterView adapterView) {
        if (adapterView instanceof AbsListView) {
            return new AbsLVDelegate((AbsListView) adapterView);
        } /*else if (adapterView instanceof HorizontalListView) {
            return new HorLVDelegate((HorizontalListView) adapterView);
        }*/
        return null;
    }

    public abstract int getHeaderViewsCount();

    public abstract ListAdapter getWrappedAdapter();

}
