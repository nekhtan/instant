package com.instant.fetcher.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Configuration of providers.
 */
@Configuration
@ConfigurationProperties(prefix = "fetcher")
@Data
@Validated
public class FetcherProperties {

    @NotEmpty
    @Valid
    private List<SourceConfiguration> sources;
}