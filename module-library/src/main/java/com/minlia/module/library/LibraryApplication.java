package com.minlia.module.library;

import com.minlia.module.rebecca.permission.annotation.EnablePermissionCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author garen
 */
//@EnablePermissionCollector
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}