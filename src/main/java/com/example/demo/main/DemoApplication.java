package com.example.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.demo")
public class DemoApplication {

    public static void main(String[] args) {
    	//training data
        SpringApplication.run(DemoApplication.class, args);
    }

}
