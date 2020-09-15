package com.example.party.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.party.bean.Personas;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonNet {
    private Handler handler;
    private OkHttpClient httpClient;
    private Gson gson;

    public PersonNet(Handler handler) {
        this.handler = handler;
        this.httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        this.gson = new Gson();
    }

    public void postPersonas(String account) {
        httpClient.newCall(constructRequest(account, "/personas/self-personas")).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = new Message();
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    private Request constructRequest(String account, String path) {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("account", account)
                .build();
        return new Request.Builder()
                .url(Addr.ip + path)
                .method("POST", body)
                .build();
    }
}
