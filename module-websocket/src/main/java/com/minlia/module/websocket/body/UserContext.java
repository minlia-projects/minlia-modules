package com.minlia.module.websocket.body;

/**
 * Created by garen on 2017/11/30.
 */
public class UserContext {

    private String username;

    public UserContext(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
