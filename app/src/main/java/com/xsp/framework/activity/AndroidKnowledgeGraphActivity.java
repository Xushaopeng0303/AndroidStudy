package com.xsp.framework.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.xsp.framework.R;
import com.xsp.framework.github.Node;
import com.xsp.framework.github.NodeBean;
import com.xsp.framework.github.SimpleTreeAdapter;
import com.xsp.framework.github.TreeListViewAdapter;
import com.xsp.library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Android 知识图谱
 */
public class AndroidKnowledgeGraphActivity extends BaseActivity {
    private List<NodeBean> mDataList = new ArrayList<>();
    private TreeListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_view);

        initAndroidDev();
        initAndroidBasic();
        initPerformance();
        initDataPersistence();
        initCaseTest();
        initAdvanced();
        initPlugin();
        initHotFix();
        initJVM();
        initApkResolve();
        initAnimation();
        initIpc();
        initDesignPattern();
        initOpenSource();
        ListView mTree = (ListView) findViewById(R.id.id_common_list_view);
        try {
            mAdapter = new SimpleTreeAdapter(mTree, this, mDataList, 0);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        Toast.makeText(getApplicationContext(), node.getText(), Toast.LENGTH_SHORT).show();
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);

    }

    /**
     * 开发工具 10
     */
    private void initAndroidDev() {
        mDataList.add(new NodeBean(100000, 0, "开发工具"));

        mDataList.add(new NodeBean(101000, 100000, "Eclipse"));
        mDataList.add(new NodeBean(102000, 100000, "Android Studio"));
        mDataList.add(new NodeBean(103000, 100000, "Beyond Compare"));
        mDataList.add(new NodeBean(104000, 100000, "JD-JUI"));

        mDataList.add(new NodeBean(102100, 102000, "adb"));
        mDataList.add(new NodeBean(102200, 102000, "Logcat"));
        mDataList.add(new NodeBean(102300, 102000, "Debugger"));
        mDataList.add(new NodeBean(102400, 102000, "TraceView"));
        mDataList.add(new NodeBean(102500, 102000, "HierarchyViewer"));
        mDataList.add(new NodeBean(102600, 102000, "Lint"));
        mDataList.add(new NodeBean(102700, 102000, "Heap"));
    }

    /**
     * 基础知识 11
     */
    private void initAndroidBasic() {
        mDataList.add(new NodeBean(110000, 0, "基础知识"));

        mDataList.add(new NodeBean(111000, 110000, "四大组件"));
        mDataList.add(new NodeBean(112000, 110000, "Fragment"));
        mDataList.add(new NodeBean(113000, 110000, "Event 事件"));
        mDataList.add(new NodeBean(114000, 110000, "View 绘制"));
        mDataList.add(new NodeBean(115000, 110000, "消息分发"));

        // 四大组件
        mDataList.add(new NodeBean(111100, 111000, "Activity"));
        mDataList.add(new NodeBean(111200, 111000, "Service"));
        mDataList.add(new NodeBean(111300, 111000, "BroadcastReceiver"));
        mDataList.add(new NodeBean(111400, 111000, "ContentProvider"));

        // Event
        mDataList.add(new NodeBean(113001, 113000, "onTouch"));
        mDataList.add(new NodeBean(113002, 113000, "onTouchEvent"));
        mDataList.add(new NodeBean(113003, 113000, "onClick"));
        mDataList.add(new NodeBean(113004, 113000, "Event 处理流程"));

        // View
        mDataList.add(new NodeBean(114001, 114000, "onMeasure"));
        mDataList.add(new NodeBean(114002, 114000, "onLayout"));
        mDataList.add(new NodeBean(114003, 114000, "onDraw"));
        mDataList.add(new NodeBean(114004, 114000, "View 绘制流程"));

        // 消息分发
        mDataList.add(new NodeBean(115001, 115000, "Handler"));
        mDataList.add(new NodeBean(115002, 115000, "AsyncTask"));
        mDataList.add(new NodeBean(115003, 115000, "Scheme"));
        mDataList.add(new NodeBean(115004, 115000, "Android 消息处理机制"));

        // Service
        mDataList.add(new NodeBean(111201, 111200, "生命周期"));
        mDataList.add(new NodeBean(111202, 111200, "调用时机"));
        mDataList.add(new NodeBean(111203, 111200, "进程保活"));
        mDataList.add(new NodeBean(111204, 111200, "IntentService 的内部实现"));
        mDataList.add(new NodeBean(111205, 111200, "Service 调用的优缺点"));

        // BroadcastReceiver
        mDataList.add(new NodeBean(111301, 111300, "动态注册"));
        mDataList.add(new NodeBean(111302, 111300, "静态注册"));
        mDataList.add(new NodeBean(111303, 111300, "动态注册和静态注册优缺点"));
        mDataList.add(new NodeBean(111304, 111300, "广播类型"));
        mDataList.add(new NodeBean(111305, 111300, "本地广播"));
        mDataList.add(new NodeBean(111306, 111300, "广播拦截"));
    }

    /**
     * 性能优化 12
     */
    private void initPerformance() {
        mDataList.add(new NodeBean(120000, 0, "性能优化"));

        mDataList.add(new NodeBean(121000, 120000, "ANR"));
        mDataList.add(new NodeBean(122000, 120000, "Crash"));
        mDataList.add(new NodeBean(123000, 120000, "卡顿"));
        mDataList.add(new NodeBean(124000, 120000, "过度绘制"));
        mDataList.add(new NodeBean(125000, 120000, "流量优化"));
        mDataList.add(new NodeBean(126000, 120000, "电量优化"));
        mDataList.add(new NodeBean(127000, 120000, "内存泄露-LeakCanary"));
        mDataList.add(new NodeBean(128000, 120000, "精简层级-HierarchyView"));
    }

    /**
     * 数据持久化 13
     */
    private void initDataPersistence() {
        mDataList.add(new NodeBean(130000, 0, "数据持久化"));

        mDataList.add(new NodeBean(131000, 130000, "SP"));
        mDataList.add(new NodeBean(132000, 130000, "Internal Storage"));
        mDataList.add(new NodeBean(133000, 130000, "External Storage"));
        mDataList.add(new NodeBean(134000, 130000, "SQLite"));
        mDataList.add(new NodeBean(135000, 130000, "ContentProvider"));

        mDataList.add(new NodeBean(134100, 134000, "增删改查"));
        mDataList.add(new NodeBean(134100, 134000, "DB 事务处理"));
        mDataList.add(new NodeBean(134100, 134000, "批量插入"));
    }

    /**
     * 用例自测 14
     */
    private void initCaseTest() {
        mDataList.add(new NodeBean(140000, 0, "用例自测"));

        mDataList.add(new NodeBean(141000, 140000, "Monkey"));
        mDataList.add(new NodeBean(142000, 140000, "Capture"));
        mDataList.add(new NodeBean(143000, 140000, "Xmind"));
        mDataList.add(new NodeBean(144000, 140000, "Junit"));
    }

    /**
     * 高级进阶 50
     */
    private void initAdvanced() {
        mDataList.add(new NodeBean(500000, 0, "高级进阶"));

        mDataList.add(new NodeBean(501000, 500000, "自定义 View"));
        mDataList.add(new NodeBean(502000, 500000, "数据结构"));
        mDataList.add(new NodeBean(503000, 500000, "系统架构"));
        mDataList.add(new NodeBean(504000, 500000, "NDK(JNI)"));
        mDataList.add(new NodeBean(505000, 500000, "OpenGL"));
    }

    /**
     * 插件化 60
     */
    private void initPlugin() {
        mDataList.add(new NodeBean(600000, 0, "插件化"));

        mDataList.add(new NodeBean(601000, 600000, "droidplugin"));
    }

    /**
     * 热修复 70
     */
    private void initHotFix() {
        mDataList.add(new NodeBean(700000, 0, "热修复"));

        mDataList.add(new NodeBean(701000, 700000, "阿里 AddFix"));
        mDataList.add(new NodeBean(702000, 700000, "携程 DynamicAPK"));
        mDataList.add(new NodeBean(703000, 700000, "QQ 热修复"));
    }



    /**
     * JVM 90
     */
    private void initJVM() {
        mDataList.add(new NodeBean(900000, 0, "JVM"));

        mDataList.add(new NodeBean(901000, 900000, "GC 机制"));
        mDataList.add(new NodeBean(902000, 900000, "内存分配"));
    }

    /**
     * APK 91
     */
    private void initApkResolve() {
        mDataList.add(new NodeBean(910000, 0, "APK"));

        mDataList.add(new NodeBean(911000, 910000, "APK 构成"));
        mDataList.add(new NodeBean(912000, 910000, "APK 打包流程"));
    }

    /**
     * Animation 92
     */
    private void initAnimation() {
        mDataList.add(new NodeBean(920000, 0, "Animation"));

        mDataList.add(new NodeBean(921000, 920000, "Tween animation"));
        mDataList.add(new NodeBean(922000, 920000, "Frame animation"));
        mDataList.add(new NodeBean(923000, 920000, "Property animation"));
    }

    /**
     * 进程通信 97
     */
    private void initIpc() {
        mDataList.add(new NodeBean(970000, 0, "进程通信"));

        mDataList.add(new NodeBean(971000, 970000, "IPC"));
        mDataList.add(new NodeBean(972000, 970000, "Binder"));
        mDataList.add(new NodeBean(973000, 970000, "AIDL"));
    }

    /**
     * 设计模式 98
     */
    private void initDesignPattern() {
        mDataList.add(new NodeBean(980000, 0, "设计模式"));

        mDataList.add(new NodeBean(981000, 980000, "单例"));
        mDataList.add(new NodeBean(982000, 980000, "适配器"));
        mDataList.add(new NodeBean(983000, 980000, "观察者"));
    }

    /**
     * 开源框架 99
     */
    private void initOpenSource() {
        mDataList.add(new NodeBean(990000, 0, "开源框架"));

        mDataList.add(new NodeBean(991000, 990000, "EventBus"));
    }

}
