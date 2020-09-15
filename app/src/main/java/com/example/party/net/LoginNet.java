package com.example.party.net;

import android.os.Handler;
import android.os.Message;

import com.example.party.bean.News;
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

public class LoginNet {
    private Handler handler;
    private OkHttpClient httpClient;
    private Gson gson;

    public LoginNet(Handler handler) {
        this.handler = handler;
        this.httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        this.gson = new Gson();
    }

    public void post(String account, String password) {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("account", account)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder()
                .url(Addr.ip + "/user/login")
                .method("POST", body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
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
}
