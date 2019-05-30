package com.example.gozum.chatm8.entities;

public class Friend implements IEntity {

    private String friend_id;
    private String user_account_id;
    private String friends_account_id;
    private String created_time;

    public Friend(String friend_id, String user_account_id, String friends_account_id, String created_time) {
        this.friend_id = friend_id;
        this.user_account_id = user_account_id;
        this.friends_account_id = friends_account_id;
        this.created_time = created_time;
    }

    public Friend()
    {

    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(String user_account_id) {
        this.user_account_id = user_account_id;
    }

    public String getFriends_account_id() {
        return friends_account_id;
    }

    public void setFriends_account_id(String friends_account_id) {
        this.friends_account_id = friends_account_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
