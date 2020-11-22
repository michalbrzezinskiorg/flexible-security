package org.michalbrzezinski.securitate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.stream.IntStream;

@SpringBootApplication
public class SecuritateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecuritateApplication.class, args);
    }
}
