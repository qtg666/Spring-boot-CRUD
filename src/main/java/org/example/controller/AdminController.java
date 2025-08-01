package org.example.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.annotations.CheckRole1;
import org.example.common.Result;
import org.example.entity.Admin;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    //查询全部
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
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        List<Admin> admins = adminService.selectById(id);
        return Result.success(admins);
    }

    @CheckRole1
    @PostMapping("/insertAdmin")
    public Result insertAdmin(Admin admin) {
        if(adminService.insertAdmin(admin)){
            return Result.success();
        }else{
            return Result.error("插入失败");
        }
    }

    @CheckRole1
    @PutMapping("/updateAdmin")
    public Result updateAdmin(Admin admin) {
        if(adminService.updateAdmin(admin)){
            return Result.success();
        }else{
            return Result.error("更新失败");
        }
    }

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
