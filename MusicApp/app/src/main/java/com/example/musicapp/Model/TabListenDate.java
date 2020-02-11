package com.example.musicapp.Model;

public class TabListenDate {
    private int image;
    private String listenerNumber;
    private String text;

    public TabListenDate(int image, String listenerNumber, String text) {
        this.image = image;
        this.listenerNumber = listenerNumber;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getListenerNumber() {
        return listenerNumber;
    }

    public void setListenerNumber(String listenerNumber) {
        this.listenerNumber = listenerNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
