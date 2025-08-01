package org.example.mapper;

import org.example.entity.Admin;

import java.util.List;
/*
    mapper接口和mapper.xml的关系：
    mapper接口定义方法
    mapper.xml实现方法（通过SQL语句）
 */
public interface AdminMapper {
    List<Admin> selectAll();
    List<Admin> selectById(Integer id);
    Integer insertAdmin(Admin admin);
    Integer updateAdmin(Admin admin);
    Integer deleteById(Integer id);
    String selectPwdByUsername(String username);
    String selectRoleByUsername(String username);


}
