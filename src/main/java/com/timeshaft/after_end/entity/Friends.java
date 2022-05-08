package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (Friends)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:19:03
 */
public class Friends implements Serializable {
    private static final long serialVersionUID = -26661167304247542L;
    
    private Integer id;
    
    private Integer userId1;
    
    private Integer userId2;
    
    private String nickname1;
    
    private String nickname2;

    private String state;

    private String status;

    public Friends() {
    }

    public Friends(Integer userId1, Integer userId2, String nickname1, String nickname2, String state, String status) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
        this.state = state;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public String getNickname1() {
        return nickname1;
    }

    public void setNickname1(String nickname1) {
        this.nickname1 = nickname1;
    }

    public String getNickname2() {
        return nickname2;
    }

    public void setNickname2(String nickname2) {
        this.nickname2 = nickname2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}