package com.wzh.web.Util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by wangzhenhui on 2017/6/26.
 */
public abstract class Printable {
    public Printable() {
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String buildMDCKey() {
        return "";
    }
}