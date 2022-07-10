package com.yks.regit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yks.regit.emtity.User;
import com.yks.regit.mapper.UserMapper;
import com.yks.regit.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
