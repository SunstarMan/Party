package com.example.party.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.party.R;
import com.example.party.bean.User;
import com.example.party.net.LoginNet;
import com.google.gson.Gson;

public class SignInActivity extends AppCompatActivity {
    private EditText editAccount;
    private EditText editPassword;
    private ImageView imgSignIn;

    private LoginNet loginNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        loginNet = new LoginNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = (String) msg.obj;
                if (!response.equals("error")) {
                    Intent intent = new Intent();
                    intent.putExtra("user", response);
                    setResult(MainActivity.LOGIN_SUCCESS, intent);
                    finish();
                }
            }
        });
    }

    private void initView() {
        editAccount = findViewById(R.id.sign_in_edit_account);
        editPassword = findViewById(R.id.sign_in_edit_password);
        imgSignIn = findViewById(R.id.sign_in_btn);
    }

    private void initListener() {
        imgSignIn.setOnClickListener(v -> {
            String account = editAccount.getText().toString();
            String password = editPassword.getText().toString();
            if (account.equals("") || password.equals("")) {
                Toast.makeText(SignInActivity.this, "帐号或密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                loginNet.post(account, password);
            }
        });
    }
}
