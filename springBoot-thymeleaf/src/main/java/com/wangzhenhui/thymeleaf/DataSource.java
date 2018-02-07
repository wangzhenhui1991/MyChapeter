/*
 * Copyright 2018 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */

package com.wangzhenhui.thymeleaf;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhenhui
 * @date 2018/2/7 000714:23
 */
@Getter
@Setter
public class DataSource {
    List<Data> data;
    Integer recordsTotal ;
    public DataSource(Integer total){
        initData(total);
    }
    public void initData(Integer total){
        recordsTotal = total;
        List<Data> datas = new ArrayList<>();

        for(int i =0;i<recordsTotal;i++){
            Data d = new Data();
            d.setFirst_name("first_name"+String.valueOf(i));
            d.setLast_name("last_name"+String.valueOf(i));
            d.setPosition("position"+String.valueOf(i));
            d.setOffice("office"+String.valueOf(recordsTotal-i));
            datas.add(d);
        }
        data = datas;
    }
}
