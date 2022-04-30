package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (GroupUser)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public class GroupUser implements Serializable {
    private static final long serialVersionUID = 539127955890909567L;
    
    private Integer id;
    
    private Integer groupId;
    
    private Integer userId;
    
    private String userNickname;
    
    private String identity;

    private String state;

    public GroupUser(Integer groupId, Integer userId, String userNickname, String identity, String state) {
        this.groupId = groupId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.identity = identity;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}