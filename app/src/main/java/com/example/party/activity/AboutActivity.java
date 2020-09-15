package com.example.party.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.party.R;

public class AboutActivity extends AppCompatActivity {
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initData();
        initView();
        initListener();
    }

    private void initData() {
    }

    private void initView() {
        imageView_back = findViewById(R.id.about_back);
    }

    private void initListener() {
        //返回按钮
        imageView_back.setOnClickListener((view)->{
            finish();
        });
    }
}
