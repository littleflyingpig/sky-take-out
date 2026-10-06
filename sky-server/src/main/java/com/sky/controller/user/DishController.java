package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@Slf4j
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId){
        //构造redis中的键，尝试查询
        String key = "dish_" + categoryId;
        //查询是否存在菜品数据
        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishVOList != null && dishVOList.size() > 0){
            return Result.success(dishVOList);
        }
        //如果不存在再到数据库查询
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        dishVOList = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, dishVOList);

        return Result.success(dishVOList);
    }
}
