package org.example.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.annotations.CheckRole1;
import org.example.common.Result;
import org.example.entity.Admin;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "用户操作", description = "对用户进行操作的接口")
public class AdminController {

    @Resource
    AdminService adminService;

    //查询全部
    @Operation(summary = "查询全部", description = "查询用户表里所有信息")
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Admin> admins = adminService.selectAll();
        return Result.success(admins);
    }

    @GetMapping("/selectPage")//分页查询
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Admin> pageInfo =adminService.selectPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    //按id查询
    @Operation(summary = "按id查询", description = "按id查询用户表里的信息",
            parameters = {@Parameter(name = "id", description = "用户id", required = true, example = "1")})
    @CheckRole1
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        List<Admin> admins = adminService.selectById(id);
        return Result.success(admins);
    }

    @Operation(summary = "插入用户", description = "把一条新的用户信息插入用户表",
            parameters = {@Parameter(name = "admin", description = "用户信息", required = true,
                    example = "{id=1,name=xiaoming,password=123456,email=11@qq.com,phone=123,username=xm123}")})
    @CheckRole1
    @PostMapping("/insertAdmin")
    public Result insertAdmin(Admin admin) {
        if(adminService.insertAdmin(admin)){
            return Result.success();
        }else{
            return Result.error("插入失败");
        }
    }

    @Operation(summary = "更新用户", description = "输入用户信息，自动将该信息中的id对应的用户信息替换为刚输入的",
            parameters = {@Parameter(name = "admin", description = "用户信息", required = true,
                    example = "{id=1,name=xiaoming,password=123456,email=11@qq.com,phone=123,username=xm123}")})
    @CheckRole1
    @PutMapping("/updateAdmin")
    public Result updateAdmin(Admin admin) {
        if(adminService.updateAdmin(admin)){
            return Result.success();
        }else{
            return Result.error("更新失败");
        }
    }

    @Operation(summary = "删除用户", description = "按id删除用户表中对应的用户信息",
            parameters = {@Parameter(name = "id", description = "用户id", required = true, example = "1")})
    @CheckRole1
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        if(adminService.deleteById(id)){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }
}
