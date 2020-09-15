package com.example.party.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.party.LoginActivity;
import com.example.party.R;

public class SelfThinkActivity extends AppCompatActivity {
    private ImageView imageView_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_think);
        initData();
        initView();
        initListener();
    }

    private void initData() {
    }

    private void initView() {
        imageView_return = findViewById(R.id.self_think_return);
    }

    private void initListener() {
        imageView_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
