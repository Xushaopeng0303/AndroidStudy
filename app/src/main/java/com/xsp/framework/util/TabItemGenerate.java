package com.xsp.framework.util;

import android.content.Context;
import android.content.res.Resources;

import com.xsp.framework.R;
import com.xsp.framework.activity.AndroidKnowledgeGraphActivity;
import com.xsp.framework.activity.animation.AnimationActivity;
import com.xsp.framework.activity.animation.SystemTransitionSrcActivity;
import com.xsp.framework.activity.animation.TransitionActivity;
import com.xsp.framework.activity.custom.CustomUi1Activity;
import com.xsp.framework.activity.DropDownMenuActivity;
import com.xsp.framework.activity.EmptyActivity;
import com.xsp.framework.activity.GitHubActivity;
import com.xsp.framework.activity.StudyCanvasActivity;
import com.xsp.framework.activity.custom.CustomUi2Activity;
import com.xsp.framework.activity.custom.ProgressBarActivity;
import com.xsp.framework.activity.eb.EventBusReceiveActivity;
import com.xsp.framework.activity.material.CoordinatorLayoutActivity;
import com.xsp.framework.activity.material.TabLayoutActivity;
import com.xsp.framework.modle.ListItem;

import java.util.ArrayList;

/**
 * 生成列表类数据
 */
public class TabItemGenerate {

    /**
     * Ui 功能列表
     */
    public static ArrayList<ListItem> getUiList(Context context) {
        Resources res = context.getResources();
        ArrayList<ListItem> list = new ArrayList<>();
        list.add(new ListItem(res.getString(R.string.tab_ui_drop_down_menu), DropDownMenuActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_ui_circle_progress), CustomUi1Activity.class));
        list.add(new ListItem(res.getString(R.string.tab_ui_tag_flow), CustomUi1Activity.class));
        list.add(new ListItem(res.getString(R.string.tab_ui_tab_segment), CustomUi1Activity.class));
        list.add(new ListItem(res.getString(R.string.tab_ui_switch_button), CustomUi2Activity.class));
        list.add(new ListItem(res.getString(R.string.tab_ui_progress_bar), ProgressBarActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_ui_canvas), StudyCanvasActivity.class));

        return list;
    }

    /**
     * 常用功能 功能列表
     */
    public static ArrayList<ListItem> getFunctionList(Context context) {
        Resources res = context.getResources();
        ArrayList<ListItem> list = new ArrayList<>();
        list.add(new ListItem(res.getString(R.string.tab_fun_tab_layout), TabLayoutActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_fun_coordinator_layout), CoordinatorLayoutActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_fun_snack_bar), EmptyActivity.class, HandleType.SNACK_BAR));
        list.add(new ListItem(res.getString(R.string.tab_fun_navigation_view), null));
        list.add(new ListItem(res.getString(R.string.tab_fun_text_input_layout), null));
        list.add(new ListItem(res.getString(R.string.tab_fun_floating_action_button), null));
        list.add(new ListItem(res.getString(R.string.tab_fun_collapsing_tool_bar_layout), null));
        list.add(new ListItem(res.getString(R.string.tab_fun_tool_bar_layout), null));

        return list;
    }

    /**
     * 框架 功能列表
     */
    public static ArrayList<ListItem> getFrameList(Context context) {
        Resources res = context.getResources();
        ArrayList<ListItem> list = new ArrayList<>();
        list.add(new ListItem(res.getString(R.string.tab_frame_android_graph), AndroidKnowledgeGraphActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_frame_git_hub), GitHubActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_frame_event_bus), EventBusReceiveActivity.class));
        return list;
    }

    /**
     * 进阶 功能列表
     */
    public static ArrayList<ListItem> getAdvancedList(Context context) {
        Resources res = context.getResources();
        ArrayList<ListItem> list = new ArrayList<>();
        list.add(new ListItem(res.getString(R.string.tab_advanced_animation), AnimationActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_advanced_transition_system), SystemTransitionSrcActivity.class));
        list.add(new ListItem(res.getString(R.string.tab_advanced_transition), TransitionActivity.class));
        return list;
    }

    /**
     * GitHub 源码
     */
    public static ArrayList<ListItem> getGitHubList(Context context) {
        Resources res = context.getResources();
        ArrayList<ListItem> list = new ArrayList<>();
        list.add(new ListItem(res.getString(R.string.git_hub_android_process), null, HandleType.ANDROID_PROGRESS));
        return list;
    }
}
