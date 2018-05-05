package org.lemon.yhj.toys;

import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.lemon.yhj.utils.DateUtil;

/**
 * Created by Administrator on 2017/6/15.
 */
public class MainTest {

    public static void main(String[] args) {
        System.out.println(""+ Days.daysBetween(new DateTime(),new DateTime("2017-06-21")).get(DurationFieldType.days()));
        System.out.println(""+ DateUtil.daysBetween(new Date(), new DateTime("2017-06-21").toDate()));
        Integer i = -1;
        System.out.println(i.longValue());
        Map<String, String> bizContent = new TreeMap<>();
        bizContent.put("aaa","111");
        bizContent.put("bbb","");
        System.out.println(JSONObject.toJSONString(bizContent));

        System.out.println(new Random().nextInt(1));

        String str = "java怎么把字符串中的**!!1！的汉字取出来";
        String reg = "[^\u4e00-\u9fa5]";
        System.out.println(str.replaceAll(reg, ""));
    }
}
