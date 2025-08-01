package org.example.exception;

import org.example.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
    全局的异常捕获器
 */
//@ControllerAdvice("org.example.controller")
//public class GlobalExceptionHandler {
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody//将result对象转换成.json格式
//    public Result error(Exception e) {
//        log.error("系统异常", e);
//        return Result.error("系统异常");
//    }
//
//    @ExceptionHandler(value = CustomerException.class)
//    @ResponseBody//将result对象转换成.json格式
//    public Result customerError(CustomerException e) {
//        log.error("自定义异常", e);//将异常输出到日志里
//        return Result.error(e.getCode(), e.getMessage());
//    }
//}
