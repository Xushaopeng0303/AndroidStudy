package com.xsp.framework.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsp.framework.R;
import com.xsp.library.fragment.BaseFragment;

/**
 * 通用的空 Fragment
 */
public class EmptyFragment extends BaseFragment {
    private static final String TAG = EmptyFragment.class.getSimpleName();

    public static EmptyFragment getInstance(String content) {
        EmptyFragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, content);
        fragment.setArguments(bundle);
        return fragment;

    }

    public EmptyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_one_text_layout, container, false);
        TextView tv = (TextView) view.findViewById(R.id.id_common_text_show);
        tv.setText(getArguments().getString(TAG));
        return view;
    }
}
