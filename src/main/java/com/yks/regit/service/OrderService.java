package com.yks.regit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yks.regit.emtity.Orders;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param order
     */
    public void submit(Orders order, HttpServletRequest request);
}
