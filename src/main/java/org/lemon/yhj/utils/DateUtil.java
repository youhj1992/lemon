/**
 * Project Name:lemontree<br>
 * File Name:DateUtil.java<br>
 * Package Name:org.lemon.yhj.utils<br>
 * Date:2017年5月18日下午4:26:48<br>
 * Copyright (c) 2017, duiba.com.cn All Rights Reserved.<br>
 */

package org.lemon.yhj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: DateUtil <br/>
 * 使用JDK中Date操作的Date工具类 date: 2017年5月18日 下午4:26:48 <br/>.
 *
 * @author youhaijun
 * @version
 * @since JDK 1.7
 */
public class DateUtil {

    /** The Constant LOG. */
    private static final Logger           LOG             = LoggerFactory.getLogger(DateUtil.class);

    /** yyyy-MM-dd. */
    private static final SimpleDateFormat DAY             = getFormat("yyyy-MM-dd");

    /** yyyyMMdd. */
    private static final SimpleDateFormat DAY_NUMBER      = getFormat("yyyyMMdd");

    /** yyMMdd. */
    private static final SimpleDateFormat YEAR_DAY_NUMBER = getFormat("yyMMdd");

    /** yyyy. */
    private static final SimpleDateFormat YEAR            = getFormat("yyyy");

    /** MM-dd. */
    private static final SimpleDateFormat DAY_ONLY        = getFormat("MM-dd");

    /** yyyy-MM-dd HH:mm:ss. */
    private static final SimpleDateFormat SECOND          = getFormat("yyyy-MM-dd HH:mm:ss");

    /** HH:mm:ss. */
    private static final SimpleDateFormat SECOND_ONLY     = getFormat("HH:mm:ss");

    /** yyyy-MM-dd HH:mm. */
    private static final SimpleDateFormat MINUTE          = getFormat("yyyy-MM-dd HH:mm");

    /** HH:mm. */
    private static final SimpleDateFormat MINUTE_ONLY     = getFormat("HH:mm");

    /** yyyyMMdd-HHmm. */
    private static final SimpleDateFormat MINUTE_ANOTHER  = getFormat("yyyyMMdd-HHmm");

    /** MM/dd/yyyy. */
    private static final SimpleDateFormat MONTH_DAY_YEAR  = getFormat("MM/dd/yyyy");

    /** yyyyMMddHHmmssSSS. */
    private static final SimpleDateFormat MILLI_SECOND    = getFormat("yyyyMMddHHmmssSSS");

    /**
     * method : 1、根据Date类型获取"yyyy"字符串.
     *
     * @param date the date
     * @return the year str
     */
    public static String getYearStr(Date date) {
        synchronized (YEAR) {
            return getStr(date, YEAR);
        }
    }

    /**
     * method : 2、根据时间戳获取"yyyy"字符串.
     *
     * @param date the date
     * @return the year str
     */
    public static String getYearStr(long date) {
        synchronized (YEAR) {
            return getStr(new Date(date), YEAR);
        }
    }

    /**
     * method : 3、根据Date类型获取"yyyyMMdd-HHmm"字符串.
     *
     * @param date the date
     * @return the minute db str
     */
    public static String getMinuteDbStr(Date date) {
        synchronized (MINUTE_ANOTHER) {
            return getStr(date, MINUTE_ANOTHER);
        }
    }

    /**
     * method : 4、根据"yyyyMMdd-HHmm"字符串 获取 Date类型.
     *
     * @param str the str
     * @return the minute db date
     */
    public static Date getMinuteDbDate(String str) {
        synchronized (MINUTE_ANOTHER) {
            return getDate(str, MINUTE_ANOTHER);
        }
    }

    /**
     * method : 4、根据"MM/dd/yyyy"字符串 获取 Date类型.
     *
     * @param str the str
     * @return the mon day year date
     */
    public static Date getMonDayYearDate(String str) {
        synchronized (MONTH_DAY_YEAR) {
            return getDate(str, MONTH_DAY_YEAR);
        }
    }

    /**
     * method : 5、根据Date类型 获取"HH:mm:ss"字符串.
     *
     * @param date the date
     * @return the second only str
     */
    public static String getSecondOnlyStr(Date date) {
        synchronized (SECOND_ONLY) {
            return getStr(date, SECOND_ONLY);
        }
    }

    /**
     * method : 6、根据时间戳获取"HH:mm:ss"字符串.
     *
     * @param date the date
     * @return the second only str
     */
    public static String getSecondOnlyStr(long date) {
        return getSecondOnlyStr(new Date(date));
    }

    /**
     * method : 7、根据时间戳获取"MM-dd"字符串.
     *
     * @param date the date
     * @return the only day str
     */
    public static String getOnlyDayStr(long date) {
        synchronized (DAY_ONLY) {
            return getStr(new Date(date), DAY_ONLY);
        }
    }

    /**
     * method : 8、根据时间戳获取"yyyy-MM-dd HH:mm:ss"字符串.
     *
     * @param date the date
     * @return the second str
     */
    public static String getSecondStr(long date) {
        return getSecondStr(new Date(date));
    }

    /**
     * method : 9、根据Date获取"yyyy-MM-dd HH:mm:ss"字符串.
     *
     * @param date the date
     * @return the second str
     */
    public static String getSecondStr(Date date) {
        synchronized (SECOND) {
            return getStr(date, SECOND);
        }
    }

    /**
     * method : 10、根据Date获取"yyyy-MM-dd HH:mm"字符串.
     *
     * @param date the date
     * @return the minute str
     */
    public static String getMinuteStr(Date date) {
        synchronized (MINUTE) {
            return getStr(date, MINUTE);
        }
    }

    /**
     * method : 11、根据时间戳获取"yyyy-MM-dd HH:mm"字符串.
     *
     * @param date the date
     * @return the minute str
     */
    public static String getMinuteStr(long date) {
        return getMinuteStr(new Date(date));
    }

    /**
     * method : 12、根据Date获取"HH:mm"字符串.
     *
     * @param date the date
     * @return the minute only str
     */
    public static String getMinuteOnlyStr(Date date) {
        synchronized (MINUTE_ONLY) {
            return getStr(date, MINUTE_ONLY);
        }
    }

    /**
     * method : 13、根据Date获取"yyyy-MM-dd"字符串.
     *
     * @param date the date
     * @return the day str
     */
    public static String getDayStr(Date date) {
        synchronized (DAY) {
            return getStr(date, DAY);
        }
    }

    /**
     * method : 14、根据时间戳获取"yyyy-MM-dd"字符串.
     *
     * @param date the date
     * @return the day str
     */
    public static String getDayStr(long date) {
        return getDayStr(new Date(date));
    }

    /**
     * method : 15、根据Date获取"yyyyMMdd"形式的数字.
     *
     * @param date the date
     * @return the day number
     */
    public static int getDayNumber(Date date) {
        if (date == null) {
            return 0;
        }
        synchronized (DAY_NUMBER) {
            return Integer.valueOf(getStr(date, DAY_NUMBER));
        }
    }

    /**
     * method : 16、根据Date获取"yyMMdd"形式的数字.
     *
     * @param date the date
     * @return the YY day number
     */
    public static int getYYDayNumber(Date date) {
        if (date == null) {
            return 0;
        }
        synchronized (YEAR_DAY_NUMBER) {
            return Integer.valueOf(getStr(date, YEAR_DAY_NUMBER));
        }
    }

    /**
     * method : 17、根据Date获取"yyyy-MM-dd 00:00:00"形式的Date.
     *
     * @param date the date
     * @return the day date
     */
    public static Date getDayDate(Date date) {
        return getDayDate(getDayStr(date));
    }

    /**
     * method : 18、根据"yyyy-MM-dd HH:mm:ss"形式的字符串 获取Date.
     *
     * @param dateStr the date str
     * @return the second date
     */
    public static Date getSecondDate(String dateStr) {
        synchronized (SECOND) {
            return getDate(dateStr, SECOND);
        }
    }

    /**
     * method : 19、根据"yyyy-MM-dd"形式的字符串 获取Date.
     *
     * @param dateStr the date str
     * @return the day date
     */
    public static Date getDayDate(String dateStr) {
        synchronized (DAY) {
            return getDate(dateStr, DAY);
        }
    }

    /**
     * method : 20、根据"HH:mm"形式的字符串 获取Date 获取的为1970-01-01 HH:mm:00形式的Date，一般不常用.
     *
     * @param dateStr the date str
     * @return the minute only date
     */
    public static Date getMinuteOnlyDate(String dateStr) {
        synchronized (MINUTE_ONLY) {
            return getDate(dateStr, MINUTE_ONLY);
        }
    }

    /**
     * method : 21、根据"yyyy-MM-dd HH:mm"形式的字符串 获取Date.
     *
     * @param dateStr the date str
     * @return the minute date
     */
    public static Date getMinuteDate(String dateStr) {
        synchronized (MINUTE) {
            return getDate(dateStr, MINUTE);
        }
    }

    /**
     * method : 22、根据时间戳 获取"yyyy-MM-dd HH:mm"形式的Date.
     *
     * @param time the time
     * @return the minute date
     */
    public static Date getMinuteDate(long time) {
        return getMinuteDate(getMinuteStr(time));
    }

    /**
     * method : 23、根据（Date获取"yyyy-MM-dd"形式字符串）和（"HH:mm"的字符串）拼接成"yyyy-MM-dd HH:mm" 形式的字符串，然后返回时间戳.
     *
     * @param date the date
     * @param str the str
     * @return the minite date
     */
    public static long getMiniteDate(Date date, String str) {
        if (str == null) return 0;
        return getMinuteDate(getDayStr(date) + " " + str).getTime();
    }

    /**
     * method : 把date的月数增加offset.
     *
     * @param date the date
     * @param offset the offset
     * @return the date
     */
    public static Date monthsAddOrSub(Date date, int offset) {
        return addOrSub(date, Calendar.MONTH, offset);
    }

    /**
     * method : 24、把date的天数增加offset.
     *
     * @param date the date
     * @param offset the offset
     * @return the date
     */
    public static Date daysAddOrSub(Date date, int offset) {
        return addOrSub(date, Calendar.DAY_OF_MONTH, offset);
    }

    /**
     * method : 25、把date的小时数增加offset.
     *
     * @param date the date
     * @param offset the offset
     * @return the date
     */
    public static Date hoursAddOrSub(Date date, int offset) {
        return addOrSub(date, Calendar.HOUR_OF_DAY, offset);
    }

    /**
     * method : 26、把date的分钟数增加offset.
     *
     * @param date the date
     * @param offset the offset
     * @return the date
     */
    public static Date minutesAddOrSub(Date date, int offset) {
        return addOrSub(date, Calendar.MINUTE, offset);
    }

    /**
     * method : 27、把date的秒数增加offset.
     *
     * @param date the date
     * @param offset the offset
     * @return the date
     */
    public static Date secondsAddOrSub(Date date, int offset) {
        return addOrSub(date, Calendar.SECOND, offset);
    }

    /**
     * method : 28、是否为TimeString 样式的String 00:00 - 23:59.
     *
     * @param toCheck the to check
     * @return true, if checks if is time string
     */
    public static boolean isTimeString(String toCheck) {
        if (!StringUtils.isNotBlank(toCheck)) {
            return false;
        }
        if (toCheck.trim().matches("([0-1][0-9]|2[0-3]):[0-5][0-9]|24:00")) return true;
        else return false;
    }

    /**
     * method : 29、比较两个TimeString 的大小 大于0表示ts1 > ts2，等于0表示相等，小于0表示ts1 < ts2.
     *
     * @param ts1 the ts1
     * @param ts2 the ts2
     * @return the int
     */
    public static int compareHHmmInString(String ts1, String ts2) {
        return ts1.compareTo(ts2);
    }

    /**
     * method : 30、检测是否在开始与结束之间，前闭后开区间 -1： start 不小于end 0: 不在start 与end之间 1: 在start与end之间.
     *
     * @param ts the ts
     * @param start the start
     * @param end the end
     * @return the int
     */
    public static int betweenHHmmInString(String ts, String start, String end) {
        if (compareHHmmInString(start, end) >= 0) return -1;
        if (compareHHmmInString(ts, start) < 0) return 0;
        if (compareHHmmInString(end, ts) <= 0) return 0;
        return 1;
    }

    /**
     * method : 31、检测两个时间是否相等, 00:00 == 24:00.
     *
     * @param ts1 the ts1
     * @param ts2 the ts2
     * @return true, if equals in time string
     */
    public static boolean equalsInTimeString(String ts1, String ts2) {
        if (ts1.equals(ts2)) return true;
        if (ts1.equals("00:00") || ts1.equals("24:00")) {
            if (ts2.equals("00:00") || ts2.equals("24:00")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取yyyyMMddHHmmssSSS.
     *
     * @return the millisecond
     */
    public static String getMillisecond() {
        synchronized (MILLI_SECOND) {
            return getStr(new Date(), MILLI_SECOND);
        }
    }

    /**
     * method : 32、检测是否为同一个小时和分钟. 小时为24小时制 本方法只比较HH:mm不会判断哪年哪月哪日 1: cal>c; 0:cal=c; -1:cal<c
     *
     * @param cal the cal
     * @param c the c
     * @return the int
     */
    public static int compareHHmm(Calendar cal, Calendar c) {
        if (cal.get(Calendar.HOUR_OF_DAY) > c.get(Calendar.HOUR_OF_DAY)) {
            return 1;
        } else if (cal.get(Calendar.HOUR_OF_DAY) == c.get(Calendar.HOUR_OF_DAY)) {
            if (cal.get(Calendar.MINUTE) > c.get(Calendar.MINUTE)) return 1;
            else if (cal.get(Calendar.MINUTE) == c.get(Calendar.MINUTE)) return 0;
            else return -1;
        } else return -1;
    }

    /**
     * method : 33、检测是否在开始与结束之间，闭区间 小时为24小时制 本方法只比较HH:mm不会判断哪年哪月哪日 -1： start 不小于end 0: 不在start 与end之间 1: 在start与end之间.
     *
     * @param cal the cal
     * @param start the start
     * @param end the end
     * @return the int
     */
    public static int betweenHHmm(Calendar cal, Calendar start, Calendar end) {
        if (compareHHmm(start, end) != -1) return -1;
        if (compareHHmm(cal, start) == -1) return 0;
        if (compareHHmm(end, cal) == -1) return 0;
        return 1;
    }

    /**
     * method : 34、检测是否为同在一天. 1: cal>c; 0:cal=c; -1:cal<c
     *
     * @param cal the cal
     * @param c the c
     * @return true, if compare day
     */
    public static boolean compareDay(Calendar cal, Calendar c) {
        if (cal.get(Calendar.YEAR) == c.get(Calendar.YEAR) && cal.get(Calendar.MONTH) == c.get(Calendar.MONTH)
            && cal.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)) return true;
        else return false;
    }

    /**
     * method : 35、将00:00格式的字符串转为Calender.
     *
     * @param timeString the time string
     * @return the calendar
     * @throws Exception the exception
     */
    public static Calendar string2calendar(String timeString) throws Exception {
        if (!DateUtil.isTimeString(timeString)) throw new Exception("Wrong argument : timeString format error "
                                                                    + timeString);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, getHour(timeString));
        cal.set(Calendar.MINUTE, getMinute(timeString));
        return cal;
    }

    /**
     * method : 36、将calendar转为HH:MM格式的字符串.
     *
     * @param cal the cal
     * @return the string
     */
    public static String calendar2TimeString(Calendar cal) {
        return (cal.get(Calendar.HOUR_OF_DAY) > 9 ? cal.get(Calendar.HOUR_OF_DAY) : "0" + cal.get(Calendar.HOUR_OF_DAY))
               + ":" + (cal.get(Calendar.MINUTE) > 9 ? cal.get(Calendar.MINUTE) : "0" + cal.get(Calendar.MINUTE));
    }

    /**
     * method : 37、TimeString 中得到小时信息 比如 23:25那么获取23.
     *
     * @param timeString the time string
     * @return the hour
     */
    public static int getHour(String timeString) {
        return Integer.parseInt(timeString.substring(0, 2));
    }

    /**
     * method : 38、TimeString 中得到分钟信息 比如 23:25那么获取25.
     *
     * @param timeString the time string
     * @return the minute
     */
    public static int getMinute(String timeString) {
        return Integer.parseInt(timeString.substring(3, 5));
    }

    /**
     * method : 39、从Date的字符串中只得到月日信息.
     *
     * @param dateString the date string
     * @return the date only from date
     */
    public static String getDateOnlyFromDate(String dateString) {
        return dateString.substring(5, 10);
    }

    /**
     * method : 40、将calendar转为MM-dd格式的字符串.
     *
     * @param cal the cal
     * @return the string
     */
    public static String calendar2DateString(Calendar cal) {
        return (cal.get(Calendar.MONTH) + 1 > 9 ? cal.get(Calendar.MONTH) + 1 : "0" + (cal.get(Calendar.MONTH) + 1))
               + "-"
               + (cal.get(Calendar.DAY_OF_MONTH) > 9 ? cal.get(Calendar.DAY_OF_MONTH) : "0"
                                                                                        + cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 日期月份相减.
     *
     * @param beginDate the begin date
     * @param endDate the end date
     * @return the integer
     */
    @SuppressWarnings("static-access")
    public static Integer monthSub(Date beginDate, Date endDate) {
        Integer monthNum = 0;
        Integer yearNumber = 0;
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(beginDate);
        endCalendar.setTime(endDate);

        yearNumber = endCalendar.get(endCalendar.YEAR) - startCalendar.get(endCalendar.YEAR);
        monthNum = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return yearNumber * 12 + monthNum;
    }

    /**
     * 获取当前日期月份的第N天.
     *
     * @param beginDate the begin date
     * @param index the index
     * @return the month day by index
     */
    public static Date getMonthDayByIndex(Date beginDate, int index) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        cal.set(Calendar.DAY_OF_MONTH, index);
        return cal.getTime();
    }

    /**
     * 获取今天0点到明天0点.
     *
     * @return the map< string, date>
     */
    public static Map<String, Date> retTodayAndTomorrow() {
        Map<String, Date> dateMap = new HashMap<String, Date>();
        Date start = DateUtil.getDayDate(new Date()); // 获取今天 0点0分0秒 日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.DATE, 1);
        Date end = cal.getTime(); // 获取明天 0点0分0秒 日期
        dateMap.put("startDay", start);
        dateMap.put("endDay", end);
        return dateMap;
    }

    /**
     * 获取当前时间到明天0点的秒数.
     *
     * @return the to tomorrow seconds
     */
    public static int getToTomorrowSeconds() {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
                                                      curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) ((tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000);
    }

    /**
     * 获取今天零点零分零秒 时间搓.
     *
     * @return the today zero time in millis
     */
    public static long getTodayZeroTimeInMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取当前月第一天，零点零分零秒 时间搓.
     *
     * @return the month first day time in millis
     */
    public static long getMonthFirstDayTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取下一月第一天，零点零分零秒 时间搓.
     *
     * @return the next month first day time in millis
     */
    public static long getNextMonthFirstDayTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取相前时间与传入时间的相差几天.
     *
     * @param date the date
     * @return the differ day
     */
    public static long getDifferDay(Date date) {
        Date nowDate = new Date();
        if (nowDate.after(date)) {
            return 0l;
        }
        long checkday = Math.abs(date.getTime() - nowDate.getTime()) / (1000 * 24 * 60 * 60)
                        + (Math.abs(date.getTime() - nowDate.getTime()) % (1000 * 24 * 60 * 60) == 0 ? 0 : 1);
        return checkday;
    }

    /**
     * Adds the or sub.
     *
     * @param date the date
     * @param type the type
     * @param offset the offset
     * @return the date
     */
    private static Date addOrSub(Date date, int type, int offset) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.get(type);
        cal.set(type, cal.get(type) + offset);
        return cal.getTime();
    }

    /**
     * Gets the str.
     *
     * @param date the date
     * @param format the format
     * @return the str
     */
    private static String getStr(Date date, SimpleDateFormat format) {
        if (date == null) {
            return "";
        }
        return format.format(date);
    }

    /**
     * Gets the format.
     *
     * @param format the format
     * @return the format
     */
    private static SimpleDateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * Gets the date.
     *
     * @param dateStr the date str
     * @param format the format
     * @return the date
     */
    private static Date getDate(String dateStr, SimpleDateFormat format) {
        if ("".equals(dateStr) || dateStr == null) return null;
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            LOG.error("format yyyy-MM-dd HH:mm:ss error:", e);
        }
        return null;
    }

    /**
     * 根据日期 获取 该日期当周 周一日期 如果日期为周一，那么获取上周一.
     *
     * @param day the day
     * @return the monday date
     */
    public static Date getMondayDate(Date day) {
        Calendar calWeek = Calendar.getInstance();
        calWeek.setTime(day);
        if (Calendar.SUNDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -6);
        } else if (Calendar.MONDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -7);
        } else if (Calendar.TUESDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -1);
        } else if (Calendar.WEDNESDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -2);
        } else if (Calendar.THURSDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -3);
        } else if (Calendar.FRIDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -4);
        } else if (Calendar.SATURDAY == calWeek.get(Calendar.DAY_OF_WEEK)) {
            calWeek.add(Calendar.DATE, -5);
        }
        Date monday = getDayDate(calWeek.getTime());
        return monday;
    }

    /**
     * 根据日期获取 该日期当月1号的日期 如果参数日期为1号，那么获取上月1号的日期.
     *
     * @param day the day
     * @return the first day date
     */
    public static Date getFirstDayDate(Date day) {
        Calendar calMonth = Calendar.getInstance();
        calMonth.setTime(day);
        if (1 == calMonth.get(Calendar.DAY_OF_MONTH)) {
            calMonth.setTime(DateUtil.daysAddOrSub(day, -1));
        }
        calMonth.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtil.getDayDate(calMonth.getTime());
    }

    /**
     * 获取00:00:00时间点.
     *
     * @param date the date
     * @return Date 年月日不变，时分秒改为当天的23:59:59
     */
    public static Date getStartTime(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return calendar.getTime();
    }

    /**
     * 获取23:59:59时间点.
     *
     * @param date the date
     * @return Date 年月日不变，时分秒改为当天的23:59:59
     */
    public static Date getEndTime(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 根据日期字符串获取该日期的结束时间 2015-12-01 23:59:59.
     *
     * @param strDate the str date
     * @return the end time
     */
    public static Date getEndTime(String strDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(getDayDate(strDate));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();

    }

    /**
     * 根据日期字符串获取该日期的开始时间 2015-12-01 00:00:00.
     *
     * @param strDate the str date
     * @return the start time
     */
    public static Date getStartTime(String strDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(getDayDate(strDate));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 计算两个日期之间相差的天数.
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) {
        smdate = getDayDate(smdate);
        bdate = getDayDate(bdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 日期增加或减少天数.
     *
     * @param date the date
     * @param day 负数为减少，正数为增加
     * @return the date
     */
    public static Date changeByDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 获取昨日日期.
     *
     * @return the yesterday
     */
    public static String getYesterday() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dft.format(endDate);
    }
}
