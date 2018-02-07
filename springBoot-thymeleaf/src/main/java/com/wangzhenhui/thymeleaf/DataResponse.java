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

import java.util.List;

/**
 * @author wangzhenhui
 * @date 2018/2/7 000713:56
 */
@Getter
@Setter
public class DataResponse{
    Integer draw;
    Integer recordsTotal ;
    Integer recordsFiltered;
    List<Data> data;

    public DataResponse(Integer draw,DataSource source,Integer start,Integer length){
        setDraw(draw);
        setData(source.getData().subList(start,start+length));
        setRecordsTotal(source.getRecordsTotal());
        setRecordsFiltered(source.getRecordsTotal());
    }






}