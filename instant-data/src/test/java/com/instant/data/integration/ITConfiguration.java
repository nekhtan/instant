package com.instant.data.integration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "com.instant.data" })
@EnableJpaRepositories(basePackages = "com.instant.data.repository")
@EntityScan(basePackages = "com.instant.data.entity")
public class ITConfiguration {}
