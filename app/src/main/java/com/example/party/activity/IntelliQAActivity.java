package com.example.party.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.party.R;
import com.example.party.adapter.ChatRecyclerAdapter;
import com.example.party.bean.ChatMessage;
import com.example.party.net.AskNet;
import com.example.party.util.listener.ASRListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntelliQAActivity extends AppCompatActivity {
    private ImageView imageView_return;
    private ImageView imgASR;
    private RecyclerView rvChat;
    private EditText editContent;
    private Button btnSend;

    private EventManager asr;
    private EventListener asrListener;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private AskNet askNet;
    private Handler asrHandler;

    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelli_q_a);
        initData();
        initView();
        initPermission();
        initListener();
    }

    private void initData() {
        asr = EventManagerFactory.create(this, "asr");
        askNet = new AskNet(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String result = (String) msg.obj;
                updateChat(result, ChatMessage.RECEIVE);
            }
        });
        asrHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String result = (String) msg.obj;
                updateChat(result, ChatMessage.SEND);
                askNet.ask(result);
            }
        };
        asrListener = new ASRListener(asrHandler);
        asr.registerListener(asrListener);
        chatMessages = new ArrayList<>();
        chatRecyclerAdapter = new ChatRecyclerAdapter(chatMessages);
    }

    private void initView() {
        imageView_return = findViewById(R.id.intelli_q_and_a_return);
        imgASR = findViewById(R.id.intelli_q_and_a_voice);
        btnSend = findViewById(R.id.intelli_q_and_a_sendout);
        editContent = findViewById(R.id.intelli_q_and_a_edit_content);
        rvChat = findViewById(R.id.intelli_q_and_a_rv_char_view);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(chatRecyclerAdapter);
    }

    private void initListener() {
        btnSend.setOnClickListener(v -> {
            String content = editContent.getText().toString();
            if (!content.equals("")) {
                updateChat(content, ChatMessage.SEND);
                editContent.setText("");
                askNet.ask(content);
            }
        });
        // 返回按钮
        imageView_return.setOnClickListener((view) -> {
            finish();
        });
        // 语音识别按钮
        imgASR.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(IntelliQAActivity.this, "长按说话", Toast.LENGTH_SHORT).show();
                    startASR();
                    break;
                case MotionEvent.ACTION_UP:
                    stopASR();
                    break;
                default:
                    return false;
            }
            return true;
        });
    }

    private void startASR() {
        Map<String, Object> params = new HashMap<>();
        params.put("accept-audio-volume", true);
        params.put("pid", 1537);
        String json = new JSONObject(params).toString();
        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
    }

    private void stopASR() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
    }

    private void updateChat(String content, int type) {
        chatMessages.add(new ChatMessage(content, type));
        chatRecyclerAdapter.notifyItemInserted(chatMessages.size() - 1);
        rvChat.scrollToPosition(chatMessages.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.unregisterListener(asrListener);
    }

    private void initPermission() {
        String[] permissions = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<>();

        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                toApplyList.add(permission);
            }
        }

        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
