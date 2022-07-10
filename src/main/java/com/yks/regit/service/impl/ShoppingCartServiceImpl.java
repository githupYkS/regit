package com.yks.regit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yks.regit.emtity.ShoppingCart;
import com.yks.regit.mapper.ShoppingCartMapper;
import com.yks.regit.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
