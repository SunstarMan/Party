package com.example.party.util.listener;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class ASRListener implements EventListener {
    private Handler asrHandler;

    public ASRListener(Handler asrHandler) {
        this.asrHandler = asrHandler;
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        try {
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                JSONObject result = new JSONObject(params);
                if (result.getString("result_type").equals("final_result")) {
                    Message message = new Message();
                    String best_result = result.getString("best_result");
                    if (best_result.endsWith("，")) {
                        message.obj = best_result.substring(0, best_result.length() - 1);
                    } else {
                        message.obj = best_result;
                    }
                    asrHandler.sendMessage(message);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
