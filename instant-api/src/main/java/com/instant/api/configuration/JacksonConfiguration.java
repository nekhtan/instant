package com.instant.api.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Jackson.
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Configure Jackson to ignore null fields during serialization.
     *
     * @return the customizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    }

}