package com.yks.regit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yks.regit.emtity.OrderDetail;
import com.yks.regit.mapper.OrderDetailMapper;
import com.yks.regit.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
