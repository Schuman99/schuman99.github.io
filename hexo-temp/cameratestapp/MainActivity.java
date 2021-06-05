package com.gaode.cameratestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.zxing.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    private Button scanBtn;
    private WindowManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        scanBtn = findViewById(R.id.scan_btn);
        manager = getWindowManager();
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //用于接收扫描结果
        if (resultCode == RESULT_OK) { //成功获取结果
            String result = data.getExtras().getString("result");
            Log.i("mtag", "onActivityResult: result  " + result);
        }

    }


}
