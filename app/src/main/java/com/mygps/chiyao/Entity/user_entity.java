package com.mygps.chiyao.Entity;

/**
 * Created by zy on 2016/3/31.
 */
public class user_entity {
    private String name;
    private String password;
    private Long id;

    public user_entity(){
        super();
    }

    public user_entity(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "user_entity{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
