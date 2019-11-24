package com.example.android.engaz.models;

public class track {
    private int id;
    private String content;

    public track(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public track(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
