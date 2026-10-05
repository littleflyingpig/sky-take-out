package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 依次插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 删除口味
     * @param dishId
     */
    @Delete("delete from dish_flavor where id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 批量删除口味数据
     * @param dishIds
     */
    void deleteByIds(List<Long> dishIds);

    /**
     * 查询口味信息
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
