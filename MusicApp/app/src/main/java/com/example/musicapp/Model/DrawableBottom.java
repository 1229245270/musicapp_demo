package com.example.musicapp.Model;

public class DrawableBottom {
    private int icon;
    private String title;
    private boolean isHave;
    private String hint;
    private int hintImage;

    public DrawableBottom(int icon, String title, boolean isHave, String hint, int hintImage) {
        this.icon = icon;
        this.title = title;
        this.isHave = isHave;
        this.hint = hint;
        this.hintImage = hintImage;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHava() {
        return isHave;
    }

    public void setHava(boolean hava) {
        isHave = hava;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getHintImage() {
        return hintImage;
    }

    public void setHintImage(int hintImage) {
        this.hintImage = hintImage;
    }
}
