package com.yks.regit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yks.regit.common.R;
import com.yks.regit.emtity.Orders;
import com.yks.regit.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param order
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order, HttpServletRequest request){
        log.info("订单数据：{}",order);
        orderService.submit(order,request);
        return R.success("下单成功");
    }

    /**
     * 查询当前的用户数据
     * @param page
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize, HttpSession session){
        log.info("page={},pageSize={}",page,pageSize);
        //构建分页构造器
        Page pageInfo = new Page(page,pageSize);
        //从session中获取当前的用户数据
        Long userId = (Long) session.getAttribute("user");
        //构建查询条件
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        queryWrapper.eq(Orders::getUserId,userId);
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //查询当前数据
        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
}
