package com.xsp.library.util.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xsp.library.util.BaseUtil;

import java.util.List;

/**
 * Fragment util
 */
public class FragmentUtil extends BaseUtil {
    /**
     * remove FragmentManager's all fragment
     */
    public static void removeFragment(FragmentManager fm) {
        if (fm == null) {
            return;
        }
        List<Fragment> fragments = fm.getFragments();
        FragmentTransaction transaction = fm.beginTransaction();
        if (transaction == null) {
            return;
        }

        for (Fragment fragment : fragments) {
            if (fragment != null) {
                transaction.remove(fragment);
            }
        }
        transaction.commitAllowingStateLoss();
        fm.executePendingTransactions();
    }
}
