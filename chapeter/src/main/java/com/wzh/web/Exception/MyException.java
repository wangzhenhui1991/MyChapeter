package com.wzh.web.Exception;

import java.util.Objects;

/**
 * Created by wangzhenhui on 2017/5/7.
 */
public class MyException extends Exception{
    private int errCode;
    private String errMsg;
    private Objects data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Objects getData() {
        return data;
    }

    public void setData(Objects data) {
        this.data = data;
    }

    public MyException(String message) {
        super(message);
    }

}
