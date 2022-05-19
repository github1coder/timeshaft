package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public class User implements Serializable {
    private static final long serialVersionUID = 311162846449163380L;
    
    private Integer id;
    
    private String username;
    
    private String email;
    
    private String password;
    
    private String photo;

    private Integer private1;

    public User() {
    }

    public User(String email, String password, String username, String photo, Integer private1) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.private1 = private1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPrivate1() {
        return private1;
    }

    public void setPrivate1(Integer private1) {
        this.private1 = private1;
    }
}