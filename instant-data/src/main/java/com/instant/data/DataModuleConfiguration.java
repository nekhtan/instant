package com.instant.data;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for the Data module (persistence layer).
 */
@Configuration
@ComponentScan(basePackages = "com.instant.data")
@EnableAutoConfiguration
@EnableJpaAuditing
public class DataModuleConfiguration {}
