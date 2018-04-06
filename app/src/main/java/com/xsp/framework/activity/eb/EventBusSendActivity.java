package com.xsp.framework.activity.eb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xsp.framework.R;
import com.xsp.framework.activity.eb.modle.ParamsType;
import com.xsp.library.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus 发送界面
 */
public class EventBusSendActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_two_button_layout);
        Button sendBtn = (Button) findViewById(R.id.id_common_top_btn);
        sendBtn.setText(getResources().getText(R.string.event_bus_send_text));
        sendBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_common_top_btn:
                EventBus.getDefault().post(new ParamsType("EventBus take params"));
                break;
            default:
                break;
        }
    }
}
