package de.nukulartechniker.badger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BadgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BadgerApplication.class, args);
    }
}
