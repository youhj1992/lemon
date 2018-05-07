/**
 * Project Name:activity-center-common<br>
 * File Name:EntityUtil.java<br>
 * Package Name:cn.com.duiba.tuia.activity.center.common.util<br>
 * Date:2017年3月8日下午3:46:22<br>
 * Copyright (c) 2017, duiba.com.cn All Rights Reserved.<br>
 */
package org.lemon.yhj.utils;



import org.lemon.yhj.dto.BatchFunction;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ClassName: EntityUtil <br/>
 * Function: 类工具. <br/>
 * date: 2017年3月8日 下午3:46:22 <br/>
 *
 * E:entity,dto之类的
 * I:id
 * E1/E2/E3/E4 : entity,dto之类的
 * @author wubo
 * @since JDK 1.7
 */
public class EntityUtil {

    /**
     * 将list转化为map,key为lamda表达式定义
     * 不支持过滤
     *
     * @param list list
     * @param keyFunc 获取key的函数
     * @param <I> map的key。一般是id
     * @param <E> map的value，一般是 dto，entity
     * @return map
     */
    public static <I, E> Map<I, E> listToMap(List<E> list, Function<E, I> keyFunc) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<I, E> map = new HashMap<>();
        for (E e : list) {
            map.put(keyFunc.apply(e), e);
        }
        return map;

    }

    /**
     * list转map
     * 支持过滤
     * @param list list
     * @param keyFunc 获取key的函数
     * @param filterFunc 过滤函数，返回true表示过滤，返回的map中不包含被过滤的
     * @param <I> map的key。一般是id
     * @param <E> map的value，一般是 dto，entity
     * @return map
     */
    public static <I, E> Map<I, E> listToMap(List<E> list, Function<E, I> keyFunc, Predicate<E> filterFunc) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<I, E> map = new HashMap<>();
        for (E e : list) {
            if (!filterFunc.test(e)) {
                map.put(keyFunc.apply(e), e);
            }
        }
        return map;
    }


    /**
     * @Description: getParam 从List<Dto>中提取某个参数的list
     *
     * @Param: [list, t]
     * @Return: java.util.List<I>
     * @throws:
     * @author: youhaijun
     * @Date: 2017/6/22
     */
    public static <I, E> List<I> getParam(List<E> list, Function<E, I> t) {

        List<I> result = new ArrayList<>();

        if (list == null || list.isEmpty()) {
            return result;
        }
        for (E e : list) {
            I k = t.apply(e);
            if (k != null) {
                result.add(k);
            }
        }
        return result;
    }

    /**
     * 告诉我
     * 从某类获取id的函数
     * 根据id批量查询dto的函数
     * 从dto获取id的函数
     * <p>
     * 然后帮你搞定一切
     * <p>
     * map,list数据会set到BatchFunction中，自己去get
     * <p>
     */
    public static <E, I, E1> void setDtoByIdBatch(List<E> list, BatchFunction<E, I, E1> batchFunction) {

        if (list == null || list.isEmpty()) return;

        if (batchFunction == null) return;
        // 收集id
        for (E dto : list) {
            batchFunction.processIdFromEntity(dto);
        }
        // 批量查询

        batchFunction.processSelectList();
        batchFunction.processMap();
    }

    public static <E, I, E1, E2> void setDtoByIdBatch(List<E> list, BatchFunction<E, I, E1> batchFunction1, BatchFunction<E, I, E2> batchFunction2) {

        if (list == null || list.isEmpty()) return;

        if (batchFunction1 == null) return;
        // 收集id
        for (E dto : list) {
            batchFunction1.processIdFromEntity(dto);
            batchFunction2.processIdFromEntity(dto);
        }
        // 批量查询

        batchFunction1.processSelectList();
        batchFunction1.processMap();

        batchFunction2.processSelectList();
        batchFunction2.processMap();
    }

    public static <E, I, E1, E2, E3> void setDtoByIdBatch(List<E> list, BatchFunction<E, I, E1> batchFunction1, BatchFunction<E, I, E2> batchFunction2, BatchFunction<E, I, E3> batchFunction3) {

        if (list == null || list.isEmpty()) return;

        if (batchFunction1 == null) return;
        // 收集id
        for (E dto : list) {
            batchFunction1.processIdFromEntity(dto);
            batchFunction2.processIdFromEntity(dto);
            batchFunction3.processIdFromEntity(dto);
        }
        // 批量查询

        batchFunction1.processSelectList();
        batchFunction1.processMap();

        batchFunction2.processSelectList();
        batchFunction2.processMap();

        batchFunction3.processSelectList();
        batchFunction3.processMap();
    }

    public static <E, I, E1, E2, E3, E4> void setDtoByIdBatch(List<E> list, BatchFunction<E, I, E1> batchFunction1, BatchFunction<E, I, E2> batchFunction2, BatchFunction<E, I, E3> batchFunction3, BatchFunction<E, I, E4> batchFunction4) {

        if (list == null || list.isEmpty()) return;

        if (batchFunction1 == null) return;
        // 收集id
        for (E dto : list) {
            batchFunction1.processIdFromEntity(dto);
            batchFunction2.processIdFromEntity(dto);
            batchFunction3.processIdFromEntity(dto);
            batchFunction4.processIdFromEntity(dto);
        }
        // 批量查询

        batchFunction1.processSelectList();
        batchFunction1.processMap();

        batchFunction2.processSelectList();
        batchFunction2.processMap();

        batchFunction3.processSelectList();
        batchFunction3.processMap();

        batchFunction4.processSelectList();
        batchFunction4.processMap();
    }


}
