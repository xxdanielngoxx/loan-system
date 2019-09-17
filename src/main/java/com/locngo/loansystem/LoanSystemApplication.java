package com.locngo.loansystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanSystemApplication.class, args);
    }

}
