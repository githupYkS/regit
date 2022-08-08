package com.yks.regit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yks.regit.common.R;
import com.yks.regit.dto.SetmealDto;
import com.yks.regit.emtity.Category;
import com.yks.regit.emtity.Setmeal;
import com.yks.regit.service.CategoryService;
import com.yks.regit.service.SetmealDishService;
import com.yks.regit.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;


    /**
     *CacheEvict：清理缓存数据
     *value：缓存的名称，每个缓存名称下面可以有多个key
     * key：缓存的key
     * allEntries:清理setmeal下面的所有的缓存
     */
    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmeal",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息：{}",setmealDto);
        //新增套餐数据
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐数据成功");
    }

    /**
     * 分页查询数据
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @Cacheable(value = "setmeal",key = "#setmeal.categoryId+'_'+#setmeal.status")
    public R<Page> page(int page,int pageSize,String name){
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        //构建分页条件
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name!=null,Setmeal::getName,name);
        //添加排序条件，根据跟新时间
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

        //拷贝对象
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }


    /**
     *CacheEvict：清理缓存数据
     *value：缓存的名称，每个缓存名称下面可以有多个key
     * key：缓存的key
     * allEntries:清理setmeal下面的所有的缓存
     */
    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmeal",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    /**
     *Cacheable：在方法执行前Spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据，若没有数据，调用方法，并将方法放入缓存中
     *value：缓存的名称，每个缓存名称下面可以有多个key
     * key：缓存的key
     */
    /**
     * 根据条件查询套餐数据
     * @return
     */
    @Cacheable(value = "setmeal",key = "#setmeal.categoryId+'_'+#setmeal.status")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        //构造查询条件
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        /*添加查询条件*/
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        //根据修改时间进行排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //进行查询
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
