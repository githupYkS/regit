package com.yks.regit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yks.regit.dto.DishDto;
import com.yks.regit.emtity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //跟新菜品，同时对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}
