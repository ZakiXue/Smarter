package com.example.fogfly.smarter.entity;

/**
 * @author Zaki Xue
 * @time 2019/2/25 21:26
 * @description
 */
public class Journal {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Journal(String date,String title,String content,String tag){
        this.date = date;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }
    private String date;
    private String title;
    private String content;
    private String tag;
}
