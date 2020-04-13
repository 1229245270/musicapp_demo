package com.example.musicapp.Model;

public class ZhiBo {
    private String image;
    private String title;
    private String intro;

    public ZhiBo(String image, String title, String intro) {
        this.image = image;
        this.title = title;
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
