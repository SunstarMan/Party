package com.example.party.util;

import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccessToken {
    public static String ACCESS_TOKEN;
    private static long EXPIRE;

    public static void load(SharedPreferences preferences) {
        ACCESS_TOKEN = preferences.getString("access_token", null);
        EXPIRE = preferences.getLong("expires_in", 0);

        if (!isValid()) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "");
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=lXxjgYzv14lU8XBsHA0G4wEh&client_secret=HkLiRGuGPG7y6hQZK9gWPF1KdlAWLNsc")
                    .method("POST", body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    JSONObject result = null;
                    try {
                        result = new JSONObject(response.body().string());
                        ACCESS_TOKEN = result.getString("access_token");
                        EXPIRE = result.getLong("expires_in") * 1000;

                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("access_token", ACCESS_TOKEN);
                        edit.putLong("expires_in", EXPIRE);
                        edit.apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static boolean isValid() {
        return ACCESS_TOKEN != null && System.currentTimeMillis() <= EXPIRE;
    }
}
