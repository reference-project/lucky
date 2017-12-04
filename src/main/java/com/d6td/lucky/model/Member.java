package com.d6td.lucky.model;

import java.util.Date;

public class Member {
    private Integer memberId;

    private String name;

    private String headImg;

    private String openId;

    private Boolean lucky;

    private Date createTime;

    private Boolean special;

    private Boolean willBeCheck;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Boolean getLucky() {
        return lucky;
    }

    public void setLucky(Boolean lucky) {
        this.lucky = lucky;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getWillBeCheck() {
        return willBeCheck;
    }

    public void setWillBeCheck(Boolean willBeCheck) {
        this.willBeCheck = willBeCheck;
    }
}