package com.instant.data.integration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Mockito extension for tests that need to embark a (containerized) PostgreSQL database.
 */
public class PostgreSQLExtension implements BeforeAllCallback, AfterAllCallback {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:15-alpine");

    @Override
    public void beforeAll(ExtensionContext context) {
        if (isTopLevelTestClass(context)) {
            POSTGRES_CONTAINER.start();
            System.setProperty("spring.datasource.url", POSTGRES_CONTAINER.getJdbcUrl());
            System.setProperty("spring.datasource.username", POSTGRES_CONTAINER.getUsername());
            System.setProperty("spring.datasource.password", POSTGRES_CONTAINER.getPassword());
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (isTopLevelTestClass(context)) {
            POSTGRES_CONTAINER.stop();
            System.clearProperty("spring.datasource.url");
            System.clearProperty("spring.datasource.username");
            System.clearProperty("spring.datasource.password");
        }
    }

    private static boolean isTopLevelTestClass(ExtensionContext extensionContext) {
        return extensionContext.getTestClass()
            .map(klass -> klass.getAnnotation(Nested.class) == null)
            .orElse(true);
    }
}
