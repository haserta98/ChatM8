package com.example.gozum.chatm8.entities;

public final class Room implements IEntity {
    public Room(String roomid, String roomname, String created_time) {
        this.roomid = roomid;
        this.roomname = roomname;
        this.created_time = created_time;
    }

    public Room()
    {

    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    private String roomid;
    private String roomname;
    private String created_time;

}
