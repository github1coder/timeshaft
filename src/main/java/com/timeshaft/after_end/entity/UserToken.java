package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (UserToken)实体类
 *
 * @author makejava
 * @since 2022-05-08 20:32:03
 */
public class UserToken implements Serializable {
    private static final long serialVersionUID = 538030899794682596L;
    
    private Integer id;
    
    private Integer userId;
    
    private String token;

    public UserToken() {
    }

    public UserToken(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}