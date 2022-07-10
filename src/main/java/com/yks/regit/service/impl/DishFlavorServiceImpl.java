package com.yks.regit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yks.regit.emtity.DishFlavor;
import com.yks.regit.mapper.DishFlavorMapper;
import com.yks.regit.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
