package org.lemon.yhj.utils;

import java.util.Map;
import java.util.Random;

public class MathUtils {

    /**
     * 从id-rate集合中，依据概率随机返回id
     *
     * @param rates 需要处理的id-rate映射关系集合
     * @param seed 种子数 根据seed随机一个种子数，来保证该seed在集合中每次取到的都是同一个值；可以为null，此时完全随机
     * @return 如果seed!=null ，返回根据种子数随机随机的id，否则返回完全随机的id
     */
    private static Long randomByRate(Map<Long, String> rates, Long seed){
        if(rates.size() < 1){
            return null;
        }
        double rate = rates.values().stream().mapToDouble(Double::parseDouble).sum();
        if(rate != 100.00){
            return null;
        }
        //根据用户id随机一个种子数，来保证该用户在同一个列表中每次取到的都是同一个值，更换列表之后则重新随机
        //中奖的随机数,目前为int类型 对于概率而言只支持1/10000的精度,即0.01
        Random random = new Random(seed);
        double point = Double.valueOf(random.nextInt(10000) + 1) / 100.00;
        double temp = 0.0;
        for(Long id : rates.keySet()){
            temp += Double.valueOf(rates.get(id));
            if(point <= temp){
                return id;
            }
        }
        return rates.keySet().iterator().next();
    }

    /**
     * 构造完全随机的字母+数字组成的字符串
     * 例如：AJ12asKfjsl234
     * @param length 需要的字符串长度
     * @param containsUpCase true 包括大写字母；false 不包括大写字母
     * @return 定长的完全随机的字母+数字字符串
     */
    public static String getStringByNumbersAndChars(int length, boolean containsUpCase){
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                int choice = random.nextBoolean() ? 65 : 97; //取得65大写字母还是97小写字母
                str += (char) ((containsUpCase ? choice : 97) + random.nextInt(26));
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }
}
