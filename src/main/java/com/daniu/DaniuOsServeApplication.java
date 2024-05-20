package com.daniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.daniu.pms.mapper")
public class DaniuOsServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaniuOsServeApplication.class, args);
    }

}
