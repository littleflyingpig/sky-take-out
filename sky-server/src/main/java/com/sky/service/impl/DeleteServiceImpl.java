package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.service.DeleteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class DeleteServiceImpl implements DeleteService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    public void deleteBatch(List<Long> ids){
        //status是否为0
        for (Long id : ids) {
            Dish dish = dishMapper.findById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断是否关联，通过菜品id来查套餐id
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds != null && setmealIds.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        //删除菜品
        //for (Long id : ids) {
        //    dishMapper.deleteById(id);
        //    //删除口味
        //    dishFlavorMapper.deleteByDishId(id);
        //}
        //批量删除效果更好
        //delete from dish where id in (?,?,?)
        dishMapper.deleteByIds(ids);
        //delete from dish_flavor where dish_id in (?,?,?)
        dishFlavorMapper.deleteByIds(ids);
    }
}
