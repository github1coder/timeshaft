package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (Star)实体类
 *
 * @author makejava
 * @since 2022-06-11 12:16:45
 */
public class Star implements Serializable {
    private static final long serialVersionUID = -78811419936992725L;
    
    private Integer id;
    
    private Integer timeshaftId;
    
    private String title;

    private Integer UserId;

    public Star() {
    }


    public Star(Integer timeshaftId, String title, Integer userId) {
        this.timeshaftId = timeshaftId;
        this.title = title;
        UserId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimeshaftId() {
        return timeshaftId;
    }

    public void setTimeshaftId(Integer timeshaftId) {
        this.timeshaftId = timeshaftId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }
}