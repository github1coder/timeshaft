package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (GroupHeat)实体类
 *
 * @author makejava
 * @since 2022-05-11 20:05:41
 */
public class GroupHeat implements Serializable {
    private static final long serialVersionUID = 956942962785733954L;
    
    private Integer id;
    
    private Integer groupId;
    
    private Integer groupHeat;
    
    private Integer messageCount;
    
    private String type;

    public GroupHeat() {
    }

    public GroupHeat(Integer groupId, Integer groupHeat, Integer messageCount, String type) {
        this.groupId = groupId;
        this.groupHeat = groupHeat;
        this.messageCount = messageCount;
        this.type = type;
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

    public Integer getGroupHeat() {
        return groupHeat;
    }

    public void setGroupHeat(Integer groupHeat) {
        this.groupHeat = groupHeat;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void upMessageCount() {
        this.messageCount++;
    }

    public void changeGroupHeat() {
        this.groupHeat = this.groupHeat / 2 + this.messageCount / 300;
        this.messageCount = 0;
    }
}