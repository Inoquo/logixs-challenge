package com.logixs.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CoursesMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesMsApplication.class, args);
    }

}
