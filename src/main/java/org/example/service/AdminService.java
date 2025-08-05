package org.example.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.entity.Admin;
//import org.example.exception.CustomerException;
import org.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Resource
    AdminMapper adminMapper;

//    public String admin(String name){
//        if ("admin".equals(name)){
//            return "admin";
//        }else{
//            throw new CustomerException("账号错误");
//        }
//    }

    public List<Admin> selectAll(){
        return adminMapper.selectAll();
    }

    //开启分页查询
    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll();
        return PageInfo.of(list);
    }

    public List<Admin> selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    public Boolean insertAdmin(Admin admin) {
        int rows = adminMapper.insertAdmin(admin);
        return rows > 0;
    }

    public Boolean updateAdmin(Admin admin) {
        int rows = adminMapper.updateAdmin(admin);
        return rows > 0;
    }

    public Boolean deleteById(Integer id) {
        int rows = adminMapper.deleteById(id);
        return rows > 0;
    }

//    public String selectPwdByUsername(String username) {
//        return
//    }
    //验证登陆密码是否正确
    public boolean validateAdmin(String username, String password) {

        return adminMapper.selectPwdByUsername(username).equals(password);
    }

    //判断当前用户的角色是不是admin，以判断许可权限
    public boolean permission(String username) {
        if(adminMapper.selectRoleByUsername(username).equals("admin")) {
        return true;
        }else{
            return false;
        }
    }


}
