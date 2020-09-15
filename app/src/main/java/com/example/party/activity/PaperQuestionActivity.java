package com.example.party.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.party.R;
import com.example.party.bean.Question;
import com.example.party.bean.User;
import com.example.party.net.QuestionNet;
import com.example.party.util.UserInfo;

public class PaperQuestionActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView textTag;
    private TextView textStem;
    private LinearLayout linearOptionA;
    private LinearLayout linearOptionB;
    private LinearLayout linearOptionC;
    private LinearLayout linearOptionD;
    private TextView textOptionA;
    private TextView textOptionB;
    private TextView textOptionC;
    private TextView textOptionD;
    private TextView textPre;
    private TextView textNext;

    private QuestionNet questionNet;
    private Question question;
    private boolean solved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_question);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        questionNet = new QuestionNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                question = (Question) msg.obj;
                textTag.setText(question.getChapter());
                textStem.setText(question.getId() + "、" + question.getQuestion());
                textOptionA.setText(question.getOptionA());
                textOptionB.setText(question.getOptionB());
                textOptionC.setText(question.getOptionC());
                textOptionD.setText(question.getOptionD());
                solved = false;
            }
        });
    }

    private void initView() {
        imgBack = findViewById(R.id.image_paper_question_return);
        textTag = findViewById(R.id.paper_question_text_tag);
        textStem = findViewById(R.id.paper_question_text_stem);
        linearOptionA = findViewById(R.id.linear_paper_question_a);
        linearOptionB = findViewById(R.id.linear_paper_question_b);
        linearOptionC = findViewById(R.id.linear_paper_question_c);
        linearOptionD = findViewById(R.id.linear_paper_question_d);
        textOptionA = findViewById(R.id.text_paper_question_option_a);
        textOptionB = findViewById(R.id.text_paper_question_option_b);
        textOptionC = findViewById(R.id.text_paper_question_option_c);
        textOptionD = findViewById(R.id.text_paper_question_option_d);
        if (UserInfo.user.getState() == User.LOGIN) {
            questionNet.postQuestion(UserInfo.user.getAccount(), 1, 1);
        }
        textPre = findViewById(R.id.paper_question_text_pre);
        textNext = findViewById(R.id.paper_question_text_next);
    }

    private void initListener() {
        linearOptionA.setOnClickListener(v -> {
            if (question.getAnswer().equals("A")) {
                linearOptionA.setBackgroundColor(Color.rgb(0, 255, 0));
            } else {
                linearOptionA.setBackgroundColor(Color.rgb(255, 0, 0));
                showCorrect();
            }
            if (UserInfo.user.getState() == User.LOGIN && !solved) {
                questionNet.postSolveQuestion(UserInfo.user.getAccount(), question.getId(), "A", question.getAnswer());
                solved = true;
            }
        });

        linearOptionB.setOnClickListener(v -> {
            if (question.getAnswer().equals("B")) {
                linearOptionB.setBackgroundColor(Color.rgb(0, 255, 0));
            } else {
                linearOptionB.setBackgroundColor(Color.rgb(255, 0, 0));
                showCorrect();
            }
            if (UserInfo.user.getState() == User.LOGIN && !solved) {
                questionNet.postSolveQuestion(UserInfo.user.getAccount(), question.getId(), "B", question.getAnswer());
                solved = true;
            }
        });

        linearOptionC.setOnClickListener(v -> {
            if (question.getAnswer().equals("C")) {
                linearOptionC.setBackgroundColor(Color.rgb(0, 255, 0));
            } else {
                linearOptionC.setBackgroundColor(Color.rgb(255, 0, 0));
                showCorrect();
            }
            if (UserInfo.user.getState() == User.LOGIN && !solved) {
                questionNet.postSolveQuestion(UserInfo.user.getAccount(), question.getId(), "C", question.getAnswer());
                solved = true;
            }
        });

        linearOptionD.setOnClickListener(v -> {
            if (question.getAnswer().equals("D")) {
                linearOptionD.setBackgroundColor(Color.rgb(0, 255, 0));
            } else {
                linearOptionD.setBackgroundColor(Color.rgb(255, 0, 0));
                showCorrect();
            }
            if (UserInfo.user.getState() == User.LOGIN && !solved) {
                questionNet.postSolveQuestion(UserInfo.user.getAccount(), question.getId(), "D", question.getAnswer());
                solved = true;
            }
        });

        imgBack.setOnClickListener(v -> {
            finish();
        });

        textPre.setOnClickListener(v -> {
            if (UserInfo.user.getState() == User.LOGIN) {
                questionNet.postQuestion(UserInfo.user.getAccount(), question.getId() - 1, -1);
            }
            clear();
        });

        textNext.setOnClickListener(v -> {
            if (UserInfo.user.getState() == User.LOGIN) {
                questionNet.postQuestion(UserInfo.user.getAccount(), question.getId() + 1, 1);
            }
            clear();
        });
    }

    private void showCorrect() {
        switch (question.getAnswer()) {
            case "A":
                linearOptionA.setBackgroundColor(Color.rgb(0, 255, 0));
                break;
            case "B":
                linearOptionB.setBackgroundColor(Color.rgb(0, 255, 0));
                break;
            case "C":
                linearOptionC.setBackgroundColor(Color.rgb(0, 255, 0));
                break;
            case "D":
                linearOptionD.setBackgroundColor(Color.rgb(0, 255, 0));
                break;
        }
    }

    private void clear() {
        linearOptionA.setBackgroundColor(Color.rgb(255, 255, 255));
        linearOptionB.setBackgroundColor(Color.rgb(255, 255, 255));
        linearOptionC.setBackgroundColor(Color.rgb(255, 255, 255));
        linearOptionD.setBackgroundColor(Color.rgb(255, 255, 255));
    }
}
