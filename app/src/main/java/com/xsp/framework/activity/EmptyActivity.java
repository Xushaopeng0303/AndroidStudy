package com.xsp.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xsp.framework.R;
import com.xsp.framework.util.HandleType;
import com.xsp.library.activity.BaseActivity;

/**
 * 处理简单逻辑的界面
 */
public class EmptyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_two_button_layout);
        Intent intent = getIntent();
        if (intent != null) {
            handleType(intent.getStringExtra(HandleType.HANDLE_TYPE_KEY));
        }
    }

    private void handleType(String type) {
        switch (type) {
            case HandleType.SNACK_BAR:              // SnackBar
                snackBar();
                break;
            default:
                break;
        }
    }

    private void snackBar() {
        Button upBtn = (Button) findViewById(R.id.id_common_top_btn);
        upBtn.setText("test");
        Button downBtn = (Button) findViewById(R.id.id_common_bottom_btn);
        downBtn.setText("testDown");
        downBtn.setVisibility(View.VISIBLE);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmptyActivity.this, "AAAA", Toast.LENGTH_SHORT).show();
            }
        });
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmptyActivity.this, "AAAA", Toast.LENGTH_LONG).show();
            }
        });

    }
}
