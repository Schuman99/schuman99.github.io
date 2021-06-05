package com.gaode.cameratestapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private ListView show_lv;
    private List<String> data;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lv);

        show_lv = findViewById(R.id.show_lv);

        initData();

        addHeaderFooter();
        setAdapter();
        setListener();
    }

    private void addHeaderFooter() {
        ImageView iv = new ImageView(ListViewActivity.this);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);

        iv.setLayoutParams(params);
        iv.setImageResource(R.drawable.ic_launcher);
        show_lv.addHeaderView(iv);



        show_lv.addFooterView(iv);
    }

    private void setListener() {

        show_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //当Item被点击时
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemLongClick", "onItemClick: --------------" + position);
            }
        });


        show_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("onItemLongClick", "onItemLongClick: --------------" + position);
                return true;
            }
        });
    }

    private void setAdapter() {
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        show_lv.setAdapter(arrayAdapter);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("item----" + i);
        }
    }
}
