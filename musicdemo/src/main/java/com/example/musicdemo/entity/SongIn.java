package com.example.musicdemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class SongIn {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "userName不能为空")
    private String userName;
    @NotBlank(message = "fileName不能为空")
    private String fileName;
    @NotBlank(message = "listName不能为空")
    private String listName;

    public SongIn() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
