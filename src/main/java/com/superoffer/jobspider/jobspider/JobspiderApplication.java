package com.superoffer.jobspider.jobspider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobspiderApplication {

    public static void main(String[] args) {
        System.out.println("spring boot 启动成功！");
        SpringApplication.run(JobspiderApplication.class, args);
    }
}
