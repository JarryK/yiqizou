package com.yiqizou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.web")
@MapperScan({"com.web.Dao","com.web.security.mapper"})
public class YiziqouApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiziqouApplication.class, args);
    }

}
