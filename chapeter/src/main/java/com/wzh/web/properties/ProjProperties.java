package com.wzh.web.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by wangzhenhui on 2017/5/7.
 */
@Component
public class ProjProperties {
    @Value("${proj.name}")
    private String name;

    @Value("${proj.owner}")
    private String owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
