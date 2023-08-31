package com.zbdx.medicalzb;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zbdx.medicalzb")
public class MedicalZbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalZbApplication.class, args);
    }

}
