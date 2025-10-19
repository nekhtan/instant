package com.instant.fetcher;

import com.instant.data.DataModuleConfiguration;
import com.instant.fetcher.properties.FetcherProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class of the Fetcher application: calls various providers and persists the parking lots in the database.
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(FetcherProperties.class)
@Import({
    DataModuleConfiguration.class
})
public class InstantFetchApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstantFetchApplication.class, args);
    }

}