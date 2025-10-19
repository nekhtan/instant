package com.instant.fetcher.properties;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.validation.annotation.Validated;

/**
 * Configuration of a data source (parking lot provider).
 */
@Data
@Validated
public class SourceConfiguration {

    @NotNull
    private String city;

    @NotNull
    private String url;

    @NotNull
    private String type;

    @NotNull
    private String schedule;

    private boolean enabled;

}