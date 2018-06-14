package org.lemon.yhj.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 * Created by xuefeng on 2017/10/27.
 */
public class PhoneTool {

    private PhoneTool() {

    }

    /**
     * 第二第三位号码组合
     **/
    private static final String[] DOUBLE_NUMBER = {
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "45",
            "50", "51", "52", "53", "55", "56", "57", "58", "59",
            "76", "77", "78",
            "80", "81", "82", "83", "84", "85", "86", "87", "88", "89"
    };

    /**
     * 生成随机手机号码
     *
     * @return 手机号码
     */
    public static String createPartialNumber() {
        StringBuilder sb = new StringBuilder("1");
        sb.append(DOUBLE_NUMBER[new Random().nextInt(DOUBLE_NUMBER.length)]);
        sb.append("****");
        sb.append(new Random().nextInt(10));
        sb.append(new Random().nextInt(10));
        sb.append(new Random().nextInt(10));
        sb.append(new Random().nextInt(10));
        return sb.toString();
    }

    /***
         * @Description: createSuffixNumber
         * 构造四位手机尾号
         * @Param: []
         * @Return: java.lang.String
         * @throws:
         * @author: youhaijun
         * @Date:   2017/12/18
         */
    public static String createSuffixNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(new Random().nextInt(10));
        sb.append(new Random().nextInt(10));
        sb.append(new Random().nextInt(10));
        sb.append(new Random().nextInt(10));
        return sb.toString();
    }

    /***
         * @Description: createRandom 生成count位随机验证码
         *
         * @Param: [count]
         * @Return: java.lang.String
         * @throws:
         * @author: youhaijun
         * @Date:   2017/12/19
         */
    public static String createRandom(int count){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++){
            sb.append(String.valueOf(random.nextInt(10)));
        }
        return sb.toString();
    }

    public static String getSuffixPhone(String phone) {
        return phone == null ? null : phone.substring(phone.length() - 4, phone.length());
    }

    /**
     * 加密将手机号,将手机号中间4位以*代替 15322229876 => 153****9876
     * @param phone 11位手机号
     * @return
     * @author: lichao
     * @Date:   2018/3/14
     */
    public static String encryptPhone(String phone){
        if(StringUtils.isBlank(phone)||phone.length()<9){
            return phone;
        }
        return phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());
    }

}
