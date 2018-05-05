package org.lemon.yhj.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2017/9/28.
 */
public class UrlProcessUtil {
    /***
     * @Description: processUrl
     * 1.url为空白和数字的时候返回""
     * 2.给没有定义http头的url拼接http头
     *
     * @Param: [illegalUrl]
     * @Return: java.lang.String
     * @throws:
     * @author: youhaijun
     * @Date: 2017/9/20
     */
    public static String processUrl(String illegalUrl) {
        String urlNew = illegalUrl;
        if (StringUtils.isBlank(illegalUrl) || StringUtils.isNumeric(illegalUrl)) {
            return StringUtils.EMPTY;
        }

        if (!illegalUrl.startsWith("http")) {
            if (!illegalUrl.startsWith("//")) {
                urlNew = "//" + illegalUrl;
            }
            urlNew = "http:" + urlNew;
        }
        return urlNew;
    }
}
