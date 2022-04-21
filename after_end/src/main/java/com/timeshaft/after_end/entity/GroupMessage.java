package com.timeshaft.after_end.entity;
import java.util.Date;
import java.io.Serializable;

/**
 * (GroupMessage)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:47
 */
public class GroupMessage implements Serializable {
    private static final long serialVersionUID = -63558826125441316L;
    
    private Integer id;
    
    private Integer groupId;
    
    private Integer senderId;
    
    private String message;
    
    private Date sendtime;


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

}