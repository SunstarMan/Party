package com.example.party.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.party.R;
import com.example.party.bean.User;
import com.example.party.util.UserInfo;

public class AccountManageActivity extends AppCompatActivity {
    private ImageView imageView_return;
    private TextView textAccount;
    private TextView textUsername;
    private TextView textIdentity;
    private TextView textBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        initData();
        initView();
        initListener();
    }

    private void initData() {

    }

    private void initView() {
        imageView_return = findViewById(R.id.account_manage_return);
        textAccount = findViewById(R.id.account_manage_text_account);
        textUsername = findViewById(R.id.account_manage_text_username);
        textIdentity = findViewById(R.id.account_manage_text_identity);
        textBranch = findViewById(R.id.account_manage_text_branch);
        if (UserInfo.user.getState() == User.LOGIN) {
            textAccount.setText(UserInfo.user.getAccount());
            textUsername.setText(UserInfo.user.getUsername());
            textIdentity.setText(UserInfo.user.getIdentity());
            textBranch.setText(UserInfo.user.getBranch());
        }
    }

    private void initListener() {
        // 返回按钮
        imageView_return.setOnClickListener((view) -> {
            finish();
        });
    }
}
