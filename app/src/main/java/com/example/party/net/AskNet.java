package com.example.party.net;

import android.os.Handler;
import android.os.Message;

import com.example.party.util.AccessToken;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AskNet {
    private Handler handler;
    private OkHttpClient httpClient;
    private Gson gson;

    public AskNet(Handler handler) {
        this.handler = handler;
        this.httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        this.gson = new Gson();
    }

    public void ask(String query) {
        try {
            httpClient.newCall(constructRequest(query)).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONObject result = new JSONObject(response.body().string());
                        int error_code = result.getInt("error_code");
                        if (error_code == 0) {
                            Message message = new Message();
                            String answer = result.getJSONObject("result").getJSONObject("response").getJSONArray("action_list").getJSONObject(0).getString("say");
                            message.obj = answer.replaceAll("<br>", "\n");
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Request constructRequest(String query) throws JSONException {
        JSONObject query_info = new JSONObject();
        query_info.put("source", "KEYBOARD");
        query_info.put("type", "TEXT");

        JSONObject request = new JSONObject();
        request.put("bernard_level", 1);
        request.put("user_id", "888888");
        request.put("query_info", query_info);
        request.put("query", query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bot_session", "");
        jsonObject.put("log_id", "123456789");
        jsonObject.put("bot_id", "1020606");
        jsonObject.put("version", "2.0");
        jsonObject.put("request", request);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/unit/bot/chat?access_token=" + AccessToken.ACCESS_TOKEN)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
    }
}
