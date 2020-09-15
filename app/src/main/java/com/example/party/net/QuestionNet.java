package com.example.party.net;

import android.os.Handler;
import android.os.Message;

import com.example.party.bean.Question;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

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

public class QuestionNet {
    private Handler handler;
    private OkHttpClient httpClient;
    private Gson gson;
    public static final int SOLVING = 1;
    public static final int ERROR = 2;

    public QuestionNet(Handler handler) {
        this.handler = handler;
        this.httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        this.gson = new Gson();
    }

    public void postSolvingNumber(String account) {
        httpClient.newCall(constructRequest(account, "/user/solved-all", 0, 0)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = new Message();
                message.what = SOLVING;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    public void postErrorNumber(String account) {
        httpClient.newCall(constructRequest(account, "/user/error-number", 0, 0)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = new Message();
                message.what = ERROR;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    public void postQuestion(String account, int begin, int step) {
        httpClient.newCall(constructRequest(account, "/user/change-question", begin, step)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Question question = gson.fromJson(response.body().string(), Question.class);
                Message message = new Message();
                message.obj = question;
                handler.sendMessage(message);
            }
        });
    }

    public void postSolveQuestion(String account, int id, String answer, String correct) {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("account", account)
                .addFormDataPart("id", String.valueOf(id))
                .addFormDataPart("answer", answer)
                .addFormDataPart("correct", correct)
                .build();
        Request request = new Request.Builder()
                .url(Addr.ip + "/user/solve")
                .method("POST", body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    public void postErrorQuestion(String account) {
        httpClient.newCall(constructRequest(account, "/user/error-question", 0, 0)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                List<Question> questions = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        questions.add(gson.fromJson(jsonArray.optJSONObject(i).toString(), Question.class));
                    }
                    Message message = new Message();
                    message.obj = questions;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Request constructRequest(String account, String path, int begin, int step) {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("account", account)
                .addFormDataPart("begin", String.valueOf(begin))
                .addFormDataPart("step", String.valueOf(step))
                .build();
        return new Request.Builder()
                .url(Addr.ip + path)
                .method("POST", body)
                .build();
    }
}
