/**
 * Project Name:lemontree<br>
 * File Name:DateTest.java<br>
 * Package Name:org.lemon.yhj.date<br>
 * Date:2017年5月18日下午4:01:13<br>
 * Copyright (c) 2017, duiba.com.cn All Rights Reserved.<br>
 */

package org.lemon.yhj.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: DateTest <br/>
 * date: 2017年5月18日 下午4:01:13 <br/>
 *
 * @author youhaijun
 * @version
 * @since JDK 1.7
 */
public class DateTest {

    /**
     * main:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author youhaijun
     * @param args
     * @throws ParseException
     * @since JDK 1.7
     */
    public static void main(String[] args) throws ParseException {

        // 1.Calendar 转化 String
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr1 = sdf1.format(calendar1.getTime());

        // 2.String 转化Calendar
        String str2 = "2012-5-27";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf2.parse(str2);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);

        // 3.Date 转化String

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr3 = sdf3.format(new Date());

        // 4.String 转化Date
        String str4 = "2012-5-27";
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
        Date date4 = sdf4.parse(str4);

        // 5.Date 转化Calendar
        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(new java.util.Date());

        // 6.Calendar转化Date
        Calendar calendar6 = Calendar.getInstance();
        java.util.Date date6 = calendar6.getTime();

        // 7.String 转成 Timestamp
        Timestamp ts7 = Timestamp.valueOf("2012-1-14 08:11:00");

        // 8.Date 转 TimeStamp
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Timestamp ts8 = Timestamp.valueOf(time);

    }

}
