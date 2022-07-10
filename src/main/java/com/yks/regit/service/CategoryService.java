package com.yks.regit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yks.regit.emtity.Category;

public interface CategoryService extends IService<Category> {

    /*删除菜品分类*/
    public void remove(Long ids);
}
