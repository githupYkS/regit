package com.yks.regit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandel {

    /*
    *全局异常处理
    */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandel(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        /*异常处理，新增相同的用户名*/
        if (ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2]+"已经存在";
            return R.error(msg);
        }
        return R.error("失败了");
    }


    /**
     * 异常处理的方法
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandel(CustomException ex){
        log.error(ex.getMessage());
        //返回异常信息
        return R.error(ex.getMessage());
    }
}
