package com.instant.api.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for caches.
 */
@Configuration
@EnableCaching
public class CommonCacheConfiguration {

    @Bean
    CacheManager commonCacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(buildCache("parking-lots", 10)));
        return manager;
    }

    private static CaffeineCache buildCache(String name, int secondsToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
            .expireAfterWrite(secondsToExpire, TimeUnit.SECONDS)
            .build());
    }
}