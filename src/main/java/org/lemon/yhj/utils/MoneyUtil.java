package org.lemon.yhj.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by Administrator on 2017/7/20.
 */
public class MoneyUtil {


    private MoneyUtil(){}

    private static final int MULTIPLIER = 100;
/**
     * @Description: yuan2Fen
     *
     * @Param: [yuan]
     * @Return: java.lang.String
     * @throws:
     * @author: youhaijun
     * @Date:   2017/7/21
     */
    public static String yuan2Fen(String yuan) throws ParseException {
        NumberFormat format = NumberFormat.getInstance();
        Number number = format.parse(yuan);
        double temp = number.doubleValue() * 100.0;
        // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
        format.setGroupingUsed(false);
        // 设置返回数的小数部分所允许的最大位数
        format.setMaximumFractionDigits(0);

        return format.format(temp);
    }

    /**
     * @Description: yuan2Fen
     *
     * @Param: [yuan]
     * @Return: java.lang.String
     * @throws:
     * @author: youhaijun
     * @Date:   2017/7/21
     */
    public static String yuan2Fen(int yuan) {
        NumberFormat format = NumberFormat.getInstance();
        double temp = (double) yuan * 100.0;
        format.setGroupingUsed(false);
        format.setMaximumFractionDigits(0);

        return format.format(temp);
    }

    /**
     * @Description: yuan2Fen 保留2位小数,注意传入的String是否位数字
     *
     * @Param: [yuan]
     * @Return: java.lang.String
     * @throws:
     * @author: youhaijun
     * @Date:   2017/7/21
     */
    public static String fen2Yuan(String fen) {

        return new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
    }

    /**
     * @Description: yuan2Fen 保留2位小数
     *
     * @Param: [yuan]
     * @Return: java.lang.String
     * @throws:
     * @author: youhaijun
     * @Date:   2017/7/21
     */
    public static String fen2Yuan(int fen) {
        return new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
    }

    /** 汇率换算-乘（小转大）
     *
     * @param rate = a / b （1-10000之间）
     * @param bValue 允许小数
     * @return bValue 不支持小数,最小1，允许精度丢失
     */
    public static String exchangeRate2a(String rate, double bValue){
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(0);
        format.setGroupingUsed(false);
        double temp = bValue * Double.valueOf(rate);
        return format.format(temp);
    }

    /**
     * 汇率换算-除（大转小）
     * @param rate = a / b （1-10000之间）
     * @param aValue 最小1，不支持小数
     * @return bValue 最多支持4位小数
     */
    public static String exchangeRate2b(String rate, long aValue){
        return new BigDecimal(aValue).divide(new BigDecimal(rate)).setScale(4).toString();
    }

}
