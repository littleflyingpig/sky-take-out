package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 通过菜品ID查套餐ID
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdByDishId(List<Long> dishIds);
}
