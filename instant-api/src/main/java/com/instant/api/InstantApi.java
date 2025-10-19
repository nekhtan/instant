package com.instant.api;

import com.instant.data.DataModuleConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The main class for the Instant API application.
 */
@SpringBootApplication
@Import({
    DataModuleConfiguration.class
})
public class InstantApi {

    public static void main(String[] args) {
        SpringApplication.run(InstantApi.class, args);
    }

}
