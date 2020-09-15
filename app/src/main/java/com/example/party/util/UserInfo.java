package com.example.party.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.party.bean.User;

public class UserInfo {
    public static User user;

    public static void loadUserInfo(SharedPreferences preferences) {
        user = new User();
        user.setAccount(preferences.getString("account", ""));
        user.setUsername(preferences.getString("username", ""));
        user.setPassword(preferences.getString("password", ""));
        user.setIcon(preferences.getString("icon", null));
        user.setMotto(preferences.getString("motto", null));
        user.setIdentity(preferences.getString("identity", null));
        user.setBranch(preferences.getString("branch", null));
        user.setState(User.LOGOUT);
        Log.i("UserInfo", user.toString());
    }
}
