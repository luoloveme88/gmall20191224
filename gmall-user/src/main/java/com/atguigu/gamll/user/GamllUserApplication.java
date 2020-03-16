package com.atguigu.gamll.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gamll.user.mapper")
@org.mybatis.spring.annotation.MapperScan
public class GamllUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamllUserApplication.class, args);
    }

}
