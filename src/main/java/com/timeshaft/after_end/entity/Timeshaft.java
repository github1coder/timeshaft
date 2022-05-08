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

    private String conclude;

    private String type;

    public Timeshaft() {

    }

    public Timeshaft(Integer groupId, Integer creatorId, String name, Date beginTime, Date endTime, String conclude, String type) {
        this.groupId = groupId;
        this.creatorId = creatorId;
        this.name = name;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.conclude = conclude;
        this.type = type;
    }

    public String getConclude() {
        return conclude;
    }

    public void setConclude(String conclude) {
        this.conclude = conclude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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