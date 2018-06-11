/**
 * Project Name:lemontree<br>
 * File Name:StringSplitUtil.java<br>
 * Package Name:org.lemon.yhj.string<br>
 * Date:2017年1月25日上午10:03:02<br>
 * Copyright (c) 2017, duiba.com.cn All Rights Reserved.<br>
 *
 */
package org.lemon.yhj.utils;

import org.apache.commons.lang3.StringUtils;



/**
 * 字符串分隔与重组
 * ClassName: StringSplitUtil <br/>
 * date: 2017年1月25日 上午10:03:02 <br/>
 *
 * @author youhaijun
 * @version 
 * @since JDK 1.7
 */
public class StringSplitUtil {

    public static String fetchBase64Result(String base64Result) {
        if (StringUtils.isBlank(base64Result)) {
            return StringUtils.EMPTY;
        }
        return base64Result.replaceAll("=", "");
    }

    public static String reduceBase64Result(String base64Result) {
        if (StringUtils.isBlank(base64Result)) {
            return StringUtils.EMPTY;
        }
        int mod = Math.floorMod(base64Result.length(), 4);
        StringBuilder sb = new StringBuilder(base64Result);
        for (int i = 0; i < 4 - mod; i++) {
            sb.append("=");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(StringSplitUtil.fetchBase64Result("MTAwMDAwMA=="));
        System.out.println(StringSplitUtil.reduceBase64Result("MTAwMDAwMA"));
    }
}
