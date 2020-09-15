package com.example.party.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.party.R;
import com.example.party.adapter.ErrorBankRecyclerAdapter;
import com.example.party.bean.Question;
import com.example.party.bean.User;
import com.example.party.net.QuestionNet;
import com.example.party.util.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class ErrorBankActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ErrorBankRecyclerAdapter recyclerAdapter;
    private List<Question> questions;
    private QuestionNet questionNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_bank);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        questions = new ArrayList<>();
        recyclerAdapter = new ErrorBankRecyclerAdapter(questions);
        questionNet = new QuestionNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                questions.addAll((List<Question>) msg.obj);
                recyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.error_bank_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        if (UserInfo.user.getState() == User.LOGIN) {
            questionNet.postErrorQuestion(UserInfo.user.getAccount());
        }
    }

    private void initListener() {

    }
}
