package com.example.gozum.chatm8.adapters;

import com.example.gozum.chatm8.entities.IEntity;

public class FriendViewPager implements IEntity {
    private String name;

    public FriendViewPager(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
