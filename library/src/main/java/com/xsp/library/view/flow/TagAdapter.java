package com.xsp.library.view.flow;

import android.view.View;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {

    private List<T> mTagDataList;
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<>();

    protected TagAdapter(List<T> dataList) {
        mTagDataList = dataList;
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... pos) {
        for (Integer temp : pos) {
            mCheckedPosList.add(temp);
        }
        notifyDataChanged();
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        mCheckedPosList.addAll(set);
        notifyDataChanged();
    }

    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    int getCount() {
        return mTagDataList == null ? 0 : mTagDataList.size();
    }

    T getItem(int position) {
        return (mTagDataList == null || position > mTagDataList.size()) ? null : mTagDataList.get(position);
    }

    boolean setSelected(int position, T t) {
        return false;
    }

    private void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public abstract View getView(FlowLayout parent, int position, T t);


}
