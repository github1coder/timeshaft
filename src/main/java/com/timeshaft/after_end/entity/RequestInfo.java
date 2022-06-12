package com.timeshaft.after_end.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (RequestInfo)实体类
 *
 * @author makejava
 * @since 2022-06-10 21:00:12
 */
public class RequestInfo implements Serializable {
    private Integer id;
    
    private Integer userId;
    
    private String url;
    
    private Date beginTime;
    
    private Long endTime;
    
    private Integer code;

    public RequestInfo() {

    }

    public RequestInfo(Integer userId, String url, Date beginTime, Long endTime, Integer code) {
        this.userId = userId;
        this.url = url;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.code = code;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}