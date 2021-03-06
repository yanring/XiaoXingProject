package com.mygps.unrelated_to_function.start.model;

import java.io.Serializable;

/**
 * Created by 10397 on 2016/6/1.
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {

        return "id="+this.id+"----"+"username="+this.username+"----"+"password="+this.password;
    }
}
