package com.gaode.cameratestapp;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gaode.entity.Book;
import com.gaode.service.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapterLvActivity extends AppCompatActivity {
    private ListView listView;
    private List<Book> bookList;
    private BaseAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv);
        listView = findViewById(R.id.show_lv);
        initData();
        setAdapter();
    }

    private void setAdapter() {

        myAdapter = new MyAdapter(this, bookList);

        listView.setAdapter(myAdapter);


    }

    private void initData() {
        bookList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            bookList.add(new Book("张三" + i, 12 + i));
        }


    }

}
