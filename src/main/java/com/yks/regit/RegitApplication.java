package com.yks.regit;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@MapperScan("com.yks.regit.mapper")
@ServletComponentScan
@EnableTransactionManagement//开启事务的注解
public class RegitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegitApplication.class, args);
		log.info("项目启动成功");
	}

}
