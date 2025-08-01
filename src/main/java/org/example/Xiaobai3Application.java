package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("org.example.mapper")
@EnableAspectJAutoProxy
public class Xiaobai3Application {

    public static void main(String[] args) {
        SpringApplication.run(Xiaobai3Application.class, args);
    }

}
