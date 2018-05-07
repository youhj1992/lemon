package org.lemon.yhj.dto;

import java.util.*;
import java.util.function.Function;

/**
 * 批量关联查询 函数类
 *
 * dto 主类
 * id 关联id
 * fDto 关联类
 * 根据主类list中的id 批量关联查询关联类的list or map
 *
 *
 */
public class BatchFunction<dto,id,fDto> {

    /**
     * dto获取id 函数
     */
    private Function<dto, id> getIdFromDtoFunc;

    /**
     * fDto获取id 函数
     */
    private Function<fDto,id> getIdFromFDtoFunc;

    /**
     * 根据id批量查询fDto list 函数
     */
    private Function<List<id>,List<fDto>> getDtosByIdsFunc;

    // 批量查询后的map形式的数据
    private Map<id,fDto> dataMap;

    // 批量查询后的list
    private List<fDto> dataList;

    // 收集id，用于批量查询
    private Set<id> ids;


    public BatchFunction(Function<dto, id> getIdFromDtoFunc, Function<fDto, id> getIdFromFDtoFunc, Function<List<id>, List<fDto>> getDtosByIdsFunc) {
        this.getIdFromDtoFunc = getIdFromDtoFunc;
        this.getIdFromFDtoFunc = getIdFromFDtoFunc;
        this.getDtosByIdsFunc = getDtosByIdsFunc;
        dataMap = new HashMap<>();
        ids = new HashSet<>();
    }

    /**
     * 1.
     * 从dto中获取id 放在id队列中
     */
    public void processIdFromEntity(dto d) {
        id id = getIdFromDtoFunc.apply(d);
        if (id != null) {
            ids.add(id);
        }
    }

    /**
     * 2.
     * 根据id队列的数据查询list结果
     */
    public void processSelectList() {
        if(ids.isEmpty()) return;
        this.dataList = getDtosByIdsFunc.apply(new ArrayList<>(ids));
    }

    /**
     * 3.
     * 把list队列转换为map
     */
    public void processMap() {
        if (dataList == null || dataList.isEmpty()) return;
        dataList.forEach(fDto -> dataMap.put(getIdFromFDtoFunc.apply(fDto), fDto));
    }

    /**
     * 4.
     * 从map的数据中 获取fDto
     */
    public fDto getEntityById(id id) {
        if (id == null) return null;
        return dataMap.get(id);
    }

}
