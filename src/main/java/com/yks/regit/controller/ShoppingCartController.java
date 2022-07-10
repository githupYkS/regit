package com.yks.regit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yks.regit.common.BaseContext;
import com.yks.regit.common.R;
import com.yks.regit.emtity.ShoppingCart;
import com.yks.regit.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart,HttpSession session){
        log.info("购物车数据：{}",shoppingCart);

        //设置用户id，指定当前是哪个用户购物车数据
        Long user = (Long) session.getAttribute("user");
        shoppingCart.setUserId(user);

        //查询当前或者套餐
        Long dishId = shoppingCart.getDishId();
        //构造条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(ShoppingCart::getUserId,user);
        if (dishId != null){
            //添加到购物车的菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if (cartServiceOne!=null){
            //如果已经存在，就在原来的数量加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //如果不存在，则添加到购物车，数量默认是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne=shoppingCart;
        }
        //返回数据
        return R.success(shoppingCart);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpSession session){
        log.info("查看购物车...");
        //获取session中的用户ID
        Long user = (Long) session.getAttribute("user");
        //构造条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(ShoppingCart::getUserId,user);
        //排序
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        //查询
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);
        return R.success(shoppingCartList);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(HttpSession session){
        /*从session中获取当前的登录的ID*/
        Long user = (Long) session.getAttribute("user");
        //构造条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(ShoppingCart::getUserId,user);
        //执行删除
        shoppingCartService.remove(queryWrapper);
        //成功返回信息
        return R.success("清空购物车成功");
    }

    /**
     *
     减少份数
     * @param shoppingCart
     * @param session
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart,HttpSession session){
        log.info("要删减的菜品ID为：{}",shoppingCart.getDishId());
        //从session中获取用户id
        Long user = (Long) session.getAttribute("user");
        //构造查询条件
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        queryWrapper.eq(ShoppingCart::getUserId,user);
        queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        //查询出单条数据
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(queryWrapper);
        if (shoppingCart1.getNumber()>1){
            //构造修改条件
            LambdaUpdateWrapper<ShoppingCart> updateWrapper = new LambdaUpdateWrapper<>();
            //修改当前的数据
            updateWrapper.set(ShoppingCart::getNumber,shoppingCart1.getNumber()-1);
            updateWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
            shoppingCartService.update(updateWrapper);
        }else {
            //执行删除
            shoppingCartService.remove(queryWrapper);
        }
        return R.success(shoppingCart);
    }
}
