package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (GroupMessageState)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public class GroupMessageState implements Serializable {
    private static final long serialVersionUID = 421119925421190184L;
    
    private Integer messageId;
    
    private Integer userId;
    
    private String state;


    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}