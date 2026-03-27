package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

//@SpringBootApplication(scanBasePackages = "com")
//public class TenderApplicationBackendApplication {

@SpringBootApplication(scanBasePackages = "com",
		exclude = {DataSourceAutoConfiguration.class})
public class TenderApplicationBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenderApplicationBackendApplication.class, args);
    }
}