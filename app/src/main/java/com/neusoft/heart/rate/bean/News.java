package com.neusoft.heart.rate.bean;

public class News {

    public String imgUrl = "";
    public String content = "";
    public String source = "";
    public String time = "";
    public String link = "";

    public News(String imgUrl, String content, String source, String time, String link) {
        this.imgUrl = imgUrl;
        this.content = content;
        this.source = source;
        this.time = time;
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
