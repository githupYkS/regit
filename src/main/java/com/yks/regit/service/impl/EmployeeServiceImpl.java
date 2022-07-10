package com.yks.regit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yks.regit.emtity.Employee;
import com.yks.regit.mapper.EmployeeMapper;
import com.yks.regit.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
