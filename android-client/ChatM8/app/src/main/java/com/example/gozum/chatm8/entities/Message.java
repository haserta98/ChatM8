package com.example.gozum.chatm8.entities;




public final  class Message implements IEntity {

    public Message(int message_id, String message, String message_from_id, String message_to_id, String message_time, String roomid) {
        this.message_id = message_id;
        this.message = message;
        this.message_from_id = message_from_id;
        this.message_to_id = message_to_id;
        this.message_time = message_time;
        this.roomid = roomid;
    }

    public Message()
    {

    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_from_id() {
        return message_from_id;
    }

    public void setMessage_from_id(String message_from_id) {
        this.message_from_id = message_from_id;
    }

    public String getMessage_to_id() {
        return message_to_id;
    }

    public void setMessage_to_id(String message_to_id) {
        this.message_to_id = message_to_id;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    private int message_id;
    private String message;
    private String message_from_id,message_to_id;
    private String message_time;
    private String roomid;
}
