package com.timeshaft.after_end.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (PersonalMessage)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public class PersonalMessage implements Serializable {
    private static final long serialVersionUID = 907021151927924048L;
    
    private Integer id;
    
    private Integer friendsId;
    
    private Integer senderId;
    
    private String message;
    
    private Date sendtime;
    
    private String state;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Integer friendsId) {
        this.friendsId = friendsId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}