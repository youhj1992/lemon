package org.lemon.yhj.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合的交集，并集操作，主要针对不同类型的集合的操作，同类集合使用自带方法，或者common的工具类实现即可
 *
 * Attention:两个集合的key 不能有重复,否则会报错。日后解决
 *
 */
public class CollectionUtils {
    /**
     * 根据指定的属性匹配，获取交集
     * 返回第一个集合的类型
     * @param coll1 集合1
     * @param coll1Func 集合1获取指定属性的函数
     * @param coll2 集合2
     * @param coll2Func 集合2获取指定属性的函数
     * @param <E1> 集合1的类型
     * @param <E2> 集合2的类型
     * @param <P> 指定属性类型
     * @return list
     */
    public static <E1, E2, P> List<E1> getContainsList(Collection<E1> coll1, Function<E1, P> coll1Func, Collection<E2> coll2, Function<E2, P> coll2Func) {
        if (coll1 == null || coll2 == null || coll1.isEmpty() || coll2.isEmpty()) {
            return Collections.emptyList();
        }

        Map<P, E2> collMap = coll2.stream().collect(Collectors.toMap(coll2Func, Function.identity()));

        return coll1.stream().filter(e -> collMap.containsKey(coll1Func.apply(e))).collect(Collectors.toList());
    }

    /**
     * 根据指定的属性匹配，获取交集
     * 返回第一个集合的类型
     * @param coll1 集合
     * @param coll1Func 集合获取指定属性的函数
     * @param map map
     * @param <E1> 集合的类型
     * @param <E2> map的类型
     * @param <P> 指定属性
     * @return list
     */
    public static <E1, E2, P> List<E1> getContainsList(Collection<E1> coll1, Function<E1, P> coll1Func, Map<P, E2> map) {
        if (coll1 == null || map == null || coll1.isEmpty() || map.isEmpty()) {
            return Collections.emptyList();
        }

        return coll1.stream().filter(e-> map.containsKey(coll1Func.apply(e))).collect(Collectors.toList());
    }

    /**
     * 根据指定的属性匹配，获取交集
     * 返回第map value的类型
     * @param map map
     * @param coll2 集合
     * @param coll2Func
     * @param <E1>
     * @param <E2>
     * @param <P>
     * @return
     */
    public static <E1, E2, P> List<E1> getContainsList(Map<P, E1> map, Collection<E2> coll2, Function<E2, P> coll2Func) {
        if (map == null || coll2 == null || map.isEmpty() || coll2.isEmpty()) {
            return Collections.emptyList();
        }

        return coll2.stream().filter(e2 -> (map.containsKey(coll2Func.apply(e2)))).map((e2) -> (map.get(coll2Func.apply(e2)))).collect(Collectors.toList());
    }

    /**
     * 两个集合是否有交集
     * 元素以字符形式比对
     * @param coll1
     * @param coll2
     * @return true/false
     */
    public static boolean containsStringAny(final Collection coll1, final Collection coll2) {
        if (coll1 == null || coll1.isEmpty() || coll2 == null || coll2.isEmpty()) {
            return false;
        }

        for(Iterator it = coll1.iterator(); it.hasNext();) {
            Object o = it.next();
            for(Iterator it2 = coll2.iterator();it2.hasNext();) {
                if (String.valueOf(o).equals(String.valueOf(it2.next()))) {
                    return true;
                }
            }
        }

        return false;
    }

}
