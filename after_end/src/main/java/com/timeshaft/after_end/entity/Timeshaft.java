package com.timeshaft.after_end.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Timeshaft)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public class Timeshaft implements Serializable {
    private static final long serialVersionUID = -18461926436711026L;
    
    private Integer id;
    
    private Integer groupId;
    
    private Integer creatorId;
    
    private String name;
    
    private Date beginTime;
    
    private Date endTime;


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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}