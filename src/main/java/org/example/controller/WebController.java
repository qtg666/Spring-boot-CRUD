package org.example.controller;

import jakarta.annotation.Resource;
import org.example.common.Result;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Resource
    AdminService adminService;

    //表示这是一个Get请求的接口
    @GetMapping("/hello")//接口的路径，全局唯一的
    public Result hello() {
        return Result.success("hello hajimi");//接口的返回值
    }

    @GetMapping("/admin")
    public Result admin(String name) {
        String admin = adminService.admin(name);
        return Result.success(admin);//接口的返回值
    }


}
