package com.instant.fetcher.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.time.Duration;

/**
 * Configuration for REST clients.
 */
@Configuration
public class RestConfiguration {

    @Value("${spring.web.http-client.connection.timeoutMillis:10000}")
    private int connectionTimeoutMillis;

    @Value("${spring.web.http-client.read.timeoutMillis.default:20000}")
    private int responseTimeoutMillisDefault;

    /**
     * The {@link RestClient} used to fetch external data.
     *
     * @param restClientBuilder the {@link RestClient.Builder} (from SpringBoot's autoconfiguration)
     * @return the newly created {@link RestClient}
     * @see RestClientAutoConfiguration
     */
    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
            .requestInterceptor(new LoggingInterceptor())
            .build();
    }

    /**
     * Customizer for the {@link RestClient}.
     *
     * @return the newly created {@link RestClientCustomizer}
     * @see RestClientAutoConfiguration
     */
    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> restClientBuilder
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .requestFactory(ClientHttpRequestFactoryBuilder.httpComponents()
                .withCustomizer(factory -> factory.setConnectionRequestTimeout(Duration.ofMillis(5000)))
                .build(ClientHttpRequestFactorySettings.defaults()
                    .withConnectTimeout(Duration.ofMillis(connectionTimeoutMillis))
                    .withReadTimeout(Duration.ofMillis(responseTimeoutMillisDefault))));
    }

}
