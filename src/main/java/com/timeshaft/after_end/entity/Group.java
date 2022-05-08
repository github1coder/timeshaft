package com.timeshaft.after_end.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Group)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:45
 */
public class Group implements Serializable {
    private static final long serialVersionUID = -94325866666090907L;
    
    private Integer id;
    
    private String name;
    
    private Integer masterId;
    
    private String notice;
    
    private String photo;
    
    private Date buildtime;

    private String status;

    public Group() {
    }

    public Group(String name, Integer masterId, String notice, String photo, Date buildtime, String status) {
        this.name = name;
        this.masterId = masterId;
        this.notice = notice;
        this.photo = photo;
        this.buildtime = buildtime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(Date buildtime) {
        this.buildtime = buildtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}