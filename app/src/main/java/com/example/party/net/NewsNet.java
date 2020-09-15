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

public class NewsNet {
    private Handler handler;
    private OkHttpClient httpClient;
    private Gson gson;

    public NewsNet(Handler handler) {
        this.handler = handler;
        this.httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        this.gson = new Gson();
    }

    public void post(int from, int end) {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("from", String.valueOf(from))
                .addFormDataPart("end", String.valueOf(end))
                .build();
        Request request = new Request.Builder()
                .url(Addr.ip + "/news/getNews")
                .method("POST", body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    List<News> newsList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        newsList.add(gson.fromJson(object.toString(), News.class));
                    }
                    Message message = new Message();
                    message.obj = newsList;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postMessages() {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("none", "none")
                .build();
        Request request = new Request.Builder()
                .url(Addr.ip + "/news/messages")
                .method("POST", body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    List<com.example.party.bean.Message> messages = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        messages.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), com.example.party.bean.Message.class));
                    }
                    Message message = new Message();
                    message.obj = messages;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
