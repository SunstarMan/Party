package com.example.party;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.party.activity.MainActivity;
import com.example.party.util.AccessToken;

public class LoginActivity extends AppCompatActivity {
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
        initListener();
    }

    public void initData() {
        AccessToken.load(getSharedPreferences("Party", MODE_PRIVATE));
    }

    public void initView() {
        btn1 = findViewById(R.id.btn1);

    }

    public void initListener() {
        //匿名内部类
        btn1.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
