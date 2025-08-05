package org.example.controller;

import org.example.entity.Admin;
import org.example.mapper.AdminMapper;
import org.example.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminControllerTest2 {
    @Autowired
    private AdminService adminService;
    //集成测试
    @Test
    //@Sql(scripts = "/insert_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insertAdmin() {
        Admin newUser = new Admin();
        newUser.setId(7);
        newUser.setName("Alice");
        newUser.setEmail("alice@example.com");
        newUser.setPhone("123");
        newUser.setUsername("alicc1");
        newUser.setPassword("1234");

        // When
        adminService.insertAdmin(newUser);

        // Then
        List<Admin> foundUser = adminService.selectById(4); // 假设这是新插入的ID
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.get(0).getName()).isEqualTo("Alice");
    }

    @Test
    void updateAdmin() {
    }

    @Test
    void deleteById() {
    }
}
