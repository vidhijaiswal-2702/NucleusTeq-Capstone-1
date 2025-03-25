package com.ifms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ifms")
public class IfmsBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(IfmsBackendApplication.class, args);
        System.out.println("🚀 IFMS Backend is running...");
    }
}
