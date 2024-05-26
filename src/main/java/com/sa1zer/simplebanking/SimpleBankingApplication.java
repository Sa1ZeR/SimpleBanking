package com.sa1zer.simplebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBankingApplication.class, args);
    }

}
