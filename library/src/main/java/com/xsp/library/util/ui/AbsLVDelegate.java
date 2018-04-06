package com.xsp.library.util.ui;

import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AbsLVDelegate extends AdapterDelegate {

    private AbsListView mAbsListView;

    public AbsLVDelegate(AbsListView absListView) {
        this.mAbsListView = absListView;
    }

    @Override
    public ViewGroup getDepositor() {
        return mAbsListView;
    }

    @Override
    public int getHeaderViewsCount() {
        if (mAbsListView instanceof ListView) {
            ListView listview = (ListView) mAbsListView;
            return listview.getHeaderViewsCount();
        }
        return 0;
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        ListAdapter adapter = mAbsListView.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }

}
