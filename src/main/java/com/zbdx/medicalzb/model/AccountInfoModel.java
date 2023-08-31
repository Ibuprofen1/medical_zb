package com.zbdx.medicalzb.model;

/**
 * 用于登录成功时返回给前端用户信息
 **/
public class AccountInfoModel {
    private String realname;
    private String utype;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
