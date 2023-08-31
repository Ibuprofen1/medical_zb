package com.zbdx.medicalzb.domain;

import com.zbdx.medicalzb.domain.superdomain.SuperDomain;

/**
 * @description: 封装前端菜单meta里面的属性
 **/
public class Meta extends SuperDomain {
    private String title;//菜单title

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
