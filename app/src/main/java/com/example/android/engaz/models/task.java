package com.example.android.engaz.models;

public class task {
    int id;
    int idTrack;
    String content;
    int statue;

    public task(int id, int idTrack, String content, int statue) {
        this.id = id;
        this.idTrack = idTrack;
        this.content = content;
        this.statue = statue;
    }

    public task(int idTrack, String content, int statue) {
        this.idTrack = idTrack;
        this.content = content;
        this.statue = statue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(int idTrack) {
        this.idTrack = idTrack;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int isStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }
}
