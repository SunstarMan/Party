package com.example.party.bean;

import java.io.Serializable;

/**
 * @ClassName news
 * @Author 11214
 * @Date 2020/3/12
 * @Description TODO
 */
public class News implements Serializable {
    private String url;
    private String title;
    private Long timestamp;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
