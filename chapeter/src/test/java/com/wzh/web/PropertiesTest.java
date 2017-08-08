package com.wzh.web;

import com.wzh.web.properties.ProjProperties;
import com.wzh.web.properties.RandomProperties;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangzhenhui on 2017/5/7.
 */

public class PropertiesTest extends BaseTest{

    @Autowired
    private ProjProperties projProperties;
    @Autowired
    private RandomProperties randomProperties;
    @Test
    public void setProjPropertiesTest(){
        Assert.assertEquals(projProperties.getName(),"chapeter");
        Assert.assertEquals(projProperties.getOwner(),"wangzhenhui");

    }
}
