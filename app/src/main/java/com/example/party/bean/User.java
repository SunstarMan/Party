package com.example.party.bean;

/**
 * @ClassName User
 * @Author 11214
 * @Date 2020/3/13
 * @Description TODO
 */
public class User {
    private int state;
    private String account;
    private String username;
    private String password;
    private String motto;
    private String icon;
    private String identity;
    private String branch;

    public static final int LOGOUT = 1;
    public static final int LOGIN = 2;

    public int getState() {
        return state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", motto='" + motto + '\'' +
                ", icon='" + icon + '\'' +
                ", identity='" + identity + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
