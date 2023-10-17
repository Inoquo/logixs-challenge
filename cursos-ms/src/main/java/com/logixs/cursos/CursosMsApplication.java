package com.logixs.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CursosMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursosMsApplication.class, args);
    }

}
