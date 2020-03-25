package com.baol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Evan
 */

@SpringBootApplication//申明这是一个Spring Boot项目
@ComponentScan(basePackages = {"com.baol.controller","com.baol.service"})//手动指定bean组件扫描范围
public class ItemApp {
    public static void main(String[] args) {
        SpringApplication.run(ItemApp.class, args);
    }
}