package com.vgorash.lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SoaLab2Application {

    public static void main(String[] args) {
        SpringApplication.run(SoaLab2Application.class, args);
    }

}
