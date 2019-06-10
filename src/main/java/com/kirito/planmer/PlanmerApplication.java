package com.kirito.planmer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan("com.kirito.planmer.mapper")
public class PlanmerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanmerApplication.class, args);
    }

}
