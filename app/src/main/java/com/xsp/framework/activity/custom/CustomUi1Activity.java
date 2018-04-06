package com.xsp.framework.activity.custom;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xsp.framework.R;
import com.xsp.framework.view.CircleProgressView;
import com.xsp.library.activity.BaseActivity;
import com.xsp.library.view.flow.FlowLayout;
import com.xsp.library.view.flow.TagAdapter;
import com.xsp.library.view.flow.TagFlowLayout;
import com.xsp.library.view.segment.TabSegment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义UI
 */
public class CustomUi1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_custom_ui_one);

        initCircleProgressView();
        initTagFlowLayout();
        initTabSegment();
    }

    /**
     * 圆形进度条
     */
    private void initCircleProgressView() {
        CircleProgressView circleProgressbar = (CircleProgressView) findViewById(R.id.id_circle_progress_view);
        circleProgressbar.setCircleLineMargin(20);
        circleProgressbar.setOuterCircleLineStrokeWidth(2);
        circleProgressbar.setOuterCircleColor(getResources().getColor(R.color.red));
        circleProgressbar.setInnerCircleLineStrokeWidth(40);
        circleProgressbar.setInnerCircleDash(5, 5);
        circleProgressbar.setInnerCircleColor(getResources().getColor(R.color.gray));
        circleProgressbar.setInnerCircleProgressColor(getResources().getColor(R.color.red));
        circleProgressbar.setTextProgressMargin(30);
        circleProgressbar.setTextHint("继续加油哦~");
        circleProgressbar.setTextHintColor(getResources().getColor(R.color.gray));
        circleProgressbar.setTextStrokeWidth(5);
        circleProgressbar.setProgressColor(getResources().getColor(R.color.black));
        circleProgressbar.setProgress(40);
    }

    /**
     * 胶囊
     */
    @SuppressWarnings("unchecked")
    private void initTagFlowLayout() {
        TagFlowLayout tagFlowLayout = (TagFlowLayout) findViewById(R.id.id_tag_flow_layout);
        final TextView textView = (TextView) findViewById(R.id.id_ui_tag_flow_title);

        final List<String> tagList = new ArrayList<>();
        tagList.add("支付宝");
        tagList.add("微信");
        tagList.add("QQ");
        tagList.add("百度云");
        tagList.add("百度贴吧");
        tagList.add("爱奇艺");
        tagList.add("测试一个半长的文本");
        tagList.add("今日头条");
        tagList.add("百度理财");
        tagList.add("百度钱包");
        tagList.add("天猫");
        tagList.add("手机淘宝");
        tagList.add("链家");
        tagList.add("测试一个比较长的文本，这里很长很长很长很长很长很长很长很长很长很长很长很长");
        tagList.add("Boss直聘");
        tagList.add("简书");
        tagList.add("滴滴出行");
        tagList.add("OFO");
        tagList.add("膜拜单车");
        tagList.add("百度地图");
        tagList.add("手机百度");
        tagList.add("QQ浏览器");
        tagList.add("优酷土豆");
        tagFlowLayout.setAdapter(new TagAdapter(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) View.inflate(getApplicationContext(), R.layout.view_tag_flow_item, null);
                tv.setText(tagList.get(position));
                return tv;
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(getApplicationContext(), tagList.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                String text = "choose:" + selectPosSet.toString();
                textView.setText(text);
            }
        });
    }

    private void initTabSegment() {
        final TabSegment tabSegment = (TabSegment) findViewById(R.id.id_tag_segment);
        tabSegment.setOnSegmentClickListener(new TabSegment.OnSegmentClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                switch (index){
                    case 0:
                        Snackbar.make(tabSegment, " 好友", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Snackbar.make(tabSegment, " 同学", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Snackbar.make(tabSegment, " 同事", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Snackbar.make(tabSegment, " 老师", Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


}
