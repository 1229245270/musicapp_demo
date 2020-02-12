package com.example.musicapp.Model;

public class ZhiBo {
    private int image;
    private String title;
    private String intro;

    public ZhiBo(int image, String title, String intro) {
        this.image = image;
        this.title = title;
        this.intro = intro;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
