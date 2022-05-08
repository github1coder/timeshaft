package com.timeshaft.after_end.entity;

import java.io.Serializable;

/**
 * (Tag)实体类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public class Tag implements Serializable {
    private static final long serialVersionUID = 577053816216863749L;
    
    private Integer id;
    
    private Integer timeshaftId;
    
    private String name;

    public Tag(Integer timeshaftId, String name) {
        this.timeshaftId = timeshaftId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}