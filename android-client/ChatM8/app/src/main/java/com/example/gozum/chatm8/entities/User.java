package com.example.gozum.chatm8.entities;

public final class User implements IEntity {
    public User(int user_id, String account_id, String name, String password, String imgUrl, boolean isActive, String created_time, String email) {
        this.user_id = user_id;
        this.account_id = account_id;
        this.name = name;
        this.password = password;
        this.imgUrl = imgUrl;
        this.isActive = isActive;
        this.created_time = created_time;
        this.email = email;
    }

    private int user_id;
    private String account_id;
    private String name;
    private String password;
    private String imgUrl;
    private boolean isActive;
    private String created_time;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }


}
