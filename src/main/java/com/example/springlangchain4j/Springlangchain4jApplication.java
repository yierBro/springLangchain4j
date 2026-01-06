package com.example.springlangchain4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springlangchain4j.mapper")
public class Springlangchain4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springlangchain4jApplication.class, args);
    }

}
