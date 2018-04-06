package com.xsp.framework.activity.eb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xsp.framework.R;
import com.xsp.framework.activity.eb.modle.ParamsType;
import com.xsp.library.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus 接收界面
 *
 * 源码：https://github.com/greenrobot/EventBus
 * 进阶：http://www.tuicool.com/articles/QBzAZjr
 *
 * 使用步骤：
 * （1）gradle 中 添加依赖：compile 'org.greenrobot:eventbus:3.0.0'
 * （2）定义通知事件类型：EventBusEvent
 * （3）注册：EventBus.getDefault().register(this);
 * （4）事件发送：eventBus.post(new EventBusEvent event);
 * （5）事件接收：onEvent(EventBusEvent event) {}，共四种接收方式
 * （6）注销：EventBus.getDefault().unregister(this);
 *
 * 注意事项：
 * （1）事件接受依据是事件中：事件类型
 * （2）四种事件接收方式：onEventMainThread，onEvent，onEventBackgroundThread，onEventAsync
 * （3）EventBus只能用于线程间通信，不能用于进程间通信
 * （4）post事件时，观察者马上处理
 * （5）postSticky事件时，priority的值越小，优先级越低
 * （6）在EventBus3.0之前消息处理的方法只能限定于onEvent、onEventMainThread、onEventBackgroundThread和onEventAsync，他们分别代表四种线程模型。
 * （7）而在EventBus3.0之后，事件处理的方法可以随便取名，但是需要添加一个注解@Subscribe，并且要指定线程模型
 * （8）现成模型默认为POSTING、MAIN、BACKGROUND、ASYNC。和3.0以前的四个方法相对应
 */
public class EventBusReceiveActivity extends BaseActivity implements View.OnClickListener {
    private TextView mShowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_two_button_layout);

        Button jumpBtn = (Button) findViewById(R.id.id_common_top_btn);
        jumpBtn.setText(getResources().getText(R.string.event_bus_jump_to_send));
        jumpBtn.setOnClickListener(this);
        Button sendBtn = (Button) findViewById(R.id.id_common_bottom_btn);
        sendBtn.setText(getResources().getText(R.string.event_bus_send_self));
        sendBtn.setOnClickListener(this);
        sendBtn.setVisibility(View.VISIBLE);
        mShowView = (TextView) findViewById(R.id.id_common_show_text);
        mShowView.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_common_top_btn:
                startActivity(new Intent(this, EventBusSendActivity.class));
                break;
            case R.id.id_common_bottom_btn:
                EventBus.getDefault().postSticky(new ParamsType("EventBus take params"));
                break;
        }
    }

    /**
     * 不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行
     */
    @Subscribe
    public void onEventMainThread(ParamsType event) {
        String msg = "Received msg : " + event.getMsg();
        mShowView.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d("xsp", "onEventMainThread: " + event.getMsg());
    }

    /**
     * 发布事件和接收事件线程在同一个线程
     */
    @Subscribe
    public void onEvent(ParamsType event) {
        Log.d("xsp", "onEvent: " + event.getMsg());
    }

    /**
     * 那么如果事件是在UI线程中发布出来的，那么onEventBackground就会在子线程中运行，如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行
     */
    @Subscribe
    public void onEventBackgroundThread(ParamsType event){
        Log.d("xsp", "onEventBackgroundThread: " + event.getMsg());
    }

    /**
     * 无论事件在哪个线程发布，都会创建新的子线程在执行
     */
    @Subscribe
    public void onEventAsync(ParamsType event){
        Log.d("xsp", "onEventAsync: " + event.getMsg());
    }

    /**
     * 自EventBus 3.0 后，我们不需要在不同的线程中接收消息，可以在注解中设置ThreadMode
     *
     * threadMode为回调所在的线程，priority为优先级，sticky为是否接收黏性事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true, priority = 1)
    public void onCustomEvent(ParamsType event) {
        Log.d("xsp", "onCustomEvent: " + event.getMsg());
    }

}
