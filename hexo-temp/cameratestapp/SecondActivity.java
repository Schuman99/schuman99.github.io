package com.gaode.cameratestapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gaode.service.FirstService;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "SecondActivity--";
    private Button startService;
    private Button stopService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: =====------");

        setContentView(R.layout.activity_second);
        initView();
    }

    private void initView() {
        startService = findViewById(R.id.play_btn);
        stopService = findViewById(R.id.stop_btn);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.play_btn:
                Log.i(TAG, "onClick: --------play_btn");
                intent = new Intent(SecondActivity.this, FirstService.class);
                intent.putExtra("data", "start");
                startService(intent);
                break;
            case R.id.stop_btn:
                intent = new Intent(SecondActivity.this, FirstService.class);
                intent.putExtra("data", "stop");
                startService(intent);
                break;
        }
    }
}
