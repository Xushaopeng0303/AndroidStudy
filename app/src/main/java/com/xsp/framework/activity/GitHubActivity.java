package com.xsp.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.xsp.framework.R;
import com.xsp.framework.github.NodeBean;
import com.xsp.framework.github.Node;
import com.xsp.framework.github.SimpleTreeAdapter;
import com.xsp.framework.github.TreeListViewAdapter;
import com.xsp.library.activity.BaseActivity;
import com.xsp.library.activity.BaseWebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * GitHub 开源框架
 */
public class GitHubActivity extends BaseActivity {
    private List<NodeBean> mDataList = new ArrayList<>();
    private TreeListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_view);

        initFirstLevelNode();
        ListView mTree = (ListView) findViewById(R.id.id_common_list_view);
        try {
            mAdapter = new SimpleTreeAdapter(mTree, this, mDataList, 0);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        Intent intent = new Intent(GitHubActivity.this, BaseWebViewActivity.class);
                        intent.putExtra(BaseWebViewActivity.URL_KEY, node.getText());
                        startActivity(intent);
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);

    }

    private void initFirstLevelNode() {
        mDataList.add(new NodeBean(10000, 0, "https://github.com/greenrobot/EventBus"));
        mDataList.add(new NodeBean(10001, 0, "https://github.com/mzule/ActivityRouter"));
        mDataList.add(new NodeBean(10002, 0, "https://github.com/SalomonBrys/ANR-WatchDog"));
        mDataList.add(new NodeBean(10003, 0, "https://github.com/pengjianbo/FloatViewFinal"));
    }


}
