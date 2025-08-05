package org.example.controller;

import org.example.common.Result;
import org.example.entity.Admin;
import org.example.mapper.AdminMapper;
import org.example.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    @Mock
    AdminMapper adminMapper;

    @InjectMocks
    AdminService adminService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectAll() {
        Admin admin1 = new Admin();
        admin1.setId(1);
        admin1.setUsername("hajimi");
        admin1.setPassword("123456");
        admin1.setEmail("hajimi@gmail.com");
        admin1.setPhone("111");
        admin1.setName("hajihaji");
        //创建模拟数据，以便在不连接数据库的情况下测试
        //创建一个不可变列表，里面只包含之前创建的那个 admin1 对象
        List<Admin> mockAdmins = List.of(admin1);
        //当 adminMapper 的 selectAll() 方法被调用时，就返回我们预先准备好的 mockAdmins 列表
        when(adminMapper.selectAll()).thenReturn(mockAdmins);
        //调用方法
        List<Admin> admins = adminService.selectAll();

        assertEquals(admins.get(0).getId(), 1);
        assertThat(admins).hasSize(1);
        assertEquals(admins.get(0).getUsername(), "hajimi");
        assertEquals(admins.get(0).getPassword(), "123456");
    }
    //单元测试，用mock模拟数据
    @Test
    void selectById() {
        //设置数据
        Admin admin = new Admin();
        admin.setId(1);
        admin.setUsername("hajimi");
        admin.setPassword("123456");
        //定义mock行为
        List<Admin> mockAdmins = List.of(admin);
        when(adminMapper.selectById(1)).thenReturn(mockAdmins);
        //调用方法
        List<Admin> admins = adminService.selectById(1);
        //验证结果
        assertNotNull(admins);
        assertEquals("hajimi", admins.get(0).getUsername());
        assertEquals("123456", admins.get(0).getPassword());
        //验证是否调用selectById
        verify(adminMapper, times(1)).selectById(1);
    }

}