package com.yks.regit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yks.regit.dto.SetmealDto;
import com.yks.regit.emtity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品关联信息
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     */
    public void removeWithDish(List<Long> ids);
}
